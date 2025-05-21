package destiny.machinurgy.init;

import destiny.machinurgy.MachinurgyMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MachinurgyMod.MODID);

    //Materials
    public static final RegistryObject<Item> ARCHEOBRASS_PLATE = ITEMS.register("archeobrass_plate", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCHEOBRASS_INGOT = ITEMS.register("archeobrass_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCHEOBRASS_NUGGET = ITEMS.register("archeobrass_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NOCTILITH_SHARD = ITEMS.register("noctilith_shard", () -> new Item(new Item.Properties()));

    //Components
    public static final RegistryObject<Item> COGITATOR = ITEMS.register("cogitator", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOTHERBOARD = ITEMS.register("motherboard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WORN_GEARS = ITEMS.register("worn_gears", () -> new Item(new Item.Properties()));

    //Standard Template Constructs
    public static final RegistryObject<Item> STANDARD_TEMPLATE_CONSTRUCT = ITEMS.register("standard_template_construct", () -> new Item(new Item.Properties()));

    //Machine Spirit Stuff
    public static final RegistryObject<Item> STANDARD_DATA_VESSEL = ITEMS.register("standard_data_vessel", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RUDIMENTARY_DATA_VESSEL = ITEMS.register("rudimentary_data_vessel", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> OIL_FLASK = ITEMS.register("oil_flask", () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

