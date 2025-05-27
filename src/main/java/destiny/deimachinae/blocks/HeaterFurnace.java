package destiny.deimachinae.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class HeaterFurnace extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public HeaterFurnace(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            addParticles(pLevel, pPos, pState, pRandom);
        }
    }

    private static void addParticles(Level pLevel, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            double x = pPos.getX() + 0.5F;
            double y = pPos.getY();
            double z = pPos.getZ() + 0.5F;
            if (pRandom.nextDouble() < 0.1) {
                pLevel.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 0.5F, false);
            }

            Direction direction = pState.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double $$10 = pRandom.nextDouble() * 0.6 - 0.3;
            double $$11 = axis == Direction.Axis.X ? direction.getStepX() * 0.52 : $$10;
            double $$12 = pRandom.nextDouble() * 6.0F / 16.0F;
            double $$13 = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52 : $$10;
            pLevel.addParticle(ParticleTypes.SMOKE, x + $$11, y + $$12, z + $$13, 0.0F, 0.0F, 0.0F);
        }
    }
}
