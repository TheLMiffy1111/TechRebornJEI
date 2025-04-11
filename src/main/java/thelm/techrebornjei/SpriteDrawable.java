package thelm.techrebornjei;

import java.util.function.Supplier;

import mezz.jei.api.gui.drawable.IDrawableStatic;
import net.minecraft.client.gui.GuiGraphics;
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
	public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
		draw(guiGraphics, xOffset, yOffset, 0, 0, 0, 0);
	}

	@Override
	public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset, int maskTop, int maskBottom, int maskLeft, int maskRight) {
		GuiRenderUtil.blitSprite(guiGraphics, spriteSupplier.get(), xOffset + maskLeft, yOffset + maskTop, maskLeft, maskTop, width - maskLeft - maskRight, height - maskTop - maskBottom, width, height);
	}
}
