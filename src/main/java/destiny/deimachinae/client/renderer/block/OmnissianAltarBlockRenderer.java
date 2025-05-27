package destiny.deimachinae.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import destiny.deimachinae.blocks.block_entities.OmnissianAltarBlockEntity;
import destiny.deimachinae.client.model.block.OmnissianAltarModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class OmnissianAltarBlockRenderer extends GeoBlockRenderer<OmnissianAltarBlockEntity> {
    public OmnissianAltarBlockRenderer() {
        super(new OmnissianAltarModel());
    }

    @Override
    public void actuallyRender(PoseStack poseStack, OmnissianAltarBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        Level level = animatable.getLevel();

        if (animatable.inputSlot.getStackInSlot(0) != ItemStack.EMPTY) {
            ItemStack itemStack = animatable.inputSlot.getStackInSlot(0);

            renderSpinningItem(level, itemStack, poseStack, partialTick, bufferSource, packedLight);
        }
    }

    private void renderSpinningItem(Level level, ItemStack stack, PoseStack poseStack, float partialTicks, MultiBufferSource bufferIn, int combinedLightIn) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        poseStack.pushPose();
        float time = (level.getGameTime() + partialTicks) * 0.8f;
        float yOffset = Mth.sin(time * 0.1f) * 0.15f;

        poseStack.translate(0, 1 + yOffset, 0);
        poseStack.mulPose(Axis.YP.rotation(time * 0.0575f));
        poseStack.scale(1, 1, 1);

        itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, combinedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferIn, level, 0);
        poseStack.popPose();
    }
}
