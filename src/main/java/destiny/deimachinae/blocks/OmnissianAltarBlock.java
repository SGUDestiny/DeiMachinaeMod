package destiny.deimachinae.blocks;

import destiny.deimachinae.blocks.block_entities.OmnissianAltarBlockEntity;
import destiny.deimachinae.init.BlockEntityInit;
import destiny.deimachinae.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OmnissianAltarBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = MathUtil.buildShape(
            Block.box(0, 0, 0, 16, 6, 16),
            Block.box(3, 6, 3, 13, 10, 13),
            Block.box(2, 10, 2, 14, 12, 14),
            Block.box(-1, 0, -1, 3, 8, 3),
            Block.box(13, 0, -1, 17, 8, 3),
            Block.box(13, 0, 13, 17, 8, 17),
            Block.box(-1, 0, 13, 3, 8, 17)
    );

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
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof OmnissianAltarBlockEntity altar) {
                Containers.dropContents(level, pos, altar.getDroppableInventory());
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
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

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
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
