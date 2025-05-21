package destiny.machinurgy.init;

import destiny.machinurgy.MachinurgyMod;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MachinurgyMod.MODID);

    public static void register(IEventBus bus)
    {
        BLOCK_ENTITIES.register(bus);
    }
}
