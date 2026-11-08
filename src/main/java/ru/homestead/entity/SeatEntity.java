package ru.homestead.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class SeatEntity extends Entity {
    private BlockPos sourcePos;

    public SeatEntity(EntityType<? extends SeatEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public SeatEntity(Level level, BlockPos pos, double yOffset) {
        this(ru.homestead.registry.ModEntities.SEAT.get(), level);
        this.sourcePos = pos.immutable();
        this.setPos(pos.getX() + 0.5, pos.getY() + yOffset, pos.getZ() + 0.5);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("SourceX")) {
            this.sourcePos = new BlockPos(tag.getInt("SourceX"), tag.getInt("SourceY"), tag.getInt("SourceZ"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (this.sourcePos != null) {
            tag.putInt("SourceX", this.sourcePos.getX());
            tag.putInt("SourceY", this.sourcePos.getY());
            tag.putInt("SourceZ", this.sourcePos.getZ());
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.getPassengers().isEmpty() || this.sourcePos == null || this.level().getBlockState(this.sourcePos).isAir()) {
                this.discard();
            }
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(net.minecraft.world.entity.LivingEntity passenger) {
        Direction direction = Direction.NORTH;
        if (this.sourcePos != null) {
            // Prefer dismount in front of the seat
            return new Vec3(this.sourcePos.getX() + 0.5, this.sourcePos.getY(), this.sourcePos.getZ() + 0.5)
                    .add(direction.getStepX() * 0.8, 0, direction.getStepZ() * 0.8);
        }
        return super.getDismountLocationForPassenger(passenger);
    }

    public static boolean sit(Level level, BlockPos pos, Player player, double yOffset) {
        if (level.isClientSide) {
            return true;
        }
        // Check if someone is already sitting here
        AABB search = new AABB(pos).inflate(0.1);
        for (SeatEntity existing : level.getEntitiesOfClass(SeatEntity.class, search)) {
            if (!existing.getPassengers().isEmpty()) {
                return false;
            }
        }
        SeatEntity seat = new SeatEntity(level, pos, yOffset);
        level.addFreshEntity(seat);
        player.startRiding(seat);
        return true;
    }
}
