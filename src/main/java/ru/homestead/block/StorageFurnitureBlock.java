package ru.homestead.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import ru.homestead.block.entity.StorageBlockEntity;

public class StorageFurnitureBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public enum Type {
        BEDSIDE_CABINET,        // 9
        COMMODE,                // 27
        KITCHEN_CABINET,        // 27
        KITCHEN_CABINET_UPPER,  // 9
        REFRIGERATOR,           // 27
        KITCHEN_ISLAND,         // 27
        MIRROR_CABINET,         // 9
        WASHING_MACHINE,        // 9
        LAUNDRY_BASKET          // 9
    }

    private final Type type;

    public StorageFurnitureBlock(BlockBehaviour.Properties properties, Type type) {
        super(properties);
        this.type = type;
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private int getSlotCount() {
        return switch (type) {
            case BEDSIDE_CABINET, KITCHEN_CABINET_UPPER, MIRROR_CABINET, WASHING_MACHINE, LAUNDRY_BASKET -> 9;
            default -> 27;
        };
    }

    private VoxelShape createShape() {
        return switch (type) {
            case BEDSIDE_CABINET -> Shapes.or(
                    box(1, 2, 1, 15, 14, 15),
                    box(2, 0, 2, 4, 2, 4), box(12, 0, 2, 14, 2, 4),
                    box(2, 0, 12, 4, 2, 14), box(12, 0, 12, 14, 2, 14));
            case COMMODE -> Shapes.or(box(1, 0, 1, 15, 14, 15), box(0, 14, 0, 16, 16, 16));
            case KITCHEN_CABINET, KITCHEN_CABINET_UPPER -> box(0, 0, 0, 16, 16, 16);
            case REFRIGERATOR -> box(1, 0, 1, 15, 16, 15);
            case KITCHEN_ISLAND -> Shapes.or(box(0, 0, 0, 16, 14, 16), box(0, 14, 0, 16, 16, 16));
            case MIRROR_CABINET -> box(2, 2, 12, 14, 14, 16);
            case WASHING_MACHINE -> box(1, 0, 1, 15, 16, 15);
            case LAUNDRY_BASKET -> box(3, 0, 3, 13, 12, 13);
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof StorageBlockEntity storage) {
            player.openMenu(storage);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StorageBlockEntity(pos, state, getSlotCount());
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof StorageBlockEntity storage && level instanceof ServerLevel) {
                Containers.dropContents(level, pos, storage);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return createShape(); }
    @Override public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return createShape(); }
    @Override public BlockState rotate(BlockState state, Rotation rotation) { return state.setValue(FACING, rotation.rotate(state.getValue(FACING))); }
    @Override public BlockState mirror(BlockState state, Mirror mirror) { return state.rotate(mirror.getRotation(state.getValue(FACING))); }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(FACING); }
}
