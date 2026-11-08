package ru.homestead.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import ru.homestead.block.MediaBlock;
import ru.homestead.block.entity.MediaBlockEntity;

import java.util.function.Supplier;

public class MediaActionPacket {
    public enum Action { SET_URL, SET_FILE, PLAY, PAUSE, STOP, POWER_OFF, SET_VOLUME }

    private final BlockPos pos;
    private final Action action;
    private final String text;
    private final float volume;

    public MediaActionPacket(BlockPos pos, Action action, String text, float volume) {
        this.pos = pos;
        this.action = action;
        this.text = text == null ? "" : text;
        this.volume = volume;
    }

    public static MediaActionPacket setUrl(BlockPos pos, String url) {
        return new MediaActionPacket(pos, Action.SET_URL, url, 0);
    }

    public static MediaActionPacket setFile(BlockPos pos, String path) {
        return new MediaActionPacket(pos, Action.SET_FILE, path, 0);
    }

    public static MediaActionPacket play(BlockPos pos) { return new MediaActionPacket(pos, Action.PLAY, "", 0); }
    public static MediaActionPacket pause(BlockPos pos) { return new MediaActionPacket(pos, Action.PAUSE, "", 0); }
    public static MediaActionPacket stop(BlockPos pos) { return new MediaActionPacket(pos, Action.STOP, "", 0); }
    public static MediaActionPacket powerOff(BlockPos pos) { return new MediaActionPacket(pos, Action.POWER_OFF, "", 0); }

    public static void encode(MediaActionPacket pkt, FriendlyByteBuf buf) {
        buf.writeBlockPos(pkt.pos);
        buf.writeEnum(pkt.action);
        buf.writeUtf(pkt.text, 1024);
        buf.writeFloat(pkt.volume);
    }

    public static MediaActionPacket decode(FriendlyByteBuf buf) {
        return new MediaActionPacket(buf.readBlockPos(), buf.readEnum(Action.class), buf.readUtf(1024), buf.readFloat());
    }

    public static void handle(MediaActionPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            if (player.distanceToSqr(pkt.pos.getX() + 0.5, pkt.pos.getY() + 0.5, pkt.pos.getZ() + 0.5) > 64.0) return;
            BlockEntity be = player.level().getBlockEntity(pkt.pos);
            if (!(be instanceof MediaBlockEntity media)) return;
            switch (pkt.action) {
                case SET_URL -> media.setUrl(pkt.text);
                case SET_FILE -> media.setFilePath(pkt.text);
                case PLAY -> media.play();
                case PAUSE -> media.pause();
                case STOP -> media.stop();
                case POWER_OFF -> {
                    media.powerOff();
                    var state = player.level().getBlockState(pkt.pos);
                    if (state.getBlock() instanceof MediaBlock) {
                        player.level().setBlock(pkt.pos, state.setValue(MediaBlock.LIT, false), 3);
                    }
                }
                case SET_VOLUME -> media.setVolume(pkt.volume);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
