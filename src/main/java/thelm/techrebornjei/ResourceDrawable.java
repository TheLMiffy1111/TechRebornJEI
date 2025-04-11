package thelm.techrebornjei;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.drawable.IDrawableStatic;
import net.minecraft.resources.ResourceLocation;

public record ResourceDrawable(ResourceLocation atlasLocation, int u, int v, int width, int height, int textureWidth, int textureHeight) implements IDrawableStatic {

	public ResourceDrawable(ResourceLocation atlasLocation, int u, int v, int width, int height) {
		this(atlasLocation, u, v, width, height, 256, 256);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void draw(PoseStack poseStack, int xOffset, int yOffset) {
		draw(poseStack, xOffset, yOffset, 0, 0, 0, 0);
	}

	@Override
	public void draw(PoseStack poseStack, int xOffset, int yOffset, int maskTop, int maskBottom, int maskLeft, int maskRight) {
		GuiRenderUtil.blit(poseStack, atlasLocation, xOffset + maskLeft, yOffset + maskTop, u + maskLeft, v + maskTop, width - maskLeft - maskRight, height - maskTop - maskBottom, textureHeight, textureHeight);
	}
}
