package ru.homestead.registry;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.homestead.Homestead;
import ru.homestead.menu.MediaMenu;
import ru.homestead.menu.StorageMenu;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Homestead.MOD_ID);

    public static final RegistryObject<MenuType<StorageMenu>> STORAGE =
            MENUS.register("storage", () -> IForgeMenuType.create((windowId, inv, data) -> new StorageMenu(windowId, inv)));

    public static final RegistryObject<MenuType<MediaMenu>> MEDIA =
            MENUS.register("media", () -> IForgeMenuType.create(MediaMenu::new));

    private ModMenus() {}
    public static void register(IEventBus eventBus) { MENUS.register(eventBus); }
}
