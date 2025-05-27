package destiny.deimachinae.blocks;

import destiny.deimachinae.blocks.block_entities.OmnissianAltarBlockEntity;
import destiny.deimachinae.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class OmnissianAltarBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public OmnissianAltarBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof OmnissianAltarBlockEntity altar) {
            ItemStack stack = pPlayer.getItemInHand(pHand);

            if (altar.inputSlot.getStackInSlot(0) == ItemStack.EMPTY) {
                ItemStack copyStack = stack.copy();
                copyStack.setCount(1);

                altar.inputSlot.setStackInSlot(0, copyStack);

                stack.shrink(1);

                return InteractionResult.SUCCESS;
            } else if (pPlayer.getItemInHand(pHand).isEmpty() && altar.inputSlot.getStackInSlot(0) != ItemStack.EMPTY) {
                ItemStack inputStack = altar.inputSlot.getStackInSlot(0).copy();

                pPlayer.addItem(inputStack);

                altar.inputSlot.setStackInSlot(0, ItemStack.EMPTY);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(FACING);
    }

    @Override
    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @org.jetbrains.annotations.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityInit.OMNISSIAN_ALTAR.get(), OmnissianAltarBlockEntity::tick);
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityInit.OMNISSIAN_ALTAR.get().create(pos, state);
    }
}
