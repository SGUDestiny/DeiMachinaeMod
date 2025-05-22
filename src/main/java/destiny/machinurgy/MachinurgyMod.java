package destiny.machinurgy;

import com.mojang.logging.LogUtils;
import destiny.machinurgy.init.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MachinurgyMod.MODID)
public class MachinurgyMod {
    public static final String MODID = "machinurgy";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MachinurgyMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ItemInit.register(modEventBus);
        BlockInit.register(modEventBus);
        ItemTabInit.register(modEventBus);
        BlockEntityInit.register(modEventBus);
        SoundInit.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
