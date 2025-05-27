package destiny.deimachinae.blocks.block_entities;

import destiny.deimachinae.init.BlockEntityInit;
import destiny.deimachinae.items.MachineSpiritItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;

public class OmnissianAltarBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public int craftingTicks = 0;
    public final ItemStackHandler inputSlot;
    private final LazyOptional<IItemHandler> inputHandler;

    protected static class Animations {
        protected static final String MAIN_CONTROLLER = "main";
        protected static final RawAnimation IDLE_LOOP = RawAnimation.begin().thenLoop("omnissian_altar.idle_loop");
        protected static final RawAnimation READY_LOOP = RawAnimation.begin().thenLoop("omnissian_altar.ready_loop");
        protected static final RawAnimation RITUAL_LOOP = RawAnimation.begin().thenLoop("omnissian_altar.ritual_loop");

        private Animations() {}
    }

    public OmnissianAltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.OMNISSIAN_ALTAR.get(), pPos, pBlockState);
        inputSlot = createHandler(1);
        inputHandler = LazyOptional.of(() -> inputSlot);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, OmnissianAltarBlockEntity altar) {
    }

    private <T extends OmnissianAltarBlockEntity> PlayState handleAnimationState(AnimationState<T> state) {
        if (craftingTicks > 0) {
            return state.setAndContinue(Animations.RITUAL_LOOP);
        } else if (inputSlot.getStackInSlot(0).getItem() instanceof MachineSpiritItem) {
            return state.setAndContinue(Animations.READY_LOOP);
        }

        return state.setAndContinue(Animations.IDLE_LOOP);
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<OmnissianAltarBlockEntity> controller = new AnimationController<>(this, Animations.MAIN_CONTROLLER, 10, this::handleAnimationState);
        controller.setAnimation(Animations.IDLE_LOOP);
        controllers.add(controller);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inputSlot.deserializeNBT(compound.getCompound("InputSlot"));
        craftingTicks = compound.getInt("CraftingTicks");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("InputSlot", inputSlot.serializeNBT());
        compound.putInt("CraftingTicks", craftingTicks);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            return inputHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < inputSlot.getSlots(); ++i) {
            drops.add(inputSlot.getStackInSlot(i));
            inputSlot.setStackInSlot(i, ItemStack.EMPTY);
        }
        return drops;
    }

    private ItemStackHandler createHandler(int size) {
        return new ItemStackHandler(size)
        {
            @Override
            protected void onContentsChanged(int slot) {
                markUpdated();
            }
        };
    }

    private void markUpdated() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }
}
