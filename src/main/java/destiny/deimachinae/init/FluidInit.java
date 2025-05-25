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

    public static final RegistryObject<FlowingFluid> SOURCE_OIL = FLUIDS.register("oil",
            () -> new ForgeFlowingFluid.Source(FluidInit.OIL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_OIL = FLUIDS.register("flowing_oil",
            () -> new ForgeFlowingFluid.Flowing(FluidInit.OIL_PROPERTIES));


    public static final ForgeFlowingFluid.Properties OIL_PROPERTIES = new ForgeFlowingFluid.Properties(
            FluidTypeInit.OIL_TYPE, SOURCE_OIL, FLOWING_OIL)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(BlockInit.OIL)
            .bucket(ItemInit.OIL_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
