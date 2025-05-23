package destiny.deimachinae.init;

import destiny.deimachinae.DeiMachinaeMod;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DeiMachinaeMod.MODID);

/*    public static final RegistryObject<BlockEntityType<AncientCryptBlockEntity>> ANCIENT_CRYPT = BLOCK_ENTITIES.register("ancient_crypt",
            () -> BlockEntityType.Builder.of(AncientCryptBlockEntity::new, BlockInit.ANCIENT_CRYPT.get()).build(null));*/

    public static void register(IEventBus bus)
    {
        BLOCK_ENTITIES.register(bus);
    }
}
