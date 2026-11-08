package ru.homestead.client.video;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import ru.homestead.Homestead;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Client video+audio session optimized for smoother playback.
 * - Caps display resolution (default max width 640) for stable FPS in-game
 * - Bulk pixel upload into NativeImage
 * - Audio via Java Sound SourceDataLine
 * - Timestamp-based pacing against grabber
 */
public class VideoSession implements AutoCloseable {
    private static final int MAX_WIDTH = 640;

    private final BlockPos pos;
    private final String source;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicReference<BufferedImage> pendingFrame = new AtomicReference<>();

    private Thread worker;
    private FFmpegFrameGrabber grabber;
    private DynamicTexture texture;
    private ResourceLocation textureId;
    private int texW = 16;
    private int texH = 16;
    private String error;
    private SourceDataLine audioLine;

    public VideoSession(BlockPos pos, String source) {
        this.pos = pos;
        this.source = source;
    }

    public ResourceLocation getTextureId() { return textureId; }
    public String getError() { return error; }
    public boolean isRunning() { return running.get(); }

    public void start() {
        if (running.get()) return;
        running.set(true);
        paused.set(false);
        worker = new Thread(this::loop, "homestead-video-" + pos.asLong());
        worker.setDaemon(true);
        worker.setPriority(Thread.NORM_PRIORITY + 1);
        worker.start();
    }

    public void setPaused(boolean value) {
        paused.set(value);
        if (audioLine != null) {
            try {
                if (value) audioLine.stop();
                else audioLine.start();
            } catch (Exception ignored) {}
        }
    }

    /** Called from render thread: flip pending frame into GPU texture. */
    public void tickUpload() {
        BufferedImage img = pendingFrame.getAndSet(null);
        if (img == null) return;
        ensureTexture(img.getWidth(), img.getHeight());
        if (texture == null) return;
        NativeImage ni = texture.getPixels();
        if (ni == null) return;
        bulkCopy(img, ni);
        texture.upload();
    }

    private void loop() {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        long startWall = System.nanoTime();
        long startMediaUs = 0;
        boolean mediaStartSet = false;

        try {
            grabber = new FFmpegFrameGrabber(source);
            grabber.setOption("threads", "auto");
            grabber.start();

            int srcW = Math.max(1, grabber.getImageWidth());
            int srcH = Math.max(1, grabber.getImageHeight());
            double scale = srcW > MAX_WIDTH ? (double) MAX_WIDTH / srcW : 1.0;
            int outW = Math.max(2, (int) (srcW * scale) & ~1);
            int outH = Math.max(2, (int) (srcH * scale) & ~1);

            Minecraft.getInstance().execute(() -> ensureTexture(outW, outH));

            initAudio(grabber);

            while (running.get()) {
                if (paused.get()) {
                    Thread.sleep(20);
                    continue;
                }

                Frame frame = grabber.grab();
                if (frame == null) {
                    grabber.setTimestamp(0);
                    startWall = System.nanoTime();
                    mediaStartSet = false;
                    continue;
                }

                if (frame.image != null) {
                    long ts = frame.timestamp; // microseconds
                    if (!mediaStartSet) {
                        startMediaUs = ts;
                        startWall = System.nanoTime();
                        mediaStartSet = true;
                    }
                    long targetWall = startWall + (ts - startMediaUs) * 1000L;
                    long now = System.nanoTime();
                    long sleepNs = targetWall - now;
                    if (sleepNs > 2_000_000L) {
                        Thread.sleep(sleepNs / 1_000_000L, (int) (sleepNs % 1_000_000L));
                    }

                    BufferedImage raw = converter.convert(frame);
                    if (raw != null) {
                        BufferedImage scaled = scaleImage(raw, outW, outH);
                        pendingFrame.set(scaled);
                    }
                }

                if (frame.samples != null && audioLine != null && !paused.get()) {
                    writeAudio(frame);
                }
            }
        } catch (Throwable t) {
            error = t.getMessage() != null ? t.getMessage() : t.getClass().getSimpleName();
            t.printStackTrace();
        } finally {
            running.set(false);
            closeAudio();
            try { if (grabber != null) grabber.stop(); } catch (Exception ignored) {}
            try { if (grabber != null) grabber.release(); } catch (Exception ignored) {}
        }
    }

    private void initAudio(FFmpegFrameGrabber g) {
        try {
            int channels = Math.max(1, g.getAudioChannels());
            int sampleRate = g.getSampleRate() > 0 ? g.getSampleRate() : 44100;
            AudioFormat format = new AudioFormat(sampleRate, 16, channels, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                // try stereo fallback
                format = new AudioFormat(44100, 16, 2, true, false);
                info = new DataLine.Info(SourceDataLine.class, format);
            }
            if (AudioSystem.isLineSupported(info)) {
                audioLine = (SourceDataLine) AudioSystem.getLine(info);
                audioLine.open(format, sampleRate * channels * 2);
                audioLine.start();
            }
        } catch (Throwable t) {
            audioLine = null;
            System.err.println("[Homestead] Audio init failed: " + t.getMessage());
        }
    }

    private void writeAudio(Frame frame) {
        try {
            if (frame.samples == null || frame.samples.length == 0) return;
            ShortBuffer sb = frame.samples[0] instanceof ShortBuffer
                    ? (ShortBuffer) frame.samples[0]
                    : null;
            if (sb == null) {
                // convert float buffer if needed
                if (frame.samples[0] instanceof java.nio.FloatBuffer fb) {
                    sb = floatToShort(fb);
                } else if (frame.samples[0] instanceof ByteBuffer) {
                    return;
                } else {
                    return;
                }
            }
            sb.rewind();
            byte[] bytes = new byte[sb.remaining() * 2];
            for (int i = 0; i < bytes.length; i += 2) {
                short s = sb.get();
                bytes[i] = (byte) (s & 0xFF);
                bytes[i + 1] = (byte) ((s >> 8) & 0xFF);
            }
            audioLine.write(bytes, 0, bytes.length);
        } catch (Throwable ignored) {}
    }

    private static ShortBuffer floatToShort(java.nio.FloatBuffer fb) {
        fb.rewind();
        short[] out = new short[fb.remaining()];
        for (int i = 0; i < out.length; i++) {
            float f = Math.max(-1f, Math.min(1f, fb.get()));
            out[i] = (short) (f * 32767);
        }
        return ShortBuffer.wrap(out);
    }

    private void closeAudio() {
        try {
            if (audioLine != null) {
                audioLine.drain();
                audioLine.stop();
                audioLine.close();
            }
        } catch (Exception ignored) {}
        audioLine = null;
    }

    private static BufferedImage scaleImage(BufferedImage src, int w, int h) {
        if (src.getWidth() == w && src.getHeight() == h) {
            // ensure INT_RGB for bulk copy
            if (src.getType() == BufferedImage.TYPE_INT_RGB || src.getType() == BufferedImage.TYPE_INT_ARGB) {
                return src;
            }
        }
        BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dst.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(src, 0, 0, w, h, null);
        g.dispose();
        return dst;
    }

    private void ensureTexture(int w, int h) {
        if (texture != null && texW == w && texH == h) return;
        if (textureId != null) {
            Minecraft.getInstance().getTextureManager().release(textureId);
            textureId = null;
        }
        if (texture != null) {
            texture.close();
            texture = null;
        }
        texture = new DynamicTexture(w, h, true);
        texW = w;
        texH = h;
        textureId = new ResourceLocation(Homestead.MOD_ID, "video/" + Long.toUnsignedString(pos.asLong()));
        Minecraft.getInstance().getTextureManager().register(textureId, texture);
    }

    private static void bulkCopy(BufferedImage image, NativeImage ni) {
        int w = Math.min(ni.getWidth(), image.getWidth());
        int h = Math.min(ni.getHeight(), image.getHeight());
        BufferedImage src = image;
        if (image.getType() != BufferedImage.TYPE_INT_ARGB && image.getType() != BufferedImage.TYPE_INT_RGB) {
            BufferedImage conv = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = conv.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = conv;
        }
        int[] pixels = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
        int stride = src.getWidth();
        for (int y = 0; y < h; y++) {
            int row = y * stride;
            for (int x = 0; x < w; x++) {
                int argb = pixels[row + x];
                int a = (argb >> 24) & 0xFF;
                if (src.getType() == BufferedImage.TYPE_INT_RGB) a = 255;
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;
                // NativeImage setPixelRGBA expects ABGR in actual memory on many mappings
                ni.setPixelRGBA(x, y, (a << 24) | (b << 16) | (g << 8) | r);
            }
        }
    }

    @Override
    public void close() {
        running.set(false);
        if (worker != null) {
            try { worker.join(800); } catch (InterruptedException ignored) {}
        }
        closeAudio();
        Minecraft.getInstance().execute(() -> {
            if (textureId != null) {
                Minecraft.getInstance().getTextureManager().release(textureId);
                textureId = null;
            }
            if (texture != null) {
                texture.close();
                texture = null;
            }
        });
    }
}
