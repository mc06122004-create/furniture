package ru.homestead;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.homestead.registry.ModBlocks;
import ru.homestead.registry.ModItems;
import ru.homestead.registry.ModCreativeTabs;
import ru.homestead.registry.ModEntities;
import ru.homestead.registry.ModBlockEntities;
import ru.homestead.registry.ModMenus;
import ru.homestead.registry.ModSounds;
import ru.homestead.network.ModNetwork;

@Mod(Homestead.MOD_ID)
public final class Homestead {
    public static final String MOD_ID = "homestead";

    public Homestead() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        ModSounds.register(modEventBus);
        ModNetwork.register();
    }
}
