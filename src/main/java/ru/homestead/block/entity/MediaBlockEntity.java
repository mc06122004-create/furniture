package ru.homestead.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import ru.homestead.menu.MediaMenu;
import ru.homestead.registry.ModBlockEntities;

public class MediaBlockEntity extends BlockEntity implements MenuProvider {
    public enum PlayState { OFF, ON, PLAYING, PAUSED }
    public enum SourceType { NONE, URL, FILE }

    private String url = "";
    private String filePath = "";
    private SourceType sourceType = SourceType.NONE;
    private PlayState playState = PlayState.OFF;
    private float volume = 1.0F;
    private int positionSeconds = 0;

    public MediaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MEDIA.get(), pos, state);
    }

    public String getUrl() { return url; }
    public String getFilePath() { return filePath; }
    public SourceType getSourceType() { return sourceType; }
    public String getActiveSource() {
        return switch (sourceType) {
            case URL -> url;
            case FILE -> filePath;
            default -> "";
        };
    }

    public void setUrl(String url) {
        this.url = url == null ? "" : url.trim();
        if (!this.url.isEmpty()) {
            this.sourceType = SourceType.URL;
            this.filePath = "";
        } else if (this.filePath.isEmpty()) {
            this.sourceType = SourceType.NONE;
        }
        setChanged();
        sync();
    }

    public void setFilePath(String path) {
        this.filePath = path == null ? "" : path.trim();
        if (!this.filePath.isEmpty()) {
            this.sourceType = SourceType.FILE;
            this.url = "";
        } else if (this.url.isEmpty()) {
            this.sourceType = SourceType.NONE;
        }
        setChanged();
        sync();
    }

    public PlayState getPlayState() { return playState; }
    public void setPlayState(PlayState state) { this.playState = state; setChanged(); sync(); }
    public float getVolume() { return volume; }
    public void setVolume(float volume) { this.volume = Math.max(0F, Math.min(1F, volume)); setChanged(); sync(); }
    public int getPositionSeconds() { return positionSeconds; }
    public void setPositionSeconds(int s) { this.positionSeconds = Math.max(0, s); setChanged(); sync(); }

    public void powerOn() {
        if (playState == PlayState.OFF) { playState = PlayState.ON; setChanged(); sync(); }
    }

    public void powerOff() {
        playState = PlayState.OFF;
        positionSeconds = 0;
        setChanged();
        sync();
    }

    public void play() {
        if (getActiveSource().isEmpty()) return;
        playState = PlayState.PLAYING;
        setChanged();
        sync();
    }

    public void pause() {
        if (playState == PlayState.PLAYING) { playState = PlayState.PAUSED; setChanged(); sync(); }
    }

    public void stop() {
        if (playState == PlayState.PLAYING || playState == PlayState.PAUSED) {
            playState = PlayState.ON;
            positionSeconds = 0;
            setChanged();
            sync();
        }
    }

    private void sync() {
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Url", url);
        tag.putString("FilePath", filePath);
        tag.putString("SourceType", sourceType.name());
        tag.putString("PlayState", playState.name());
        tag.putFloat("Volume", volume);
        tag.putInt("Position", positionSeconds);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        url = tag.getString("Url");
        filePath = tag.getString("FilePath");
        try { sourceType = SourceType.valueOf(tag.getString("SourceType")); } catch (Exception e) { sourceType = SourceType.NONE; }
        try { playState = PlayState.valueOf(tag.getString("PlayState")); } catch (Exception e) { playState = PlayState.OFF; }
        volume = tag.contains("Volume") ? tag.getFloat("Volume") : 1.0F;
        positionSeconds = tag.getInt("Position");
    }

    @Override public CompoundTag getUpdateTag() { CompoundTag tag = super.getUpdateTag(); saveAdditional(tag); return tag; }
    @Override public ClientboundBlockEntityDataPacket getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }
    @Override public Component getDisplayName() { return Component.translatable("container.homestead.media"); }
    @Nullable @Override public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) { return new MediaMenu(id, inv, this); }
}
