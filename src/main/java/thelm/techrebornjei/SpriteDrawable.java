package thelm.techrebornjei;

import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.drawable.IDrawableStatic;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public record SpriteDrawable(Supplier<TextureAtlasSprite> spriteSupplier, int width, int height) implements IDrawableStatic {

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
		GuiRenderUtil.blitSprite(poseStack, spriteSupplier.get(), xOffset + maskLeft, yOffset + maskTop, maskLeft, maskTop, width - maskLeft - maskRight, height - maskTop - maskBottom, width, height);
	}
}
