package destiny.deimachinae.items.block_items;

import destiny.deimachinae.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class IncenseBurnerBlockItem extends BlockItem {
    public IncenseBurnerBlockItem(Properties pProperties) {
        super(BlockInit.INCENSE_BURNER.get(), pProperties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        return isOnBlock(pContext.getLevel(), pContext.getClickedPos(), pContext.getClickedFace().getOpposite());
    }

    public boolean isOnBlock(Level pLevel, BlockPos pPos, Direction blockDirection) {
        Block parentBlock = pLevel.getBlockState(pPos.relative(blockDirection)).getBlock();
        return !parentBlock.equals(Blocks.AIR);
    }
}
