package ru.homestead.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import ru.homestead.block.entity.MediaBlockEntity;
import ru.homestead.network.MediaActionPacket;
import ru.homestead.network.ModNetwork;
import ru.homestead.registry.ModMenus;

public class MediaMenu extends AbstractContainerMenu {
    private final MediaBlockEntity media;
    private final BlockPos pos;
    private final ContainerData data;
    private final boolean clientSide;

    public MediaMenu(int id, Inventory inv, MediaBlockEntity media) {
        super(ModMenus.MEDIA.get(), id);
        this.media = media;
        this.pos = media.getBlockPos();
        this.data = new SimpleContainerData(3);
        this.clientSide = inv.player.level().isClientSide;
        addDataSlots(data);
        syncFromEntity();
    }

    public MediaMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, getMedia(inv, buf));
    }

    private static MediaBlockEntity getMedia(Inventory inv, FriendlyByteBuf buf) {
        BlockEntity be = inv.player.level().getBlockEntity(buf.readBlockPos());
        if (be instanceof MediaBlockEntity m) return m;
        throw new IllegalStateException("Media BlockEntity missing");
    }

    private void syncFromEntity() {
        if (media == null) return;
        data.set(0, media.getPlayState().ordinal());
        data.set(1, (int) (media.getVolume() * 100));
        data.set(2, media.getPositionSeconds());
    }

    @Override
    public void broadcastChanges() {
        syncFromEntity();
        super.broadcastChanges();
    }

    public MediaBlockEntity getMedia() { return media; }
    public BlockPos getPos() { return pos; }
    public String getUrl() { return media != null ? media.getUrl() : ""; }
    public String getFilePath() { return media != null ? media.getFilePath() : ""; }
    public MediaBlockEntity.SourceType getSourceType() {
        return media != null ? media.getSourceType() : MediaBlockEntity.SourceType.NONE;
    }

    public MediaBlockEntity.PlayState getPlayState() {
        int ord = data.get(0);
        var values = MediaBlockEntity.PlayState.values();
        if (ord >= 0 && ord < values.length) return values[ord];
        return MediaBlockEntity.PlayState.OFF;
    }

    public void setUrl(String url) {
        if (clientSide) ModNetwork.CHANNEL.sendToServer(MediaActionPacket.setUrl(pos, url));
        else if (media != null) media.setUrl(url);
    }

    public void setFilePath(String path) {
        if (clientSide) ModNetwork.CHANNEL.sendToServer(MediaActionPacket.setFile(pos, path));
        else if (media != null) media.setFilePath(path);
    }

    public void play() {
        if (clientSide) ModNetwork.CHANNEL.sendToServer(MediaActionPacket.play(pos));
        else if (media != null) media.play();
    }

    public void pause() {
        if (clientSide) ModNetwork.CHANNEL.sendToServer(MediaActionPacket.pause(pos));
        else if (media != null) media.pause();
    }

    public void stop() {
        if (clientSide) ModNetwork.CHANNEL.sendToServer(MediaActionPacket.stop(pos));
        else if (media != null) media.stop();
    }

    public void powerOff() {
        if (clientSide) ModNetwork.CHANNEL.sendToServer(MediaActionPacket.powerOff(pos));
        else if (media != null) media.powerOff();
    }

    @Override
    public boolean stillValid(Player player) {
        return media != null && media.getLevel() != null
                && media.getLevel().getBlockEntity(pos) == media
                && player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
