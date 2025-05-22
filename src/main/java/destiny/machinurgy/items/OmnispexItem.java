package destiny.machinurgy.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.A;

public class OmnispexItem extends Item {
    public static final String IS_ON = "is_on";
    public static final String ANIMATION_FRAME = "animation_frame";
    public static final String SELECTED_BLOCK = "selected_block";
    private int ticker = 0;

    public OmnispexItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        setDefaults(stack);

        //If not crouching, switch on state
        if (!pPlayer.isCrouching()) {
/*            boolean is_on = stack.getTag().getBoolean(IS_ON);

            stack.getTag().putBoolean(IS_ON, !is_on);
            pLevel.playSound(null, pPlayer.getOnPos().above(), SoundEvents.NOTE_BLOCK_CHIME.get(), SoundSource.PLAYERS);

            pPlayer.displayClientMessage(Component.literal("Is on: " + is_on), true);*/

            ticker = 1;

            return InteractionResultHolder.pass(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        ItemStack stack = pContext.getItemInHand();
        Player player = pContext.getPlayer();

        setDefaults(stack);

        //If crouching, select clicked block
        if (player.isCrouching()) {
            Level level = pContext.getLevel();
            BlockPos pos = pContext.getClickedPos();
            BlockState state = level.getBlockState(pos);
            String selected_block = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();

            stack.getTag().putString(SELECTED_BLOCK, selected_block);
            level.playSound(null, pos, SoundEvents.NOTE_BLOCK_CHIME.get(), SoundSource.PLAYERS);
            player.displayClientMessage(Component.literal("Selected block: " + selected_block), true);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void setDefaults(ItemStack stack) {
        if (stack.getTag() == null) {
            stack.getOrCreateTag().putBoolean(IS_ON, false);
            stack.getOrCreateTag().putInt(ANIMATION_FRAME, 0);
            stack.getOrCreateTag().putString(SELECTED_BLOCK, "");
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        animationTicker(pStack, pEntity);
    }

    //This is a fucking clusterfuck man
    public void animationTicker(ItemStack pStack, Entity pEntity) {
        if (pEntity instanceof Player player) {
            if (pStack.getTag() != null) {
                int frame = pStack.getTag().getInt(ANIMATION_FRAME);

                if (ticker != 0) {
                    if (frame == 6) {
                        ticker = 0;
                        pStack.getTag().putInt(ANIMATION_FRAME, 0);
                        return;
                    }

                    if (ticker % 10 == 0) {
                        pStack.getTag().putInt(ANIMATION_FRAME, frame + 1);
                    }

                    ticker++;

                    player.displayClientMessage(Component.literal("Ticker: " + ticker), true);
                }
            }
        }
    }
}
