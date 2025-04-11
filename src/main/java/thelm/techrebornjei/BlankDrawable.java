package thelm.techrebornjei;

import com.mojang.blaze3d.vertex.PoseStack;

public record BlankDrawable(int width, int height) implements IMaskableDrawable {

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void draw(PoseStack poseStack, float xOffset, float yOffset, float maskTop, float maskBottom, float maskLeft, float maskRight) {}
}
