package ru.homestead.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ru.homestead.registry.ModBlockEntities;
import ru.homestead.registry.ModSounds;
import net.minecraft.sounds.SoundSource;
import ru.homestead.block.ApplianceBlock;

public class ApplianceBlockEntity extends BlockEntity {
    private boolean powered = false;
    private int timerTicks = 0;
    private int maxTimerTicks = 0;
    private ItemStack cookingItem = ItemStack.EMPTY;
    private ItemStack resultItem = ItemStack.EMPTY;

    public ApplianceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.APPLIANCE.get(), pos, state);
    }

    public boolean isPowered() { return powered; }

    public void setPowered(boolean powered) {
        this.powered = powered;
        if (!powered) {
            this.timerTicks = 0;
        }
        setChanged();
        sync();
    }

    public void toggle() {
        setPowered(!this.powered);
    }

    public int getTimerTicks() { return timerTicks; }
    public int getMaxTimerTicks() { return maxTimerTicks; }
    public boolean hasTimer() { return maxTimerTicks > 0; }

    public ItemStack getCookingItem() { return cookingItem; }
    public boolean hasCookingItem() { return !cookingItem.isEmpty(); }

    public void startCooking(ItemStack input, ItemStack result, int seconds) {
        this.cookingItem = input.copy();
        this.cookingItem.setCount(1);
        this.resultItem = result.copy();
        this.resultItem.setCount(1);
        this.maxTimerTicks = seconds * 20;
        this.timerTicks = this.maxTimerTicks;
        this.powered = true;
        setChanged();
        sync();
    }

    public void startTimer(int seconds) {
        this.maxTimerTicks = seconds * 20;
        this.timerTicks = this.maxTimerTicks;
        this.powered = true;
        setChanged();
        sync();
    }

    public ItemStack finishAndTakeResult() {
        ItemStack out = this.resultItem.copy();
        this.cookingItem = ItemStack.EMPTY;
        this.resultItem = ItemStack.EMPTY;
        this.powered = false;
        this.timerTicks = 0;
        this.maxTimerTicks = 0;
        setChanged();
        sync();
        return out;
    }

    public void clearCooking() {
        this.cookingItem = ItemStack.EMPTY;
        this.resultItem = ItemStack.EMPTY;
        this.powered = false;
        this.timerTicks = 0;
        this.maxTimerTicks = 0;
        setChanged();
        sync();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ApplianceBlockEntity be) {
        if (level.isClientSide) return;
        if (be.powered && be.timerTicks > 0) {
            be.timerTicks--;
            if (be.timerTicks <= 0) {
                be.powered = false;
                // Keep cookingItem/resultItem so player can take the result
                BlockState st = level.getBlockState(pos);
                if (st.getBlock() instanceof ApplianceBlock ab && ab.getApplianceType() == ApplianceBlock.Type.MICROWAVE) {
                    level.playSound(null, pos, ModSounds.MICROWAVE_DONE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                level.setBlock(pos, st.setValue(ApplianceBlock.LIT, false), 3);
                be.setChanged();
                be.sync();
            } else if (be.timerTicks % 20 == 0) {
                be.setChanged();
            }
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
        tag.putBoolean("Powered", powered);
        tag.putInt("Timer", timerTicks);
        tag.putInt("MaxTimer", maxTimerTicks);
        tag.put("CookingItem", cookingItem.save(new CompoundTag()));
        tag.put("ResultItem", resultItem.save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        powered = tag.getBoolean("Powered");
        timerTicks = tag.getInt("Timer");
        maxTimerTicks = tag.getInt("MaxTimer");
        cookingItem = ItemStack.of(tag.getCompound("CookingItem"));
        resultItem = ItemStack.of(tag.getCompound("ResultItem"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
