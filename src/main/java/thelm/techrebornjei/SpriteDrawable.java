package thelm.techrebornjei;

import java.util.function.Supplier;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public record SpriteDrawable(Supplier<TextureAtlasSprite> spriteSupplier, int width, int height) implements IMaskableDrawable {

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void draw(GuiGraphics guiGraphics, float xOffset, float yOffset, float maskTop, float maskBottom, float maskLeft, float maskRight) {
		GuiRenderUtil.blitSprite(guiGraphics, spriteSupplier.get(), xOffset + maskLeft, yOffset + maskTop, maskLeft, maskTop, width - maskLeft - maskRight, height - maskTop - maskBottom, width, height);
	}
}
