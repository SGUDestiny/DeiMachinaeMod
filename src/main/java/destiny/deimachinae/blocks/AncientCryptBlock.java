package destiny.deimachinae.blocks;

import destiny.deimachinae.init.ItemInit;
import destiny.deimachinae.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class AncientCryptBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final IntegerProperty ITEMS = IntegerProperty.create("items", 0, 4);
    public static final IntegerProperty ATTEMPTS = IntegerProperty.create("attempts", 0, 5);

    public AncientCryptBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, OPEN, ITEMS, ATTEMPTS);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(OPEN) ? Block.box(0, 0, 0, 16, 14, 16) : Shapes.block();
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(ATTEMPTS, 0);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (!pState.getValue(OPEN)) {
            if (stack.getItem() instanceof PickaxeItem && !pPlayer.getCooldowns().isOnCooldown(stack.getItem())) {
                int attempts = pState.getValue(ATTEMPTS);

                if (!pPlayer.isCreative()) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    pPlayer.getCooldowns().addCooldown(stack.getItem(), 20);
                }

                if (attempts < 4) {
                    if (pLevel.random.nextDouble() > 0.75) {
                        if (!pLevel.isClientSide()) {
                            pLevel.setBlock(pPos, pState.setValue(OPEN, true).setValue(ITEMS, pLevel.random.nextInt(3, 5)), 2);
                        }
                        pLevel.playSound(null, pPos, SoundInit.ANCIENT_CRYPT_OPEN.get(), SoundSource.BLOCKS);
                    } else {
                        if (!pLevel.isClientSide()) {
                            pLevel.setBlock(pPos, pState.setValue(ATTEMPTS, attempts + 1), 2);
                        }
                        pLevel.playSound(null, pPos, SoundEvents.COPPER_HIT, SoundSource.BLOCKS, 1.0f, 0.8f);
                    }
                } else {
                    if (!pLevel.isClientSide()) {
                        pLevel.setBlock(pPos, pState.setValue(OPEN, true).setValue(ITEMS, pLevel.random.nextInt(3, 5)), 2);
                    }
                    pLevel.playSound(null, pPos, SoundInit.ANCIENT_CRYPT_OPEN.get(), SoundSource.BLOCKS);
                }

                return InteractionResult.SUCCESS;
            }
        } else if (pState.getValue(ITEMS) != 0 && !(stack.getItem() instanceof PickaxeItem)) {
            Vec3 spawnPos = pPos.getCenter();

            System.out.println("Stack empty:" + stack.isEmpty());

            List<ItemStack> allItems = new ArrayList<>();
            allItems.add(new ItemStack(ItemInit.COGITATOR.get(), pLevel.random.nextInt(1, 2)));
            allItems.add(new ItemStack(ItemInit.MOTHERBOARD.get(), pLevel.random.nextInt(1, 2)));
            allItems.add(new ItemStack(ItemInit.WORN_GEARS.get(), pLevel.random.nextInt(1, 3)));
            allItems.add(new ItemStack(ItemInit.ARCHEOBRASS_PLATE.get(), pLevel.random.nextInt(1, 3)));
            allItems.add(new ItemStack(ItemInit.ARCHEOBRASS_NUGGET.get(), pLevel.random.nextInt(1, 5)));
            allItems.add(new ItemStack(ItemInit.OIL_FLASK.get(), 1));
            allItems.add(new ItemStack(Items.IRON_INGOT, pLevel.random.nextInt(1, 3)));
            allItems.add(new ItemStack(Items.IRON_NUGGET, pLevel.random.nextInt(1, 5)));
            allItems.add(new ItemStack(Items.COPPER_INGOT, pLevel.random.nextInt(1, 5)));
            allItems.add(new ItemStack(Items.REDSTONE, pLevel.random.nextInt(1, 5)));

            if (!pLevel.isClientSide()) {
                ItemEntity item = new ItemEntity(pLevel, spawnPos.x, spawnPos.y + 0.5, spawnPos.z, allItems.get(pLevel.random.nextInt(0, allItems.size())));
                item.setDeltaMovement(pLevel.random.triangle(0.0F, 0.11485000171139836), pLevel.random.triangle(0.2, 0.11485000171139836), pLevel.random.triangle(0.0F, 0.11485000171139836));
                pLevel.addFreshEntity(item);
                pLevel.playSound(null, pPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0f, 0.8f);

                pLevel.setBlock(pPos, pState.setValue(ITEMS, pState.getValue(ITEMS) - 1), 2);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
