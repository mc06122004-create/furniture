package ru.homestead.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import ru.homestead.Homestead;
import ru.homestead.block.ApplianceBlock;
import ru.homestead.block.MediaBlock;
import ru.homestead.block.FurnitureBlock;
import ru.homestead.block.HomesteadBedBlock;
import ru.homestead.block.SideTableBlock;
import ru.homestead.block.StorageFurnitureBlock;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Homestead.MOD_ID);

    private static BlockBehaviour.Properties wood() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F).sound(SoundType.WOOD).noOcclusion();
    }
    private static BlockBehaviour.Properties metal() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.0F).sound(SoundType.METAL).noOcclusion();
    }
    private static BlockBehaviour.Properties ceramic() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).strength(1.5F).sound(SoundType.STONE).noOcclusion();
    }

    // Living / Bedroom
    public static final RegistryObject<Block> OAK_SIDE_TABLE = BLOCKS.register("oak_side_table", () -> new SideTableBlock(wood()));
    public static final RegistryObject<Block> OAK_CHAIR = BLOCKS.register("oak_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.CHAIR));
    public static final RegistryObject<Block> OAK_STOOL = BLOCKS.register("oak_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.STOOL));
    public static final RegistryObject<Block> OAK_BOOKSHELF = BLOCKS.register("oak_bookshelf", () -> new FurnitureBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0F).sound(SoundType.WOOD), FurnitureBlock.Type.BOOKSHELF));
    public static final RegistryObject<Block> OAK_DINING_TABLE = BLOCKS.register("oak_dining_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.DINING_TABLE));
    public static final RegistryObject<Block> OAK_ARMCHAIR = BLOCKS.register("oak_armchair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.ARMCHAIR));
    public static final RegistryObject<Block> OAK_SOFA = BLOCKS.register("oak_sofa", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.SOFA));
    public static final RegistryObject<Block> OAK_COFFEE_TABLE = BLOCKS.register("oak_coffee_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COFFEE_TABLE));
    public static final RegistryObject<Block> OAK_BEDSIDE_CABINET = BLOCKS.register("oak_bedside_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.BEDSIDE_CABINET));
    public static final RegistryObject<Block> OAK_COMMODE = BLOCKS.register("oak_commode", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.COMMODE));
    public static final RegistryObject<Block> OAK_SINGLE_BED = BLOCKS.register("oak_single_bed", () -> new HomesteadBedBlock(wood()));

    // Kitchen furniture
    public static final RegistryObject<Block> OAK_KITCHEN_CABINET = BLOCKS.register("oak_kitchen_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.KITCHEN_CABINET));
    public static final RegistryObject<Block> OAK_KITCHEN_CABINET_UPPER = BLOCKS.register("oak_kitchen_cabinet_upper", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.KITCHEN_CABINET_UPPER));
    public static final RegistryObject<Block> OAK_COUNTERTOP = BLOCKS.register("oak_countertop", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COUNTERTOP));
    public static final RegistryObject<Block> OAK_KITCHEN_ISLAND = BLOCKS.register("oak_kitchen_island", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.KITCHEN_ISLAND));
    public static final RegistryObject<Block> OAK_BAR_STOOL = BLOCKS.register("oak_bar_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.BAR_STOOL));
    public static final RegistryObject<Block> OAK_REFRIGERATOR = BLOCKS.register("oak_refrigerator", () -> new StorageFurnitureBlock(metal(), StorageFurnitureBlock.Type.REFRIGERATOR));
    public static final RegistryObject<Block> OAK_SINK = BLOCKS.register("oak_sink", () -> new FurnitureBlock(metal(), FurnitureBlock.Type.SINK));

    // Interactive appliances 0.9.0
    public static final RegistryObject<Block> OAK_MICROWAVE = BLOCKS.register("oak_microwave",
            () -> new ApplianceBlock(metal(), ApplianceBlock.Type.MICROWAVE));
    public static final RegistryObject<Block> OAK_STOVE = BLOCKS.register("oak_stove",
            () -> new ApplianceBlock(metal(), ApplianceBlock.Type.STOVE));
    public static final RegistryObject<Block> OAK_WASHING_MACHINE = BLOCKS.register("oak_washing_machine",
            () -> new ApplianceBlock(metal(), ApplianceBlock.Type.WASHING_MACHINE));
    public static final RegistryObject<Block> OAK_COFFEE_MACHINE = BLOCKS.register("oak_coffee_machine",
            () -> new ApplianceBlock(metal(), ApplianceBlock.Type.COFFEE_MACHINE));
    public static final RegistryObject<Block> OAK_KETTLE = BLOCKS.register("oak_kettle",
            () -> new ApplianceBlock(metal(), ApplianceBlock.Type.KETTLE));
    public static final RegistryObject<Block> OAK_LAMP = BLOCKS.register("oak_lamp",
            () -> new ApplianceBlock(metal().lightLevel(s -> s.getValue(ApplianceBlock.LIT) ? 12 : 0), ApplianceBlock.Type.LAMP));
    public static final RegistryObject<Block> OAK_SWITCH = BLOCKS.register("oak_switch",
            () -> new ApplianceBlock(metal(), ApplianceBlock.Type.SWITCH));

    // Bathroom
    public static final RegistryObject<Block> OAK_BATHTUB = BLOCKS.register("oak_bathtub", () -> new FurnitureBlock(ceramic(), FurnitureBlock.Type.BATHTUB));
    public static final RegistryObject<Block> OAK_SHOWER = BLOCKS.register("oak_shower", () -> new FurnitureBlock(ceramic(), FurnitureBlock.Type.SHOWER));
    public static final RegistryObject<Block> OAK_TOILET = BLOCKS.register("oak_toilet", () -> new FurnitureBlock(ceramic(), FurnitureBlock.Type.TOILET));
    public static final RegistryObject<Block> OAK_BATHROOM_SINK = BLOCKS.register("oak_bathroom_sink", () -> new FurnitureBlock(ceramic(), FurnitureBlock.Type.BATHROOM_SINK));
    public static final RegistryObject<Block> OAK_MIRROR = BLOCKS.register("oak_mirror", () -> new FurnitureBlock(metal(), FurnitureBlock.Type.MIRROR));
    public static final RegistryObject<Block> OAK_MIRROR_CABINET = BLOCKS.register("oak_mirror_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.MIRROR_CABINET));
    public static final RegistryObject<Block> OAK_TOWEL_RACK = BLOCKS.register("oak_towel_rack", () -> new FurnitureBlock(metal(), FurnitureBlock.Type.TOWEL_RACK));
    public static final RegistryObject<Block> OAK_LAUNDRY_BASKET = BLOCKS.register("oak_laundry_basket", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.LAUNDRY_BASKET));

    // Computer
    public static final RegistryObject<Block> OAK_COMPUTER_DESK = BLOCKS.register("oak_computer_desk", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COMPUTER_DESK));
    public static final RegistryObject<Block> OAK_OFFICE_CHAIR = BLOCKS.register("oak_office_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.OFFICE_CHAIR));
    public static final RegistryObject<Block> OAK_GAMING_CHAIR = BLOCKS.register("oak_gaming_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.GAMING_CHAIR));
    public static final RegistryObject<Block> OAK_MONITOR = BLOCKS.register("oak_monitor", () -> new FurnitureBlock(metal(), FurnitureBlock.Type.MONITOR));
    public static final RegistryObject<Block> OAK_PC_TOWER = BLOCKS.register("oak_pc_tower", () -> new FurnitureBlock(metal(), FurnitureBlock.Type.PC_TOWER));
    public static final RegistryObject<Block> OAK_LAPTOP = BLOCKS.register("oak_laptop", () -> new FurnitureBlock(metal(), FurnitureBlock.Type.LAPTOP));
    public static final RegistryObject<Block> OAK_KEYBOARD = BLOCKS.register("oak_keyboard", () -> new FurnitureBlock(metal(), FurnitureBlock.Type.KEYBOARD));
    public static final RegistryObject<Block> OAK_PRINTER = BLOCKS.register("oak_printer", () -> new FurnitureBlock(metal(), FurnitureBlock.Type.PRINTER));
    public static final RegistryObject<Block> OAK_SPEAKER = BLOCKS.register("oak_speaker", () -> new FurnitureBlock(metal(), FurnitureBlock.Type.SPEAKER));


    // Media 0.10.0
    public static final RegistryObject<Block> OAK_TELEVISION = BLOCKS.register("oak_television",
            () -> new MediaBlock(metal(), MediaBlock.Type.TELEVISION));
    public static final RegistryObject<Block> OAK_COMPUTER = BLOCKS.register("oak_computer",
            () -> new MediaBlock(metal(), MediaBlock.Type.COMPUTER));


    // Color / wood variants 0.15.0
    public static final RegistryObject<Block> SPRUCE_CHAIR = BLOCKS.register("spruce_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.CHAIR));
    public static final RegistryObject<Block> SPRUCE_STOOL = BLOCKS.register("spruce_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.STOOL));
    public static final RegistryObject<Block> SPRUCE_ARMCHAIR = BLOCKS.register("spruce_armchair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.ARMCHAIR));
    public static final RegistryObject<Block> SPRUCE_SOFA = BLOCKS.register("spruce_sofa", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.SOFA));
    public static final RegistryObject<Block> SPRUCE_DINING_TABLE = BLOCKS.register("spruce_dining_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.DINING_TABLE));
    public static final RegistryObject<Block> SPRUCE_COFFEE_TABLE = BLOCKS.register("spruce_coffee_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COFFEE_TABLE));
    public static final RegistryObject<Block> SPRUCE_BEDSIDE_CABINET = BLOCKS.register("spruce_bedside_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.BEDSIDE_CABINET));
    public static final RegistryObject<Block> BIRCH_CHAIR = BLOCKS.register("birch_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.CHAIR));
    public static final RegistryObject<Block> BIRCH_STOOL = BLOCKS.register("birch_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.STOOL));
    public static final RegistryObject<Block> BIRCH_ARMCHAIR = BLOCKS.register("birch_armchair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.ARMCHAIR));
    public static final RegistryObject<Block> BIRCH_SOFA = BLOCKS.register("birch_sofa", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.SOFA));
    public static final RegistryObject<Block> BIRCH_DINING_TABLE = BLOCKS.register("birch_dining_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.DINING_TABLE));
    public static final RegistryObject<Block> BIRCH_COFFEE_TABLE = BLOCKS.register("birch_coffee_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COFFEE_TABLE));
    public static final RegistryObject<Block> BIRCH_BEDSIDE_CABINET = BLOCKS.register("birch_bedside_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.BEDSIDE_CABINET));
    public static final RegistryObject<Block> DARK_OAK_CHAIR = BLOCKS.register("dark_oak_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.CHAIR));
    public static final RegistryObject<Block> DARK_OAK_STOOL = BLOCKS.register("dark_oak_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.STOOL));
    public static final RegistryObject<Block> DARK_OAK_ARMCHAIR = BLOCKS.register("dark_oak_armchair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.ARMCHAIR));
    public static final RegistryObject<Block> DARK_OAK_SOFA = BLOCKS.register("dark_oak_sofa", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.SOFA));
    public static final RegistryObject<Block> DARK_OAK_DINING_TABLE = BLOCKS.register("dark_oak_dining_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.DINING_TABLE));
    public static final RegistryObject<Block> DARK_OAK_COFFEE_TABLE = BLOCKS.register("dark_oak_coffee_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COFFEE_TABLE));
    public static final RegistryObject<Block> DARK_OAK_BEDSIDE_CABINET = BLOCKS.register("dark_oak_bedside_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.BEDSIDE_CABINET));
    public static final RegistryObject<Block> WHITE_CHAIR = BLOCKS.register("white_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.CHAIR));
    public static final RegistryObject<Block> WHITE_STOOL = BLOCKS.register("white_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.STOOL));
    public static final RegistryObject<Block> WHITE_ARMCHAIR = BLOCKS.register("white_armchair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.ARMCHAIR));
    public static final RegistryObject<Block> WHITE_SOFA = BLOCKS.register("white_sofa", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.SOFA));
    public static final RegistryObject<Block> WHITE_DINING_TABLE = BLOCKS.register("white_dining_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.DINING_TABLE));
    public static final RegistryObject<Block> WHITE_COFFEE_TABLE = BLOCKS.register("white_coffee_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COFFEE_TABLE));
    public static final RegistryObject<Block> WHITE_BEDSIDE_CABINET = BLOCKS.register("white_bedside_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.BEDSIDE_CABINET));
    public static final RegistryObject<Block> BLACK_CHAIR = BLOCKS.register("black_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.CHAIR));
    public static final RegistryObject<Block> BLACK_STOOL = BLOCKS.register("black_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.STOOL));
    public static final RegistryObject<Block> BLACK_ARMCHAIR = BLOCKS.register("black_armchair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.ARMCHAIR));
    public static final RegistryObject<Block> BLACK_SOFA = BLOCKS.register("black_sofa", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.SOFA));
    public static final RegistryObject<Block> BLACK_DINING_TABLE = BLOCKS.register("black_dining_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.DINING_TABLE));
    public static final RegistryObject<Block> BLACK_COFFEE_TABLE = BLOCKS.register("black_coffee_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COFFEE_TABLE));
    public static final RegistryObject<Block> BLACK_BEDSIDE_CABINET = BLOCKS.register("black_bedside_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.BEDSIDE_CABINET));
    public static final RegistryObject<Block> GRAY_CHAIR = BLOCKS.register("gray_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.CHAIR));
    public static final RegistryObject<Block> GRAY_STOOL = BLOCKS.register("gray_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.STOOL));
    public static final RegistryObject<Block> GRAY_ARMCHAIR = BLOCKS.register("gray_armchair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.ARMCHAIR));
    public static final RegistryObject<Block> GRAY_SOFA = BLOCKS.register("gray_sofa", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.SOFA));
    public static final RegistryObject<Block> GRAY_DINING_TABLE = BLOCKS.register("gray_dining_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.DINING_TABLE));
    public static final RegistryObject<Block> GRAY_COFFEE_TABLE = BLOCKS.register("gray_coffee_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COFFEE_TABLE));
    public static final RegistryObject<Block> GRAY_BEDSIDE_CABINET = BLOCKS.register("gray_bedside_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.BEDSIDE_CABINET));
    public static final RegistryObject<Block> BLUE_CHAIR = BLOCKS.register("blue_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.CHAIR));
    public static final RegistryObject<Block> BLUE_STOOL = BLOCKS.register("blue_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.STOOL));
    public static final RegistryObject<Block> BLUE_ARMCHAIR = BLOCKS.register("blue_armchair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.ARMCHAIR));
    public static final RegistryObject<Block> BLUE_SOFA = BLOCKS.register("blue_sofa", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.SOFA));
    public static final RegistryObject<Block> BLUE_DINING_TABLE = BLOCKS.register("blue_dining_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.DINING_TABLE));
    public static final RegistryObject<Block> BLUE_COFFEE_TABLE = BLOCKS.register("blue_coffee_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COFFEE_TABLE));
    public static final RegistryObject<Block> BLUE_BEDSIDE_CABINET = BLOCKS.register("blue_bedside_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.BEDSIDE_CABINET));
    public static final RegistryObject<Block> RED_CHAIR = BLOCKS.register("red_chair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.CHAIR));
    public static final RegistryObject<Block> RED_STOOL = BLOCKS.register("red_stool", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.STOOL));
    public static final RegistryObject<Block> RED_ARMCHAIR = BLOCKS.register("red_armchair", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.ARMCHAIR));
    public static final RegistryObject<Block> RED_SOFA = BLOCKS.register("red_sofa", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.SOFA));
    public static final RegistryObject<Block> RED_DINING_TABLE = BLOCKS.register("red_dining_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.DINING_TABLE));
    public static final RegistryObject<Block> RED_COFFEE_TABLE = BLOCKS.register("red_coffee_table", () -> new FurnitureBlock(wood(), FurnitureBlock.Type.COFFEE_TABLE));
    public static final RegistryObject<Block> RED_BEDSIDE_CABINET = BLOCKS.register("red_bedside_cabinet", () -> new StorageFurnitureBlock(wood(), StorageFurnitureBlock.Type.BEDSIDE_CABINET));

    private ModBlocks() {}
    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }
}
