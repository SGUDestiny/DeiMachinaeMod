package destiny.deimachinae.items;

import destiny.deimachinae.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static destiny.deimachinae.blocks.AncientCryptBlock.ITEMS;
import static destiny.deimachinae.blocks.AncientCryptBlock.OPEN;

public class AncientCryptBlockItem extends BlockItem {
    public AncientCryptBlockItem(Properties pProperties) {
        super(BlockInit.ANCIENT_CRYPT.get(), pProperties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        ItemStack itemStack = pContext.getItemInHand();
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();

        if (itemStack.getTag() != null) {
            if (itemStack.getTag().get(ITEMS.getName()) != null && itemStack.getTag().get(OPEN.getName()) != null) {
                level.setBlock(pos, pState.setValue(ITEMS, itemStack.getTag().getInt(ITEMS.getName()))
                        .setValue(OPEN, itemStack.getTag().getBoolean(OPEN.getName())), 11);
            } else if (itemStack.getTag().get(ITEMS.getName()) != null) {
                level.setBlock(pos, pState.setValue(ITEMS, itemStack.getTag().getInt(ITEMS.getName())), 11);
            } else if (itemStack.getTag().get(OPEN.getName()) != null) {
                level.setBlock(pos, pState.setValue(OPEN, itemStack.getTag().getBoolean(OPEN.getName())), 11);
            }
        } else {
            level.setBlock(pos, pState, 11);
        }
        return true;
    }
}
