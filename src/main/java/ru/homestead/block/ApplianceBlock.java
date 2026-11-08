package ru.homestead.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import ru.homestead.block.entity.ApplianceBlockEntity;
import ru.homestead.registry.ModBlockEntities;
import ru.homestead.registry.ModBlocks;
import ru.homestead.registry.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;

import java.util.HashMap;
import java.util.Map;

public class ApplianceBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public enum Type {
        MICROWAVE, STOVE, WASHING_MACHINE, COFFEE_MACHINE, KETTLE, LAMP, SWITCH
    }

    private final Type type;

    // Simple cooking recipes: raw -> cooked
    private static final Map<net.minecraft.world.item.Item, net.minecraft.world.item.Item> COOK_RECIPES = new HashMap<>();
    static {
        COOK_RECIPES.put(Items.BEEF, Items.COOKED_BEEF);
        COOK_RECIPES.put(Items.PORKCHOP, Items.COOKED_PORKCHOP);
        COOK_RECIPES.put(Items.CHICKEN, Items.COOKED_CHICKEN);
        COOK_RECIPES.put(Items.MUTTON, Items.COOKED_MUTTON);
        COOK_RECIPES.put(Items.RABBIT, Items.COOKED_RABBIT);
        COOK_RECIPES.put(Items.COD, Items.COOKED_COD);
        COOK_RECIPES.put(Items.SALMON, Items.COOKED_SALMON);
        COOK_RECIPES.put(Items.POTATO, Items.BAKED_POTATO);
        COOK_RECIPES.put(Items.KELP, Items.DRIED_KELP);
        // extra smeltables
        COOK_RECIPES.put(Items.WET_SPONGE, Items.SPONGE);
        COOK_RECIPES.put(Items.CACTUS, Items.GREEN_DYE);
        COOK_RECIPES.put(Items.SEA_PICKLE, Items.LIME_DYE);
        COOK_RECIPES.put(Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT);
    }

    public ApplianceBlock(BlockBehaviour.Properties properties, Type type) {
        super(properties);
        this.type = type;
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false));
    }

    public Type getApplianceType() { return type; }

    private VoxelShape createShape() {
        return switch (type) {
            case MICROWAVE -> box(2, 0, 2, 14, 10, 14);
            case STOVE -> box(0, 0, 0, 16, 14, 16);
            case WASHING_MACHINE -> box(1, 0, 1, 15, 16, 15);
            case COFFEE_MACHINE -> box(4, 0, 4, 12, 12, 12);
            case KETTLE -> box(5, 0, 5, 11, 10, 11);
            case LAMP -> box(5, 0, 5, 11, 14, 11);
            case SWITCH -> box(5, 4, 14, 11, 10, 16);
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof ApplianceBlockEntity appliance)) return InteractionResult.PASS;

        ItemStack held = player.getItemInHand(hand);

        switch (type) {
            case MICROWAVE, STOVE -> {
                return handleCooking(level, pos, state, player, held, appliance, hand);
            }
            case WASHING_MACHINE -> {
                if (appliance.isPowered()) {
                    appliance.setPowered(false);
                    level.setBlock(pos, state.setValue(LIT, false), 3);
                    player.displayClientMessage(Component.translatable("message.homestead.washer_stop"), true);
                } else {
                    appliance.startTimer(30);
                    level.setBlock(pos, state.setValue(LIT, true), 3);
                    level.playSound(null, pos, ModSounds.WASHING_MACHINE_LOOP.get(), SoundSource.BLOCKS, 0.7F, 1.0F);
                    player.displayClientMessage(Component.translatable("message.homestead.washer_start"), true);
                }
                return InteractionResult.CONSUME;
            }
            case COFFEE_MACHINE, KETTLE -> {
                appliance.toggle();
                boolean on = appliance.isPowered();
                level.setBlock(pos, state.setValue(LIT, on), 3);
                if (on) {
                    if (type == Type.KETTLE) {
                        level.playSound(null, pos, ModSounds.KETTLE_BOIL.get(), SoundSource.BLOCKS, 0.9F, 1.0F);
                    } else if (type == Type.COFFEE_MACHINE) {
                        level.playSound(null, pos, ModSounds.COFFEE_MACHINE.get(), SoundSource.BLOCKS, 0.9F, 1.0F);
                    }
                }
                player.displayClientMessage(Component.translatable(on ? "message.homestead.appliance_on" : "message.homestead.appliance_off"), true);
                return InteractionResult.CONSUME;
            }
            case LAMP -> {
                appliance.toggle();
                boolean on = appliance.isPowered();
                level.setBlock(pos, state.setValue(LIT, on), 3);
                level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.25F, on ? 1.0F : 0.8F);
                player.displayClientMessage(Component.translatable(on ? "message.homestead.lamp_on" : "message.homestead.lamp_off"), true);
                return InteractionResult.CONSUME;
            }
            case SWITCH -> {
                level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.6F);
                // Toggle all lamps in 8-block radius
                int toggled = 0;
                BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
                for (int dx = -8; dx <= 8; dx++) {
                    for (int dy = -3; dy <= 3; dy++) {
                        for (int dz = -8; dz <= 8; dz++) {
                            cursor.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                            BlockState s = level.getBlockState(cursor);
                            if (s.is(ModBlocks.OAK_LAMP.get())) {
                                BlockEntity lampBe = level.getBlockEntity(cursor);
                                if (lampBe instanceof ApplianceBlockEntity lamp) {
                                    lamp.toggle();
                                    level.setBlock(cursor, s.setValue(LIT, lamp.isPowered()), 3);
                                    toggled++;
                                }
                            }
                        }
                    }
                }
                player.displayClientMessage(Component.translatable("message.homestead.switch_toggled", toggled), true);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    private InteractionResult handleCooking(Level level, BlockPos pos, BlockState state, Player player,
                                            ItemStack held, ApplianceBlockEntity appliance, InteractionHand hand) {
        // If cooking finished — take result
        if (!appliance.isPowered() && appliance.hasCookingItem()) {
            ItemStack result = appliance.finishAndTakeResult();
            if (!result.isEmpty()) {
                if (!player.getInventory().add(result)) {
                    level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, result));
                }
                level.setBlock(pos, state.setValue(LIT, false), 3);
                player.displayClientMessage(Component.translatable("message.homestead.food_ready"), true);
            }
            return InteractionResult.CONSUME;
        }

        // If currently cooking — stop and return raw item
        if (appliance.isPowered() && appliance.hasCookingItem()) {
            ItemStack raw = appliance.getCookingItem().copy();
            appliance.clearCooking();
            level.setBlock(pos, state.setValue(LIT, false), 3);
            if (!raw.isEmpty() && !player.getInventory().add(raw)) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, raw));
            }
            player.displayClientMessage(Component.translatable("message.homestead.cooking_cancelled"), true);
            return InteractionResult.CONSUME;
        }

        // Start cooking if holding cookable food
        if (!held.isEmpty() && COOK_RECIPES.containsKey(held.getItem())) {
            ItemStack result = new ItemStack(COOK_RECIPES.get(held.getItem()));
            int seconds = (type == Type.MICROWAVE) ? 8 : 12;
            appliance.startCooking(held, result, seconds);
            held.shrink(1);
            level.setBlock(pos, state.setValue(LIT, true), 3);
            if (type == Type.MICROWAVE) {
                level.playSound(null, pos, ModSounds.MICROWAVE_LOOP.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
            } else if (type == Type.STOVE) {
                level.playSound(null, pos, ModSounds.STOVE_SIZZLE.get(), SoundSource.BLOCKS, 0.9F, 1.0F);
            }
            player.displayClientMessage(Component.translatable(
                    type == Type.MICROWAVE ? "message.homestead.microwave_cooking" : "message.homestead.stove_cooking"), true);
            return InteractionResult.CONSUME;
        }

        // Empty hand on empty appliance
        player.displayClientMessage(Component.translatable("message.homestead.need_food"), true);
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ApplianceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> t) {
        return createTickerHelper(t, ModBlockEntities.APPLIANCE.get(), ApplianceBlockEntity::tick);
    }

    @Override public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(LIT)) return 0;
        return switch (type) {
            case LAMP -> 12;
            case STOVE, MICROWAVE -> 8;
            default -> 4;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LIT, false);
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return createShape(); }
    @Override public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return createShape(); }
    @Override public BlockState rotate(BlockState state, Rotation rotation) { return state.setValue(FACING, rotation.rotate(state.getValue(FACING))); }
    @Override public BlockState mirror(BlockState state, Mirror mirror) { return state.rotate(mirror.getRotation(state.getValue(FACING))); }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(FACING, LIT); }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT)) return;
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.8;
        double z = pos.getZ() + 0.5;
        switch (type) {
            case STOVE -> {
                level.addParticle(ParticleTypes.FLAME, x + (random.nextDouble() - 0.5) * 0.4, y, z + (random.nextDouble() - 0.5) * 0.4, 0, 0.01, 0);
                level.addParticle(ParticleTypes.SMOKE, x, y + 0.2, z, 0, 0.02, 0);
            }
            case MICROWAVE, COFFEE_MACHINE, KETTLE -> {
                if (random.nextInt(3) == 0) {
                    level.addParticle(ParticleTypes.CLOUD, x, y + 0.1, z, 0, 0.02, 0);
                }
            }
            case WASHING_MACHINE -> {
                if (random.nextInt(2) == 0) {
                    level.addParticle(ParticleTypes.SPLASH, x + (random.nextDouble()-0.5)*0.3, y, z + (random.nextDouble()-0.5)*0.3, 0, 0, 0);
                }
            }
            case LAMP -> {
                // soft glow handled by light level
            }
            default -> {}
        }
    }

}
