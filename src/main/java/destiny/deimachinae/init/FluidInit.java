package destiny.deimachinae.init;

import destiny.deimachinae.DeiMachinaeMod;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluidInit {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, DeiMachinaeMod.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_CELESTIAL_OIL = FLUIDS.register("celestial_oil",
            () -> new ForgeFlowingFluid.Source(FluidInit.CELESTIAL_OIL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_CELESTIAL_OIL = FLUIDS.register("flowing_celestial_oil",
            () -> new ForgeFlowingFluid.Flowing(FluidInit.CELESTIAL_OIL_PROPERTIES));


    public static final ForgeFlowingFluid.Properties CELESTIAL_OIL_PROPERTIES = new ForgeFlowingFluid.Properties(
            FluidTypeInit.CELESTIAL_OIL_TYPE, SOURCE_CELESTIAL_OIL, FLOWING_CELESTIAL_OIL)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(BlockInit.CELESTIAL_OIL)
            .bucket(ItemInit.CELESTIAL_OIL_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
