package ru.homestead.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.homestead.entity.SeatEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;

public class FurnitureBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public enum Type {
        CHAIR, STOOL, ARMCHAIR, SOFA, BOOKSHELF, DINING_TABLE, COFFEE_TABLE,
        BAR_STOOL, COUNTERTOP, MICROWAVE, SINK, STOVE,
        BATHTUB, SHOWER, TOILET, BATHROOM_SINK, TOWEL_RACK, MIRROR,
        OFFICE_CHAIR, GAMING_CHAIR, COMPUTER_DESK, MONITOR, PC_TOWER, LAPTOP,
        KEYBOARD, PRINTER, SPEAKER
    }

    private final Type type;

    public FurnitureBlock(BlockBehaviour.Properties properties, Type type) {
        super(properties);
        this.type = type;
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public Type getFurnitureType() { return this.type; }

    private VoxelShape createShape(BlockState state) {
        Direction facing = state.getValue(FACING);
        VoxelShape legsAndSeat = Shapes.or(
                box(2, 7, 2, 14, 9, 14),
                box(2, 0, 2, 4, 7, 4), box(12, 0, 2, 14, 7, 4),
                box(2, 0, 12, 4, 7, 14), box(12, 0, 12, 14, 7, 14));

        return switch (type) {
            case CHAIR -> Shapes.or(legsAndSeat, backrest(facing, 2, 9, 12, 14, 16, 14));
            case STOOL, BAR_STOOL -> legsAndSeat;
            case ARMCHAIR, OFFICE_CHAIR, GAMING_CHAIR -> Shapes.or(
                    box(1, 6, 1, 15, 9, 15),
                    box(1, 0, 1, 3, 6, 3), box(13, 0, 1, 15, 6, 3),
                    box(1, 0, 13, 3, 6, 15), box(13, 0, 13, 15, 6, 15),
                    backrest(facing, 1, 9, 13, 15, 16, 15), armrests(facing));
            case SOFA -> Shapes.or(
                    box(0, 6, 1, 16, 9, 15),
                    box(0, 0, 1, 2, 6, 3), box(14, 0, 1, 16, 6, 3),
                    box(0, 0, 13, 2, 6, 15), box(14, 0, 13, 16, 6, 15),
                    backrest(facing, 0, 9, 13, 16, 16, 15));
            case BOOKSHELF -> Shapes.block();
            case DINING_TABLE, COMPUTER_DESK -> Shapes.or(
                    box(0, 12, 0, 16, 14, 16),
                    box(1, 0, 1, 3, 12, 3), box(13, 0, 1, 15, 12, 3),
                    box(1, 0, 13, 3, 12, 15), box(13, 0, 13, 15, 12, 15));
            case COFFEE_TABLE -> Shapes.or(
                    box(1, 7, 1, 15, 9, 15),
                    box(2, 0, 2, 4, 7, 4), box(12, 0, 2, 14, 7, 4),
                    box(2, 0, 12, 4, 7, 14), box(12, 0, 12, 14, 7, 14));
            case COUNTERTOP -> box(0, 12, 0, 16, 16, 16);
            case MICROWAVE -> box(2, 0, 2, 14, 10, 14);
            case SINK, BATHROOM_SINK -> Shapes.or(box(0, 0, 0, 16, 12, 16), box(2, 12, 2, 14, 14, 14));
            case STOVE -> box(0, 0, 0, 16, 14, 16);
            case BATHTUB -> box(0, 0, 0, 16, 10, 16);
            case SHOWER -> box(0, 0, 0, 16, 16, 16);
            case TOILET -> Shapes.or(box(3, 0, 3, 13, 8, 13), box(4, 8, 10, 12, 14, 16));
            case TOWEL_RACK -> box(1, 10, 14, 15, 14, 16);
            case MIRROR -> box(2, 2, 14, 14, 14, 16);
            case MONITOR -> box(1, 2, 6, 15, 14, 10);
            case PC_TOWER -> box(4, 0, 2, 12, 16, 14);
            case LAPTOP -> box(2, 0, 3, 14, 2, 13);
            case KEYBOARD -> box(1, 0, 4, 15, 1, 12);
            case PRINTER -> box(2, 0, 2, 14, 8, 14);
            case SPEAKER -> box(5, 0, 5, 11, 12, 11);
        };
    }

    private static VoxelShape backrest(Direction facing, double x1, double y1, double z1, double x2, double y2, double z2) {
        return switch (facing) {
            case NORTH -> box(x1, y1, z1, x2, y2, z2);
            case SOUTH -> box(x1, y1, 16 - z2, x2, y2, 16 - z1);
            case EAST -> box(z1, y1, x1, z2, y2, x2);
            case WEST -> box(16 - z2, y1, x1, 16 - z1, y2, x2);
            default -> Shapes.empty();
        };
    }

    private static VoxelShape armrests(Direction facing) {
        return switch (facing) {
            case NORTH, SOUTH -> Shapes.or(box(1, 9, 1, 3, 13, 13), box(13, 9, 1, 15, 13, 13));
            case EAST, WEST -> Shapes.or(box(1, 9, 1, 13, 13, 3), box(1, 9, 13, 13, 13, 15));
            default -> Shapes.empty();
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        // Sitting
        if (type == Type.CHAIR || type == Type.STOOL || type == Type.ARMCHAIR || type == Type.SOFA ||
            type == Type.BAR_STOOL || type == Type.OFFICE_CHAIR || type == Type.GAMING_CHAIR) {
            if (player.isShiftKeyDown()) return InteractionResult.PASS;
            double yOffset = switch (type) {
                case ARMCHAIR, SOFA, OFFICE_CHAIR, GAMING_CHAIR -> 0.40;
                case BAR_STOOL -> 0.55;
                default -> 0.45;
            };
            if (SeatEntity.sit(level, pos, player, yOffset)) {
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            return InteractionResult.PASS;
        }
        // Computer / electronics placeholder interface
        if (type == Type.MONITOR || type == Type.LAPTOP || type == Type.PC_TOWER || type == Type.PRINTER) {
            if (!level.isClientSide) {
                player.displayClientMessage(
                        net.minecraft.network.chat.Component.translatable("message.homestead.computer"), true);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        // Bathroom water
        if (type == Type.SHOWER || type == Type.BATHTUB) {
            if (level.isClientSide) {
                for (int i = 0; i < 16; i++) {
                    double x = pos.getX() + 0.2 + level.random.nextDouble() * 0.6;
                    double y = pos.getY() + (type == Type.SHOWER ? 1.2 : 0.7) - level.random.nextDouble() * 0.3;
                    double z = pos.getZ() + 0.2 + level.random.nextDouble() * 0.6;
                    level.addParticle(ParticleTypes.SPLASH, x, y, z, 0, -0.05, 0);
                    if (i % 3 == 0) {
                        level.addParticle(ParticleTypes.FALLING_WATER, x, y + 0.3, z, 0, -0.1, 0);
                    }
                }
            } else {
                level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.6F, 1.1F);
                player.displayClientMessage(Component.translatable(
                        type == Type.SHOWER ? "message.homestead.shower_on" : "message.homestead.bath_on"), true);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (type == Type.SINK || type == Type.BATHROOM_SINK) {
            if (level.isClientSide) {
                for (int i = 0; i < 8; i++) {
                    level.addParticle(ParticleTypes.SPLASH,
                            pos.getX() + 0.4 + level.random.nextDouble() * 0.2,
                            pos.getY() + 0.9,
                            pos.getZ() + 0.4 + level.random.nextDouble() * 0.2,
                            0, 0, 0);
                }
            } else {
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 0.4F, 1.2F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (type == Type.TOILET) {
            if (!level.isClientSide) {
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.5F, 0.8F);
                player.displayClientMessage(Component.translatable("message.homestead.toilet_flush"), true);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return createShape(state); }
    @Override public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return createShape(state); }
    @Override public BlockState rotate(BlockState state, Rotation rotation) { return state.setValue(FACING, rotation.rotate(state.getValue(FACING))); }
    @Override public BlockState mirror(BlockState state, Mirror mirror) { return state.rotate(mirror.getRotation(state.getValue(FACING))); }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(FACING); }
}
