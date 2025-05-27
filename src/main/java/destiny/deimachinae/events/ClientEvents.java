package destiny.deimachinae.events;

import destiny.deimachinae.client.renderer.block.OmnissianAltarBlockRenderer;
import destiny.deimachinae.init.BlockEntityInit;
import destiny.deimachinae.init.FluidInit;
import destiny.deimachinae.init.ItemInit;
import destiny.deimachinae.items.properties.OmnispexItemProperty;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static destiny.deimachinae.DeiMachinaeMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityInit.OMNISSIAN_ALTAR.get(), context -> new OmnissianAltarBlockRenderer());
    }

    @SubscribeEvent
    public static void creativeTabs(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {}

        if(event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {}
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(FluidInit.SOURCE_OIL.get(), RenderType.solid());

        event.enqueueWork(() -> {
            ItemProperties.register(ItemInit.OMNISPEX.get(), new ResourceLocation(MODID, "omnispex_ping"), new OmnispexItemProperty());
        });
    }
}
