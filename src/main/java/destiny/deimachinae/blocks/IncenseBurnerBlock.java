package destiny.deimachinae.blocks;

import destiny.deimachinae.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IncenseBurnerBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 5);
    public static final VoxelShape SHAPE_NORTH_SOUTH_WEST_EAST_UP = MathUtil.buildShape(
            Block.box(5, 2, 5, 11, 4, 11)
    );
    public static final VoxelShape SHAPE_DOWN = MathUtil.buildShape(
            Block.box(5, 0, 5, 11, 2, 11)
    );

    public IncenseBurnerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, false).setValue(TYPE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT, TYPE);
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(FACING) == Direction.DOWN ? SHAPE_DOWN : SHAPE_NORTH_SOUTH_WEST_EAST_UP;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (!isOnBlock(pLevel, pPos, pState) && pDirection == pState.getValue(FACING)) {
            pLevel.destroyBlock(pPos, true);
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public boolean isOnBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        Direction blockDirection = pState.getValue(FACING);
        Block parentBlock = pLevel.getBlockState(pPos.relative(blockDirection.getOpposite())).getBlock();
        return !parentBlock.equals(Blocks.AIR);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            addParticles(pLevel, pPos.getCenter().add(0, 0.1, 0), pRandom);
        }
    }

    private static void addParticles(Level pLevel, Vec3 pOffset, RandomSource pRandom) {
        float chance = pRandom.nextFloat();
        if (chance < 0.5F) {
            pLevel.addParticle(ParticleTypes.SMOKE, pOffset.x, pOffset.y, pOffset.z, 0.0F, 0.0F, 0.0F);
//          if (chance < 0.17F) {
//              pLevel.playLocalSound(pOffset.x + 0.5F, pOffset.y + 0.5F, pOffset.z + 0.5F, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.3F, false);
//          }
        }
    }
}
