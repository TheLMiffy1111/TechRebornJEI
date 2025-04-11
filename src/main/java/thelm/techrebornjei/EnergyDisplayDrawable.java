package thelm.techrebornjei;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.drawable.IDrawable;
import reborncore.client.gui.guibuilder.GuiBuilder;

public record EnergyDisplayDrawable(EntryAnimation animation) implements IDrawable {

	public static final EnergyDisplayDrawable UP = new EnergyDisplayDrawable(EntryAnimation.UP);
	public static final EnergyDisplayDrawable DOWN = new EnergyDisplayDrawable(EntryAnimation.DOWN);
	public static final EnergyDisplayDrawable STATIC = new EnergyDisplayDrawable(EntryAnimation.STATIC);

	public static final int WIDTH = 14;
	public static final int HEIGHT = 50;

	public static final ResourceDrawable POWER_BAR_BASE = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 126, 150, WIDTH, HEIGHT);
	public static final ResourceDrawable POWER_BAR_OVERLAY = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 141, 151, WIDTH - 2, HEIGHT - 2);

	public static EnergyDisplayDrawable up(int duration) {
		return new EnergyDisplayDrawable(EntryAnimation.up(duration));
	}

	public static EnergyDisplayDrawable down(int duration) {
		return new EnergyDisplayDrawable(EntryAnimation.down(duration));
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public void draw(PoseStack poseStack, int xOffset, int yOffset) {
		float mask = 0;
		if(animation.direction() != EntryAnimation.Direction.STATIC) {
			mask = System.currentTimeMillis() % animation.duration() / (float)animation.duration() * (HEIGHT - 2);
			if(animation.direction() == EntryAnimation.Direction.UP) {
				mask = HEIGHT - 2 - mask;
			}
		}
		POWER_BAR_BASE.draw(poseStack, xOffset, yOffset);
		POWER_BAR_OVERLAY.draw(poseStack, xOffset + 1, yOffset + 1, mask, 0, 0, 0);
	}

	public static boolean isMouseOver(int x, int y, double mouseX, double mouseY) {
		return mouseX >= x && mouseX < x + WIDTH && mouseY >= y && mouseY < y + HEIGHT;
	}
}
