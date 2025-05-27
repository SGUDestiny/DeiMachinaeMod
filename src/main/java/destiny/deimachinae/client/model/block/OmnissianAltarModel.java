package destiny.deimachinae.client.model.block;

import destiny.deimachinae.DeiMachinaeMod;
import destiny.deimachinae.blocks.block_entities.OmnissianAltarBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class OmnissianAltarModel extends DefaultedBlockGeoModel<OmnissianAltarBlockEntity> {
    public OmnissianAltarModel() {
        super(new ResourceLocation(DeiMachinaeMod.MODID, "omnissian_altar"));
    }

    @Override
    public RenderType getRenderType(OmnissianAltarBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}