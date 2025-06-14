package destiny.deimachinae.init;

import destiny.deimachinae.DeiMachinaeMod;
import destiny.deimachinae.fluids.CelestialOilFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class FluidTypeInit {
    public static final ResourceLocation CELESTIAL_OIL_STILL = new ResourceLocation(DeiMachinaeMod.MODID, "block/celestial_oil_still");
    public static final ResourceLocation CELESTIAL_OIL_FLOW = new ResourceLocation(DeiMachinaeMod.MODID, "block/celestial_oil_flow");
    public static final ResourceLocation CELESTIAL_OIL_OVERLAY = new ResourceLocation(DeiMachinaeMod.MODID, "misc/celestial_oil_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, DeiMachinaeMod.MODID);

    public static final RegistryObject<FluidType> CELESTIAL_OIL_TYPE = registerFluidType("celestial_oil",
            new CelestialOilFluid(CELESTIAL_OIL_STILL, CELESTIAL_OIL_FLOW, CELESTIAL_OIL_OVERLAY, 0xFFFFFFFF,
                    new Vector3f(21f / 255f, 18f / 255f, 38f / 255f),
                    FluidType.Properties.create().lightLevel(0).viscosity(8).density(15).canExtinguish(false)));

    private static RegistryObject<FluidType> registerFluidType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
