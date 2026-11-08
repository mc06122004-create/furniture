package ru.homestead.client.video;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import ru.homestead.block.entity.MediaBlockEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class VideoManager {
    private static final Map<BlockPos, VideoSession> SESSIONS = new ConcurrentHashMap<>();
    private static final Map<BlockPos, String> SOURCES = new ConcurrentHashMap<>();

    private VideoManager() {}

    public static void sync(BlockPos pos, MediaBlockEntity media) {
        MediaBlockEntity.PlayState state = media.getPlayState();
        String source = media.getActiveSource();

        if (state == MediaBlockEntity.PlayState.PLAYING || state == MediaBlockEntity.PlayState.PAUSED) {
            if (source == null || source.isEmpty()) {
                stop(pos);
                return;
            }
            String current = SOURCES.get(pos);
            if (current == null || !current.equals(source) || !SESSIONS.containsKey(pos)) {
                stop(pos);
                VideoSession session = new VideoSession(pos, source);
                SESSIONS.put(pos, session);
                SOURCES.put(pos, source);
                session.start();
            }
            VideoSession session = SESSIONS.get(pos);
            if (session != null) {
                session.setPaused(state == MediaBlockEntity.PlayState.PAUSED);
            }
        } else {
            stop(pos);
        }
    }

    public static void stop(BlockPos pos) {
        VideoSession session = SESSIONS.remove(pos);
        SOURCES.remove(pos);
        if (session != null) session.close();
    }

    public static ResourceLocation texture(BlockPos pos) {
        VideoSession session = SESSIONS.get(pos);
        return session != null ? session.getTextureId() : null;
    }

    public static void tickUpload(BlockPos pos) {
        VideoSession session = SESSIONS.get(pos);
        if (session != null) session.tickUpload();
    }

    public static String error(BlockPos pos) {
        VideoSession session = SESSIONS.get(pos);
        return session != null ? session.getError() : null;
    }

    public static void clearAll() {
        for (BlockPos pos : SESSIONS.keySet().toArray(new BlockPos[0])) {
            stop(pos);
        }
    }
}
