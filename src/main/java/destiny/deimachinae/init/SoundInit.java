package destiny.deimachinae.init;

import destiny.deimachinae.DeiMachinaeMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DeiMachinaeMod.MODID);

    public static RegistryObject<SoundEvent> OMNISPEX_PING = registerSoundEvent("omnispex_ping");
    public static RegistryObject<SoundEvent> OMNISPEX_SELECT = registerSoundEvent("omnispex_select");
    public static RegistryObject<SoundEvent> TECH_ON = registerSoundEvent("tech_on");
    public static RegistryObject<SoundEvent> TECH_OFF = registerSoundEvent("tech_off");

    private static RegistryObject<SoundEvent> registerSoundEvent(String sound)
    {
        return SOUNDS.register(sound, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DeiMachinaeMod.MODID, sound)));
    }

    public static void register(IEventBus bus)
    {
        SOUNDS.register(bus);
    }
}
