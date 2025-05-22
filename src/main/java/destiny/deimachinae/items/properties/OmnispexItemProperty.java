package destiny.deimachinae.items.properties;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static destiny.deimachinae.items.OmnispexItem.ANIMATION_FRAME;

public class OmnispexItemProperty implements ClampedItemPropertyFunction {
    @Override
    public float unclampedCall(@NotNull ItemStack stack, @javax.annotation.Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
        if (stack.getTag() != null) {
            int frame = stack.getTag().getInt(ANIMATION_FRAME);

            switch (frame) {
                case 0:
                    return 0.0f;
                case 1:
                    return 0.2f;
                case 2:
                    return 0.4f;
                case 3:
                    return 0.6f;
                case 4:
                    return 0.8f;
                case 5:
                    return 1.0f;
            }
        }
        return 0.0f;
    }
}