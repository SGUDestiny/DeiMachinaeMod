package destiny.deimachinae.items.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class FuelBlockItem extends BlockItem {
    private int burnTicks = 0;

    public FuelBlockItem(Block pBlock, Properties pProperties, int burnTicks) {
        super(pBlock, pProperties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTicks;
    }
}
