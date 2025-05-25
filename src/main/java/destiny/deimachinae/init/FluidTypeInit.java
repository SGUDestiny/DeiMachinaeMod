package destiny.deimachinae.init;

import destiny.deimachinae.DeiMachinaeMod;
import destiny.deimachinae.fluids.OilFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class FluidTypeInit {
    public static final ResourceLocation OIL_STILL = new ResourceLocation(DeiMachinaeMod.MODID, "block/oil_still");
    public static final ResourceLocation OIL_FLOW = new ResourceLocation(DeiMachinaeMod.MODID, "block/oil_flow");
    public static final ResourceLocation OIL_OVERLAY = new ResourceLocation(DeiMachinaeMod.MODID, "misc/oil_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, DeiMachinaeMod.MODID);

    public static final RegistryObject<FluidType> OIL_TYPE = registerFluidType("oil",
            new OilFluid(OIL_STILL, OIL_FLOW, OIL_OVERLAY, 0xFFFFFFFF,
                    new Vector3f(21f / 255f, 18f / 255f, 38f / 255f),
                    FluidType.Properties.create().lightLevel(0).viscosity(8).density(15).canExtinguish(false)));

    private static RegistryObject<FluidType> registerFluidType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
