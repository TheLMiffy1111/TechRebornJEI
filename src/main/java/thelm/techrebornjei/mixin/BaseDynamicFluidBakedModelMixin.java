package thelm.techrebornjei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import techreborn.client.render.BaseDynamicFluidBakedModel;

@Mixin(BaseDynamicFluidBakedModel.class)
public class BaseDynamicFluidBakedModelMixin {

	@Overwrite
	public boolean isCustomRenderer() {
		return true;
	}
}
