package ru.homestead.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import ru.homestead.Homestead;
import ru.homestead.registry.ModBlockEntities;
import ru.homestead.registry.ModMenus;

@Mod.EventBusSubscriber(modid = Homestead.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.STORAGE.get(), StorageScreen::new);
            MenuScreens.register(ModMenus.MEDIA.get(), MediaScreen::new);
            BlockEntityRenderers.register(ModBlockEntities.MEDIA.get(), MediaBlockRenderer::new);
        });
    }
}
