package destiny.deimachinae.init;

import destiny.deimachinae.DeiMachinaeMod;
import destiny.deimachinae.recipes.DistillationRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypeInit
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DeiMachinaeMod.MODID);

    public static final RegistryObject<RecipeSerializer<DistillationRecipe>> TRANSMUTATION = SERIALIZERS.register("distillation", () -> DistillationRecipe.Serializer.INSTANCE);

    public static void register(IEventBus bus)
    {
        SERIALIZERS.register(bus);
    }
}
