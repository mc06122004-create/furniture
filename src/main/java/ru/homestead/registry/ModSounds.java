package ru.homestead.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.homestead.Homestead;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Homestead.MOD_ID);

    public static final RegistryObject<SoundEvent> MICROWAVE_LOOP = register("microwave_loop");
    public static final RegistryObject<SoundEvent> MICROWAVE_DONE = register("microwave_done");
    public static final RegistryObject<SoundEvent> STOVE_SIZZLE = register("stove_sizzle");
    public static final RegistryObject<SoundEvent> WASHING_MACHINE_LOOP = register("washing_machine_loop");
    public static final RegistryObject<SoundEvent> KETTLE_BOIL = register("kettle_boil");
    public static final RegistryObject<SoundEvent> COFFEE_MACHINE = register("coffee_machine");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Homestead.MOD_ID, name)));
    }

    private ModSounds() {}

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
