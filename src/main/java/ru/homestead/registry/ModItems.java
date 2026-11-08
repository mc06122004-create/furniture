package ru.homestead.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import ru.homestead.Homestead;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Homestead.MOD_ID);

    public static final RegistryObject<Item> OAK_SIDE_TABLE_ITEM = ITEMS.register("oak_side_table", () -> new BlockItem(ModBlocks.OAK_SIDE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_CHAIR_ITEM = ITEMS.register("oak_chair", () -> new BlockItem(ModBlocks.OAK_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_STOOL_ITEM = ITEMS.register("oak_stool", () -> new BlockItem(ModBlocks.OAK_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_BOOKSHELF_ITEM = ITEMS.register("oak_bookshelf", () -> new BlockItem(ModBlocks.OAK_BOOKSHELF.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_DINING_TABLE_ITEM = ITEMS.register("oak_dining_table", () -> new BlockItem(ModBlocks.OAK_DINING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_ARMCHAIR_ITEM = ITEMS.register("oak_armchair", () -> new BlockItem(ModBlocks.OAK_ARMCHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_SOFA_ITEM = ITEMS.register("oak_sofa", () -> new BlockItem(ModBlocks.OAK_SOFA.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_COFFEE_TABLE_ITEM = ITEMS.register("oak_coffee_table", () -> new BlockItem(ModBlocks.OAK_COFFEE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_BEDSIDE_CABINET_ITEM = ITEMS.register("oak_bedside_cabinet", () -> new BlockItem(ModBlocks.OAK_BEDSIDE_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_COMMODE_ITEM = ITEMS.register("oak_commode", () -> new BlockItem(ModBlocks.OAK_COMMODE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_SINGLE_BED_ITEM = ITEMS.register("oak_single_bed", () -> new BlockItem(ModBlocks.OAK_SINGLE_BED.get(), new Item.Properties()));
    public static final RegistryObject<Item> CERAMIC_MUG = ITEMS.register("ceramic_mug", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> OAK_KITCHEN_CABINET_ITEM = ITEMS.register("oak_kitchen_cabinet", () -> new BlockItem(ModBlocks.OAK_KITCHEN_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_KITCHEN_CABINET_UPPER_ITEM = ITEMS.register("oak_kitchen_cabinet_upper", () -> new BlockItem(ModBlocks.OAK_KITCHEN_CABINET_UPPER.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_COUNTERTOP_ITEM = ITEMS.register("oak_countertop", () -> new BlockItem(ModBlocks.OAK_COUNTERTOP.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_KITCHEN_ISLAND_ITEM = ITEMS.register("oak_kitchen_island", () -> new BlockItem(ModBlocks.OAK_KITCHEN_ISLAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_BAR_STOOL_ITEM = ITEMS.register("oak_bar_stool", () -> new BlockItem(ModBlocks.OAK_BAR_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_REFRIGERATOR_ITEM = ITEMS.register("oak_refrigerator", () -> new BlockItem(ModBlocks.OAK_REFRIGERATOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_SINK_ITEM = ITEMS.register("oak_sink", () -> new BlockItem(ModBlocks.OAK_SINK.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_MICROWAVE_ITEM = ITEMS.register("oak_microwave", () -> new BlockItem(ModBlocks.OAK_MICROWAVE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_STOVE_ITEM = ITEMS.register("oak_stove", () -> new BlockItem(ModBlocks.OAK_STOVE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_WASHING_MACHINE_ITEM = ITEMS.register("oak_washing_machine", () -> new BlockItem(ModBlocks.OAK_WASHING_MACHINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_COFFEE_MACHINE_ITEM = ITEMS.register("oak_coffee_machine", () -> new BlockItem(ModBlocks.OAK_COFFEE_MACHINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_KETTLE_ITEM = ITEMS.register("oak_kettle", () -> new BlockItem(ModBlocks.OAK_KETTLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_LAMP_ITEM = ITEMS.register("oak_lamp", () -> new BlockItem(ModBlocks.OAK_LAMP.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_SWITCH_ITEM = ITEMS.register("oak_switch", () -> new BlockItem(ModBlocks.OAK_SWITCH.get(), new Item.Properties()));

    public static final RegistryObject<Item> OAK_BATHTUB_ITEM = ITEMS.register("oak_bathtub", () -> new BlockItem(ModBlocks.OAK_BATHTUB.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_SHOWER_ITEM = ITEMS.register("oak_shower", () -> new BlockItem(ModBlocks.OAK_SHOWER.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_TOILET_ITEM = ITEMS.register("oak_toilet", () -> new BlockItem(ModBlocks.OAK_TOILET.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_BATHROOM_SINK_ITEM = ITEMS.register("oak_bathroom_sink", () -> new BlockItem(ModBlocks.OAK_BATHROOM_SINK.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_MIRROR_ITEM = ITEMS.register("oak_mirror", () -> new BlockItem(ModBlocks.OAK_MIRROR.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_MIRROR_CABINET_ITEM = ITEMS.register("oak_mirror_cabinet", () -> new BlockItem(ModBlocks.OAK_MIRROR_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_TOWEL_RACK_ITEM = ITEMS.register("oak_towel_rack", () -> new BlockItem(ModBlocks.OAK_TOWEL_RACK.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_LAUNDRY_BASKET_ITEM = ITEMS.register("oak_laundry_basket", () -> new BlockItem(ModBlocks.OAK_LAUNDRY_BASKET.get(), new Item.Properties()));

    public static final RegistryObject<Item> OAK_COMPUTER_DESK_ITEM = ITEMS.register("oak_computer_desk", () -> new BlockItem(ModBlocks.OAK_COMPUTER_DESK.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_OFFICE_CHAIR_ITEM = ITEMS.register("oak_office_chair", () -> new BlockItem(ModBlocks.OAK_OFFICE_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_GAMING_CHAIR_ITEM = ITEMS.register("oak_gaming_chair", () -> new BlockItem(ModBlocks.OAK_GAMING_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_MONITOR_ITEM = ITEMS.register("oak_monitor", () -> new BlockItem(ModBlocks.OAK_MONITOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_PC_TOWER_ITEM = ITEMS.register("oak_pc_tower", () -> new BlockItem(ModBlocks.OAK_PC_TOWER.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_LAPTOP_ITEM = ITEMS.register("oak_laptop", () -> new BlockItem(ModBlocks.OAK_LAPTOP.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_KEYBOARD_ITEM = ITEMS.register("oak_keyboard", () -> new BlockItem(ModBlocks.OAK_KEYBOARD.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_PRINTER_ITEM = ITEMS.register("oak_printer", () -> new BlockItem(ModBlocks.OAK_PRINTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_SPEAKER_ITEM = ITEMS.register("oak_speaker", () -> new BlockItem(ModBlocks.OAK_SPEAKER.get(), new Item.Properties()));


    public static final RegistryObject<Item> OAK_TELEVISION_ITEM = ITEMS.register("oak_television", () -> new BlockItem(ModBlocks.OAK_TELEVISION.get(), new Item.Properties()));
    public static final RegistryObject<Item> OAK_COMPUTER_ITEM = ITEMS.register("oak_computer", () -> new BlockItem(ModBlocks.OAK_COMPUTER.get(), new Item.Properties()));


    // Variants 0.15.0
    public static final RegistryObject<Item> SPRUCE_CHAIR_ITEM = ITEMS.register("spruce_chair", () -> new BlockItem(ModBlocks.SPRUCE_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPRUCE_STOOL_ITEM = ITEMS.register("spruce_stool", () -> new BlockItem(ModBlocks.SPRUCE_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPRUCE_ARMCHAIR_ITEM = ITEMS.register("spruce_armchair", () -> new BlockItem(ModBlocks.SPRUCE_ARMCHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPRUCE_SOFA_ITEM = ITEMS.register("spruce_sofa", () -> new BlockItem(ModBlocks.SPRUCE_SOFA.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPRUCE_DINING_TABLE_ITEM = ITEMS.register("spruce_dining_table", () -> new BlockItem(ModBlocks.SPRUCE_DINING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPRUCE_COFFEE_TABLE_ITEM = ITEMS.register("spruce_coffee_table", () -> new BlockItem(ModBlocks.SPRUCE_COFFEE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPRUCE_BEDSIDE_CABINET_ITEM = ITEMS.register("spruce_bedside_cabinet", () -> new BlockItem(ModBlocks.SPRUCE_BEDSIDE_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> BIRCH_CHAIR_ITEM = ITEMS.register("birch_chair", () -> new BlockItem(ModBlocks.BIRCH_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> BIRCH_STOOL_ITEM = ITEMS.register("birch_stool", () -> new BlockItem(ModBlocks.BIRCH_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> BIRCH_ARMCHAIR_ITEM = ITEMS.register("birch_armchair", () -> new BlockItem(ModBlocks.BIRCH_ARMCHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> BIRCH_SOFA_ITEM = ITEMS.register("birch_sofa", () -> new BlockItem(ModBlocks.BIRCH_SOFA.get(), new Item.Properties()));
    public static final RegistryObject<Item> BIRCH_DINING_TABLE_ITEM = ITEMS.register("birch_dining_table", () -> new BlockItem(ModBlocks.BIRCH_DINING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> BIRCH_COFFEE_TABLE_ITEM = ITEMS.register("birch_coffee_table", () -> new BlockItem(ModBlocks.BIRCH_COFFEE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> BIRCH_BEDSIDE_CABINET_ITEM = ITEMS.register("birch_bedside_cabinet", () -> new BlockItem(ModBlocks.BIRCH_BEDSIDE_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARK_OAK_CHAIR_ITEM = ITEMS.register("dark_oak_chair", () -> new BlockItem(ModBlocks.DARK_OAK_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARK_OAK_STOOL_ITEM = ITEMS.register("dark_oak_stool", () -> new BlockItem(ModBlocks.DARK_OAK_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARK_OAK_ARMCHAIR_ITEM = ITEMS.register("dark_oak_armchair", () -> new BlockItem(ModBlocks.DARK_OAK_ARMCHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARK_OAK_SOFA_ITEM = ITEMS.register("dark_oak_sofa", () -> new BlockItem(ModBlocks.DARK_OAK_SOFA.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARK_OAK_DINING_TABLE_ITEM = ITEMS.register("dark_oak_dining_table", () -> new BlockItem(ModBlocks.DARK_OAK_DINING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARK_OAK_COFFEE_TABLE_ITEM = ITEMS.register("dark_oak_coffee_table", () -> new BlockItem(ModBlocks.DARK_OAK_COFFEE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DARK_OAK_BEDSIDE_CABINET_ITEM = ITEMS.register("dark_oak_bedside_cabinet", () -> new BlockItem(ModBlocks.DARK_OAK_BEDSIDE_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_CHAIR_ITEM = ITEMS.register("white_chair", () -> new BlockItem(ModBlocks.WHITE_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_STOOL_ITEM = ITEMS.register("white_stool", () -> new BlockItem(ModBlocks.WHITE_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_ARMCHAIR_ITEM = ITEMS.register("white_armchair", () -> new BlockItem(ModBlocks.WHITE_ARMCHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_SOFA_ITEM = ITEMS.register("white_sofa", () -> new BlockItem(ModBlocks.WHITE_SOFA.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_DINING_TABLE_ITEM = ITEMS.register("white_dining_table", () -> new BlockItem(ModBlocks.WHITE_DINING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_COFFEE_TABLE_ITEM = ITEMS.register("white_coffee_table", () -> new BlockItem(ModBlocks.WHITE_COFFEE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_BEDSIDE_CABINET_ITEM = ITEMS.register("white_bedside_cabinet", () -> new BlockItem(ModBlocks.WHITE_BEDSIDE_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_CHAIR_ITEM = ITEMS.register("black_chair", () -> new BlockItem(ModBlocks.BLACK_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_STOOL_ITEM = ITEMS.register("black_stool", () -> new BlockItem(ModBlocks.BLACK_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_ARMCHAIR_ITEM = ITEMS.register("black_armchair", () -> new BlockItem(ModBlocks.BLACK_ARMCHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_SOFA_ITEM = ITEMS.register("black_sofa", () -> new BlockItem(ModBlocks.BLACK_SOFA.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_DINING_TABLE_ITEM = ITEMS.register("black_dining_table", () -> new BlockItem(ModBlocks.BLACK_DINING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_COFFEE_TABLE_ITEM = ITEMS.register("black_coffee_table", () -> new BlockItem(ModBlocks.BLACK_COFFEE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_BEDSIDE_CABINET_ITEM = ITEMS.register("black_bedside_cabinet", () -> new BlockItem(ModBlocks.BLACK_BEDSIDE_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_CHAIR_ITEM = ITEMS.register("gray_chair", () -> new BlockItem(ModBlocks.GRAY_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_STOOL_ITEM = ITEMS.register("gray_stool", () -> new BlockItem(ModBlocks.GRAY_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_ARMCHAIR_ITEM = ITEMS.register("gray_armchair", () -> new BlockItem(ModBlocks.GRAY_ARMCHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_SOFA_ITEM = ITEMS.register("gray_sofa", () -> new BlockItem(ModBlocks.GRAY_SOFA.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_DINING_TABLE_ITEM = ITEMS.register("gray_dining_table", () -> new BlockItem(ModBlocks.GRAY_DINING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_COFFEE_TABLE_ITEM = ITEMS.register("gray_coffee_table", () -> new BlockItem(ModBlocks.GRAY_COFFEE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_BEDSIDE_CABINET_ITEM = ITEMS.register("gray_bedside_cabinet", () -> new BlockItem(ModBlocks.GRAY_BEDSIDE_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_CHAIR_ITEM = ITEMS.register("blue_chair", () -> new BlockItem(ModBlocks.BLUE_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_STOOL_ITEM = ITEMS.register("blue_stool", () -> new BlockItem(ModBlocks.BLUE_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_ARMCHAIR_ITEM = ITEMS.register("blue_armchair", () -> new BlockItem(ModBlocks.BLUE_ARMCHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_SOFA_ITEM = ITEMS.register("blue_sofa", () -> new BlockItem(ModBlocks.BLUE_SOFA.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_DINING_TABLE_ITEM = ITEMS.register("blue_dining_table", () -> new BlockItem(ModBlocks.BLUE_DINING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_COFFEE_TABLE_ITEM = ITEMS.register("blue_coffee_table", () -> new BlockItem(ModBlocks.BLUE_COFFEE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_BEDSIDE_CABINET_ITEM = ITEMS.register("blue_bedside_cabinet", () -> new BlockItem(ModBlocks.BLUE_BEDSIDE_CABINET.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_CHAIR_ITEM = ITEMS.register("red_chair", () -> new BlockItem(ModBlocks.RED_CHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_STOOL_ITEM = ITEMS.register("red_stool", () -> new BlockItem(ModBlocks.RED_STOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_ARMCHAIR_ITEM = ITEMS.register("red_armchair", () -> new BlockItem(ModBlocks.RED_ARMCHAIR.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_SOFA_ITEM = ITEMS.register("red_sofa", () -> new BlockItem(ModBlocks.RED_SOFA.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_DINING_TABLE_ITEM = ITEMS.register("red_dining_table", () -> new BlockItem(ModBlocks.RED_DINING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_COFFEE_TABLE_ITEM = ITEMS.register("red_coffee_table", () -> new BlockItem(ModBlocks.RED_COFFEE_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_BEDSIDE_CABINET_ITEM = ITEMS.register("red_bedside_cabinet", () -> new BlockItem(ModBlocks.RED_BEDSIDE_CABINET.get(), new Item.Properties()));

    private ModItems() {}
    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }
}
