package destiny.deimachinae.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DistillationRecipe implements Recipe<RecipeWrapper> {
    public static final int MAX_RESULTS = 3;

    private final ResourceLocation id;
    private final Ingredient input;
    private final NonNullList<ItemStack> results;

    public DistillationRecipe(ResourceLocation id, Ingredient input, NonNullList<ItemStack> results) {
        this.id = id;
        this.input = input;
        this.results = results;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.input);
        return nonnulllist;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, RegistryAccess access) {
        return this.results.get(0).getStack().copy();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.results.get(0).getStack();
    }

    public List<ItemStack> getResults() {
        return getRollableResults().stream()
                .map(ItemStack::getItem)
                .collect(Collectors.toList());
    }

    public NonNullList<ItemStack> getRollableResults() {
        return this.results;
    }

    public List<ItemStack> rollResults(RandomSource rand, int fortuneLevel) {
        List<ItemStack> results = new ArrayList<>();
        NonNullList<ItemStack> rollableResults = getRollableResults();
        for (ItemStack output : rollableResults) {
            ItemStack stack = output.rollOutput(rand, fortuneLevel);
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        if (inv.isEmpty())
            return false;
        return input.test(inv.getItem(0));
    }

    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.getMaxInputCount();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.DISTILLATION.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.DISTILLATION.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistillationRecipe that = (DistillationRecipe) o;

        if (!getId().equals(that.getId())) return false;
        if (!getGroup().equals(that.getGroup())) return false;
        if (!input.equals(that.input)) return false;
        if (!getResults().equals(that.getResults())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getGroup() != null ? getGroup().hashCode() : 0);
        result = 31 * result + input.hashCode();
        result = 31 * result + getResults().hashCode();
        return result;
    }

    public static class Serializer implements RecipeSerializer<DistillationRecipe>
    {
        public Serializer() {
        }

        @Override
        public DistillationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (inputItemsIn.isEmpty()) {
                throw new JsonParseException("No ingredients for distillation recipe");
            } else if (inputItemsIn.size() > 1) {
                throw new JsonParseException("Too many ingredients for distillation recipe!");
            } else {
                final NonNullList<ItemStack> results = readResults(GsonHelper.getAsJsonArray(json, "result"));
                if (results.size() > 3) {
                    throw new JsonParseException("Too many results for distillation recipe!");
                } else {
                    return new DistillationRecipe(recipeId, inputItemsIn.get(0), results);
                }
            }
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            for (int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }
            return nonnulllist;
        }

        private static NonNullList<ItemStack> readResults(JsonArray resultArray) {
            NonNullList<ItemStack> results = NonNullList.create();
            for (JsonElement result : resultArray) {
                results.add(ItemStack.deserialize(result));
            }
            return results;
        }

        @Nullable
        @Override
        public DistillationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient inputItemIn = Ingredient.fromNetwork(buffer);
            int i = buffer.readVarInt();
            NonNullList<ItemStack> resultsIn = NonNullList.withSize(i, ItemStack.EMPTY);
            for (int j = 0; j < resultsIn.size(); ++j) {
                resultsIn.set(j, buffer.readItem());
            }
            return new DistillationRecipe(recipeId, inputItemIn, resultsIn);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DistillationRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeVarInt(recipe.results.size());
            for (ItemStack result : recipe.getResults()) {
                buffer.writeItemStack(result, false);
            }
        }
    }
}
