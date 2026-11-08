package ru.homestead.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.homestead.Homestead;
import ru.homestead.block.entity.ApplianceBlockEntity;
import ru.homestead.block.entity.MediaBlockEntity;
import ru.homestead.block.entity.StorageBlockEntity;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Homestead.MOD_ID);

    public static final RegistryObject<BlockEntityType<StorageBlockEntity>> STORAGE =
            BLOCK_ENTITIES.register("storage",
                    () -> BlockEntityType.Builder.of(StorageBlockEntity::new,
                            ModBlocks.OAK_BEDSIDE_CABINET.get(),
                            ModBlocks.OAK_COMMODE.get(),
                            ModBlocks.OAK_KITCHEN_CABINET.get(),
                            ModBlocks.OAK_KITCHEN_CABINET_UPPER.get(),
                            ModBlocks.OAK_REFRIGERATOR.get(),
                            ModBlocks.OAK_KITCHEN_ISLAND.get(),
                            ModBlocks.OAK_MIRROR_CABINET.get(),
                            ModBlocks.OAK_LAUNDRY_BASKET.get(),
                            ModBlocks.SPRUCE_BEDSIDE_CABINET.get(),
                            ModBlocks.BIRCH_BEDSIDE_CABINET.get(),
                            ModBlocks.DARK_OAK_BEDSIDE_CABINET.get(),
                            ModBlocks.WHITE_BEDSIDE_CABINET.get(),
                            ModBlocks.BLACK_BEDSIDE_CABINET.get(),
                            ModBlocks.GRAY_BEDSIDE_CABINET.get(),
                            ModBlocks.BLUE_BEDSIDE_CABINET.get(),
                            ModBlocks.RED_BEDSIDE_CABINET.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<ApplianceBlockEntity>> APPLIANCE =
            BLOCK_ENTITIES.register("appliance",
                    () -> BlockEntityType.Builder.of(ApplianceBlockEntity::new,
                            ModBlocks.OAK_MICROWAVE.get(),
                            ModBlocks.OAK_STOVE.get(),
                            ModBlocks.OAK_WASHING_MACHINE.get(),
                            ModBlocks.OAK_COFFEE_MACHINE.get(),
                            ModBlocks.OAK_KETTLE.get(),
                            ModBlocks.OAK_LAMP.get(),
                            ModBlocks.OAK_SWITCH.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<MediaBlockEntity>> MEDIA =
            BLOCK_ENTITIES.register("media",
                    () -> BlockEntityType.Builder.of(MediaBlockEntity::new,
                            ModBlocks.OAK_TELEVISION.get(),
                            ModBlocks.OAK_COMPUTER.get()
                    ).build(null));

    private ModBlockEntities() {}
    public static void register(IEventBus eventBus) { BLOCK_ENTITIES.register(eventBus); }
}
