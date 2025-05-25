package destiny.deimachinae.init;

import destiny.deimachinae.DeiMachinaeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemTabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DeiMachinaeMod.MODID);

    public static final RegistryObject<CreativeModeTab> TECHNOLOGY = TABS.register("technology",
            () -> CreativeModeTab.builder()
                    .icon(() -> ItemInit.OMNISPEX.get().getDefaultInstance())
                    .title(Component.translatable("tab.deimachinae.technology"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        //Tools
                        output.accept(ItemInit.OMNISPEX.get());

                        //Components
                        output.accept(ItemInit.HAMMER_ARM.get());

                        //STCs
                        output.accept(ItemInit.STANDARD_TEMPLATE_CONSTRUCT.get());

                        output.accept(ItemInit.ANCIENT_CRYPT.get());
                    })).build());

    public static final RegistryObject<CreativeModeTab> SPIRITUALISM = TABS.register("spiritualism",
            () -> CreativeModeTab.builder()
                    .icon(() -> ItemInit.STANDARD_DATA_VESSEL.get().getDefaultInstance())
                    .title(Component.translatable("tab.deimachinae.spiritualism"))
                    .withTabsBefore(TECHNOLOGY.getKey())
                    .displayItems(((itemDisplayParameters, output) -> {
                        //Data Vessels
                        output.accept(ItemInit.RUDIMENTARY_DATA_VESSEL.get());
                        output.accept(ItemInit.STANDARD_DATA_VESSEL.get());

                        output.accept(ItemInit.PURITY_SEAL.get());

                        //Materials
                        output.accept(ItemInit.OIL_FLASK.get());
                    })).build());

    public static final RegistryObject<CreativeModeTab> MATERIALS = TABS.register("materials",
            () -> CreativeModeTab.builder()
                    .icon(() -> ItemInit.NOCTILITH_SHARD.get().getDefaultInstance())
                    .title(Component.translatable("tab.deimachinae.materials"))
                    .withTabsBefore(SPIRITUALISM.getKey())
                    .displayItems(((itemDisplayParameters, output) -> {
                        //Materials
                        output.accept(ItemInit.ARCHEOBRASS_INGOT.get());
                        output.accept(ItemInit.ARCHEOBRASS_NUGGET.get());
                        output.accept(ItemInit.ARCHEOBRASS_PLATE.get());

                        output.accept(ItemInit.IRON_PLATE.get());
                        output.accept(ItemInit.COPPER_PLATE.get());

                        output.accept(ItemInit.NOCTILITH_SHARD.get());

                        //Components
                        output.accept(ItemInit.COGITATOR.get());
                        output.accept(ItemInit.MOTHERBOARD.get());
                        output.accept(ItemInit.WORN_GEARS.get());

                        //Blocks
                        output.accept(BlockInit.ARCHEOBRASS_BLOCK.get());
                        output.accept(BlockInit.SCRAP_PILE.get());
                        output.accept(BlockInit.NOCTILITH_ORE.get());
                        output.accept(ItemInit.OIL_BUCKET.get());
                    })).build());

    public static void register(IEventBus bus)
    {
        TABS.register(bus);
    }
}
