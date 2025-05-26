package destiny.deimachinae.init;

import destiny.deimachinae.DeiMachinaeMod;
import destiny.deimachinae.items.*;
import destiny.deimachinae.items.block.AncientCryptBlockItem;
import destiny.deimachinae.items.block.FuelBlockItem;
import destiny.deimachinae.items.block.HorizontalWallBlockItem;
import destiny.deimachinae.items.block.IncenseBurnerBlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DeiMachinaeMod.MODID);

    //Tools
    public static final RegistryObject<Item> OMNISPEX = ITEMS.register("omnispex", () -> new OmnispexItem(new Item.Properties().stacksTo(1)));

    //Materials
    public static final RegistryObject<Item> ARCHEOBRASS_PLATE = ITEMS.register("archeobrass_plate", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARCHEOBRASS_INGOT = ITEMS.register("archeobrass_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCHEOBRASS_NUGGET = ITEMS.register("archeobrass_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NOCTILITH_SHARD = ITEMS.register("noctilith_shard", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OIL_COKE = ITEMS.register("oil_coke", () -> new FuelItem(new Item.Properties(), 3200));
    public static final RegistryObject<Item> OIL_COKE_BLOCK = ITEMS.register("oil_coke_block", () -> new FuelBlockItem(BlockInit.OIL_COKE_BLOCK.get(), new Item.Properties(), 28800));
    public static final RegistryObject<Item> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new BucketItem(FluidInit.SOURCE_OIL.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

    //Components
    public static final RegistryObject<Item> COGITATOR = ITEMS.register("cogitator", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOTHERBOARD = ITEMS.register("motherboard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WORN_GEARS = ITEMS.register("worn_gears", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_FILAMENT = ITEMS.register("redstone_filament", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HAMMER_ARM = ITEMS.register("hammer_arm", () -> new Item(new Item.Properties().stacksTo(1)));

    //Standard Template Constructs
    public static final RegistryObject<Item> STANDARD_TEMPLATE_CONSTRUCT = ITEMS.register("standard_template_construct", () -> new Item(new Item.Properties()));

    //Machine Spirit Stuff
    public static final RegistryObject<Item> STANDARD_DATA_VESSEL = ITEMS.register("standard_data_vessel", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RUDIMENTARY_DATA_VESSEL = ITEMS.register("rudimentary_data_vessel", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PURITY_SEAL = ITEMS.register("purity_seal", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OIL_FLASK = ITEMS.register("oil_flask", () -> new Item(new Item.Properties()));

    //Block Items
    public static final RegistryObject<Item> ANCIENT_CRYPT = ITEMS.register("ancient_crypt", () -> new AncientCryptBlockItem(new Item.Properties()));
    public static final RegistryObject<Item> IMPERIAL_AQUILA = ITEMS.register("imperial_aquila", () -> new HorizontalWallBlockItem(BlockInit.IMPERIAL_AQUILA.get(), new Item.Properties()));
    public static final RegistryObject<Item> COG_MECHANICUM = ITEMS.register("cog_mechanicum", () -> new HorizontalWallBlockItem(BlockInit.COG_MECHANICUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> INCENSE_BURNER = ITEMS.register("incense_burner", () -> new IncenseBurnerBlockItem(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

