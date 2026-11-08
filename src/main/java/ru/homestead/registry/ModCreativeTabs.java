package ru.homestead.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import ru.homestead.Homestead;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Homestead.MOD_ID);

    public static final RegistryObject<CreativeModeTab> HOMESTEAD_TAB = TABS.register("homestead", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.homestead"))
            .icon(() -> new ItemStack(ModItems.OAK_CHAIR_ITEM.get()))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.OAK_SIDE_TABLE_ITEM.get());
                output.accept(ModItems.OAK_CHAIR_ITEM.get());
                output.accept(ModItems.OAK_STOOL_ITEM.get());
                output.accept(ModItems.OAK_ARMCHAIR_ITEM.get());
                output.accept(ModItems.OAK_SOFA_ITEM.get());
                output.accept(ModItems.OAK_COFFEE_TABLE_ITEM.get());
                output.accept(ModItems.OAK_DINING_TABLE_ITEM.get());
                output.accept(ModItems.OAK_BEDSIDE_CABINET_ITEM.get());
                output.accept(ModItems.OAK_COMMODE_ITEM.get());
                output.accept(ModItems.OAK_BOOKSHELF_ITEM.get());
                output.accept(ModItems.OAK_SINGLE_BED_ITEM.get());
                output.accept(ModItems.OAK_KITCHEN_CABINET_ITEM.get());
                output.accept(ModItems.OAK_KITCHEN_CABINET_UPPER_ITEM.get());
                output.accept(ModItems.OAK_COUNTERTOP_ITEM.get());
                output.accept(ModItems.OAK_KITCHEN_ISLAND_ITEM.get());
                output.accept(ModItems.OAK_BAR_STOOL_ITEM.get());
                output.accept(ModItems.OAK_REFRIGERATOR_ITEM.get());
                output.accept(ModItems.OAK_SINK_ITEM.get());
                output.accept(ModItems.OAK_MICROWAVE_ITEM.get());
                output.accept(ModItems.OAK_STOVE_ITEM.get());
                output.accept(ModItems.OAK_WASHING_MACHINE_ITEM.get());
                output.accept(ModItems.OAK_COFFEE_MACHINE_ITEM.get());
                output.accept(ModItems.OAK_KETTLE_ITEM.get());
                output.accept(ModItems.OAK_LAMP_ITEM.get());
                output.accept(ModItems.OAK_SWITCH_ITEM.get());
                output.accept(ModItems.OAK_BATHTUB_ITEM.get());
                output.accept(ModItems.OAK_SHOWER_ITEM.get());
                output.accept(ModItems.OAK_TOILET_ITEM.get());
                output.accept(ModItems.OAK_BATHROOM_SINK_ITEM.get());
                output.accept(ModItems.OAK_MIRROR_ITEM.get());
                output.accept(ModItems.OAK_MIRROR_CABINET_ITEM.get());
                output.accept(ModItems.OAK_TOWEL_RACK_ITEM.get());
                output.accept(ModItems.OAK_LAUNDRY_BASKET_ITEM.get());
                output.accept(ModItems.OAK_COMPUTER_DESK_ITEM.get());
                output.accept(ModItems.OAK_OFFICE_CHAIR_ITEM.get());
                output.accept(ModItems.OAK_GAMING_CHAIR_ITEM.get());
                output.accept(ModItems.OAK_MONITOR_ITEM.get());
                output.accept(ModItems.OAK_PC_TOWER_ITEM.get());
                output.accept(ModItems.OAK_LAPTOP_ITEM.get());
                output.accept(ModItems.OAK_KEYBOARD_ITEM.get());
                output.accept(ModItems.OAK_PRINTER_ITEM.get());
                output.accept(ModItems.OAK_SPEAKER_ITEM.get());
                output.accept(ModItems.OAK_TELEVISION_ITEM.get());
                output.accept(ModItems.OAK_COMPUTER_ITEM.get());
                output.accept(ModItems.SPRUCE_CHAIR_ITEM.get());
                output.accept(ModItems.SPRUCE_STOOL_ITEM.get());
                output.accept(ModItems.SPRUCE_ARMCHAIR_ITEM.get());
                output.accept(ModItems.SPRUCE_SOFA_ITEM.get());
                output.accept(ModItems.SPRUCE_DINING_TABLE_ITEM.get());
                output.accept(ModItems.SPRUCE_COFFEE_TABLE_ITEM.get());
                output.accept(ModItems.SPRUCE_BEDSIDE_CABINET_ITEM.get());
                output.accept(ModItems.BIRCH_CHAIR_ITEM.get());
                output.accept(ModItems.BIRCH_STOOL_ITEM.get());
                output.accept(ModItems.BIRCH_ARMCHAIR_ITEM.get());
                output.accept(ModItems.BIRCH_SOFA_ITEM.get());
                output.accept(ModItems.BIRCH_DINING_TABLE_ITEM.get());
                output.accept(ModItems.BIRCH_COFFEE_TABLE_ITEM.get());
                output.accept(ModItems.BIRCH_BEDSIDE_CABINET_ITEM.get());
                output.accept(ModItems.DARK_OAK_CHAIR_ITEM.get());
                output.accept(ModItems.DARK_OAK_STOOL_ITEM.get());
                output.accept(ModItems.DARK_OAK_ARMCHAIR_ITEM.get());
                output.accept(ModItems.DARK_OAK_SOFA_ITEM.get());
                output.accept(ModItems.DARK_OAK_DINING_TABLE_ITEM.get());
                output.accept(ModItems.DARK_OAK_COFFEE_TABLE_ITEM.get());
                output.accept(ModItems.DARK_OAK_BEDSIDE_CABINET_ITEM.get());
                output.accept(ModItems.WHITE_CHAIR_ITEM.get());
                output.accept(ModItems.WHITE_STOOL_ITEM.get());
                output.accept(ModItems.WHITE_ARMCHAIR_ITEM.get());
                output.accept(ModItems.WHITE_SOFA_ITEM.get());
                output.accept(ModItems.WHITE_DINING_TABLE_ITEM.get());
                output.accept(ModItems.WHITE_COFFEE_TABLE_ITEM.get());
                output.accept(ModItems.WHITE_BEDSIDE_CABINET_ITEM.get());
                output.accept(ModItems.BLACK_CHAIR_ITEM.get());
                output.accept(ModItems.BLACK_STOOL_ITEM.get());
                output.accept(ModItems.BLACK_ARMCHAIR_ITEM.get());
                output.accept(ModItems.BLACK_SOFA_ITEM.get());
                output.accept(ModItems.BLACK_DINING_TABLE_ITEM.get());
                output.accept(ModItems.BLACK_COFFEE_TABLE_ITEM.get());
                output.accept(ModItems.BLACK_BEDSIDE_CABINET_ITEM.get());
                output.accept(ModItems.GRAY_CHAIR_ITEM.get());
                output.accept(ModItems.GRAY_STOOL_ITEM.get());
                output.accept(ModItems.GRAY_ARMCHAIR_ITEM.get());
                output.accept(ModItems.GRAY_SOFA_ITEM.get());
                output.accept(ModItems.GRAY_DINING_TABLE_ITEM.get());
                output.accept(ModItems.GRAY_COFFEE_TABLE_ITEM.get());
                output.accept(ModItems.GRAY_BEDSIDE_CABINET_ITEM.get());
                output.accept(ModItems.BLUE_CHAIR_ITEM.get());
                output.accept(ModItems.BLUE_STOOL_ITEM.get());
                output.accept(ModItems.BLUE_ARMCHAIR_ITEM.get());
                output.accept(ModItems.BLUE_SOFA_ITEM.get());
                output.accept(ModItems.BLUE_DINING_TABLE_ITEM.get());
                output.accept(ModItems.BLUE_COFFEE_TABLE_ITEM.get());
                output.accept(ModItems.BLUE_BEDSIDE_CABINET_ITEM.get());
                output.accept(ModItems.RED_CHAIR_ITEM.get());
                output.accept(ModItems.RED_STOOL_ITEM.get());
                output.accept(ModItems.RED_ARMCHAIR_ITEM.get());
                output.accept(ModItems.RED_SOFA_ITEM.get());
                output.accept(ModItems.RED_DINING_TABLE_ITEM.get());
                output.accept(ModItems.RED_COFFEE_TABLE_ITEM.get());
                output.accept(ModItems.RED_BEDSIDE_CABINET_ITEM.get());
                output.accept(ModItems.CERAMIC_MUG.get());
            }).build());

    private ModCreativeTabs() {}
    public static void register(IEventBus eventBus) { TABS.register(eventBus); }
}
