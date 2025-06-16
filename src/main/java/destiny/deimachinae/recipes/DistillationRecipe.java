package destiny.deimachinae.recipes;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import destiny.deimachinae.DeiMachinaeMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DistillationRecipe implements Recipe<RecipeWrapper> {

    public static final Codec<Ingredient> INGREDIENT_CODEC = new PrimitiveCodec<>()
    {
        @Override
        public <T> DataResult<Ingredient> read(DynamicOps<T> ops, T input)
        {
            try
            {
                return DataResult.success(CraftingHelper.getIngredient(ops.convertTo(JsonOps.INSTANCE, input).getAsJsonObject(), false));
            } catch (JsonSyntaxException error)
            {
                return DataResult.error(error::getMessage);
            }
        }

        @Override
        public <T> T write(DynamicOps<T> ops, Ingredient value)
        {
            return JsonOps.INSTANCE.convertTo(ops, value.toJson());
        }
    };

    public static final Codec<ItemStack> STACK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(ItemStack::getItem),
            Codec.INT.fieldOf("amount").forGetter(ItemStack::getCount),
            CompoundTag.CODEC.optionalFieldOf("tag").forGetter(stack -> Optional.ofNullable(stack.getTag()))
    ).apply(instance, DistillationRecipe::createStack));

    public static ItemStack createStack(Item item, int amount, Optional<CompoundTag> tag)
    {
        CompoundTag nbt = tag.orElse(null);
        return new ItemStack(item, amount, nbt);
    }

    public static final Codec<DistillationRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            INGREDIENT_CODEC.fieldOf("ingredient").forGetter(DistillationRecipe::getInput),
            STACK_CODEC.listOf().fieldOf("result").forGetter(DistillationRecipe::getResults)
    ).apply(instance, DistillationRecipe::new));

    public ResourceLocation id;
    public Ingredient input;
    public List<ItemStack> results;
    public DistillationRecipe(Ingredient input, List<ItemStack> outputs)
    {
        this.input = input;
        this.results = outputs;
    }

    public DistillationRecipe(ResourceLocation id, Ingredient input, List<ItemStack> outputs)
    {
        this.id = id;
        this.input = input;
        this.results = outputs;
    }


    public Ingredient getInput()
    {
        return input;
    }

    public List<ItemStack> getResults()
    {
        return results;
    }

    @Override
    public boolean matches(RecipeWrapper container, Level pLevel)
    {
        //DO THIS PART
        return this.input.test(container.getInput());
    }

    @Override
    public ItemStack assemble(RecipeWrapper pContainer, RegistryAccess pRegistryAccess)
    {
        return results.get(0).copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess)
    {
        return results.get(0).copy();
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<DistillationRecipe>
    {
        public static final Type INSTANCE = new Type();
        public static final String ID = "distillation";

        private Type()
        {

        }
    }

    public static class Serializer implements RecipeSerializer<DistillationRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(DeiMachinaeMod.MODID, "distillation");

        @Override
        public DistillationRecipe fromJson(ResourceLocation recipeID, JsonObject jsonRecipe)
        {
            DistillationRecipe recipe = DistillationRecipe.CODEC.parse(JsonOps.INSTANCE, jsonRecipe)
                    .getOrThrow(false,
                            s -> {
                                throw new JsonParseException(s);
                            });

            return new DistillationRecipe(recipeID, recipe.input, recipe.results);
        }

        @Override
        public @Nullable DistillationRecipe fromNetwork(ResourceLocation recipeID, FriendlyByteBuf buffer)
        {
            Ingredient input = Ingredient.fromNetwork(buffer);
            List<ItemStack> results = buffer.readCollection(i -> new ArrayList<>(), FriendlyByteBuf::readItem);

            return new DistillationRecipe(recipeID, input, results);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DistillationRecipe recipe)
        {
            recipe.input.toNetwork(buffer);
            recipe.results.forEach(buffer::writeItem);
        }
    }
}
