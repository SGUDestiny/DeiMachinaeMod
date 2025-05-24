package destiny.deimachinae.items;

import destiny.deimachinae.init.SoundInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OmnispexItem extends Item {
    public static final String POWER = "power";
    public static final String ANIMATION_FRAME = "animation_frame";
    public static final String DISTANCE = "distance";
    public static final String SELECTED_BLOCK = "selected_block";
    private int animationTicker = 0;
    private int searchTicker = 0;
    private int maxDistance = 64;

    public OmnispexItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        setDefaults(stack);

        //If not crouching, switch on state
        if (!pPlayer.isCrouching()) {
            boolean power = !stack.getTag().getBoolean(POWER);
            stack.getTag().putBoolean(POWER, power);

            if (power) {
                pLevel.playSound(null, pPlayer.getOnPos().above(), SoundInit.TECH_ON.get(), SoundSource.PLAYERS);
                pPlayer.displayClientMessage(Component.literal("Device activated"), true);
            } else {
                pLevel.playSound(null, pPlayer.getOnPos().above(), SoundInit.TECH_OFF.get(), SoundSource.PLAYERS);
                pPlayer.displayClientMessage(Component.literal("Device deactivated"), true);
            }

            return InteractionResultHolder.success(stack);
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
            level.playSound(null, pos, SoundInit.OMNISPEX_SELECT.get(), SoundSource.PLAYERS);
            player.displayClientMessage(Component.literal("Selected block: " + selected_block), true);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void setDefaults(ItemStack stack) {
        if (stack.getTag() == null) {
            stack.getOrCreateTag().putBoolean(POWER, false);
            stack.getOrCreateTag().putInt(ANIMATION_FRAME, 0);
            stack.getOrCreateTag().putDouble(DISTANCE, 0);
            stack.getOrCreateTag().putString(SELECTED_BLOCK, "");
        }
    }

    private void createPing(ItemStack stack, Level level, BlockPos pos) {
        if (level instanceof ServerLevel) {
            double distance = stack.getTag().getDouble(DISTANCE);
            float pitch = distance == 0 ? 1.0f : Mth.lerp(1.0f - ((float) distance / maxDistance), 0.2f, 2.0f);

            level.playSound(null, pos, SoundInit.OMNISPEX_PING.get(), SoundSource.PLAYERS, 1, pitch);

            stack.getTag().putInt(ANIMATION_FRAME, 1);
            animationTicker = 1;
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getTag() != null) {
            String selected_block = pStack.getTag().getString(SELECTED_BLOCK);

            MutableComponent block = Component.literal("Selected block: " + selected_block).withStyle(ChatFormatting.GRAY);
            pTooltipComponents.add(block);
        }
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if (stack.getTag() != null) {
            if (stack.getTag().getBoolean(POWER)) {
                searchManager(stack, level, player);
            }

            animationTicker(stack, player, level);
        }
    }

    public void searchManager(ItemStack stack, Level level, Player player) {
        if (level instanceof ServerLevel) {
            double distance = stack.getTag().getDouble(DISTANCE);
            if (distance == 0) {
                if (searchTicker >= 60) {
                    BlockPos startPos = BlockPos.containing(player.getX(), player.getY(), player.getZ());
                    AABB searchArea = new AABB(startPos).inflate(maxDistance / 2f);

                    searchTargetBlock(stack, level, startPos, searchArea);
                    searchTicker = 0;
                } else {
                    searchTicker++;
                }
            } else {
                if (searchTicker >= Mth.lerp(1.0 - (distance / maxDistance), 60, 3)) {
                    BlockPos startPos = BlockPos.containing(player.getX(), player.getY(), player.getZ());
                    AABB searchArea = new AABB(startPos).inflate(maxDistance / 2f);

                    searchTargetBlock(stack, level, startPos, searchArea);
                    searchTicker = 0;
                } else {
                    searchTicker++;
                }
            }
        }
    }

    public void searchTargetBlock(ItemStack stack, Level level, BlockPos startPos, AABB searchArea) {
        Block selectedBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(stack.getTag().getString(SELECTED_BLOCK)));
        BlockPos minPos = new BlockPos(
                Mth.floor(searchArea.minX),
                Mth.floor(searchArea.minY),
                Mth.floor(searchArea.minZ)
        );
        BlockPos maxPos = new BlockPos(
                Mth.floor(searchArea.maxX),
                Mth.floor(searchArea.maxY),
                Mth.floor(searchArea.maxZ)
        );
        List<BlockPos> foundBlocks = new ArrayList<>();

        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            if (level.getBlockState(pos).getBlock() == selectedBlock) {
                foundBlocks.add(pos.immutable());
            }
        }

        foundBlocks.sort(Comparator.comparingDouble(pos -> pos.distSqr(startPos)));

        stack.getTag().putDouble(DISTANCE, foundBlocks.isEmpty() ? 0 : foundBlocks.get(0).getCenter().distanceTo(startPos.getCenter()));

        if (stack.getTag().getInt(DISTANCE) != 0) {
            createPing(stack, level, startPos);
        }
    }

    //This is a fucking clusterfuck man
    public void animationTicker(ItemStack pStack, Player player, Level level) {
        if (level instanceof ServerLevel) {
            int frame = pStack.getTag().getInt(ANIMATION_FRAME);

            if (animationTicker != 0) {
                if (frame == 6) {
                    animationTicker = 0;
                    frame = 0;
                } else {
                    if (animationTicker % 2 == 0) {
                        frame++;
                    }
                    animationTicker++;
                }

                pStack.getTag().putInt(ANIMATION_FRAME, frame);
            }
        }
    }
}
