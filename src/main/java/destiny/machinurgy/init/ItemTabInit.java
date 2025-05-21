package destiny.machinurgy.init;

import destiny.machinurgy.MachinurgyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemTabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MachinurgyMod.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .icon(() -> ItemInit.STANDARD_DATA_VESSEL.get().getDefaultInstance())
                    .title(Component.translatable("tab.machinurgy.main"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        //Items

                        //Materials
                        output.accept(ItemInit.ARCHEOBRASS_PLATE.get());
                        output.accept(ItemInit.ARCHEOBRASS_INGOT.get());
                        output.accept(ItemInit.ARCHEOBRASS_NUGGET.get());

                        output.accept(ItemInit.NOCTILITH_SHARD.get());

                        //Components
                        output.accept(ItemInit.COGITATOR.get());
                        output.accept(ItemInit.MOTHERBOARD.get());
                        output.accept(ItemInit.WORN_GEARS.get());

                        //Standard Template Constructs
                        output.accept(ItemInit.STANDARD_TEMPLATE_CONSTRUCT.get());

                        //Machine Spirit Stuff
                        output.accept(ItemInit.STANDARD_DATA_VESSEL.get());
                        output.accept(ItemInit.RUDIMENTARY_DATA_VESSEL.get());

                        output.accept(ItemInit.OIL_FLASK.get());



                        //Blocks

                        //Scrap Blocks
                        output.accept(BlockInit.SCRAP_PILE.get());
                    })).build());

    public static void register(IEventBus bus)
    {
        TABS.register(bus);
    }
}
