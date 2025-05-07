package thelm.techrebornjei.gui.render;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.drawable.IDrawable;
import reborncore.client.gui.guibuilder.GuiBuilder;
import thelm.jeidrawables.gui.render.AnimatedDrawable;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public class EnergyDisplayDrawable implements IDrawable {

	public static final int WIDTH = 14;
	public static final int HEIGHT = 50;

	public static final ResourceDrawable POWER_BAR_BASE = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 126, 150, WIDTH, HEIGHT);
	public static final ResourceDrawable POWER_BAR_OVERLAY = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 141, 151, WIDTH - 2, HEIGHT - 2);

	public static final EnergyDisplayDrawable UP = new EnergyDisplayDrawable(Direction.UP, 5000);
	public static final EnergyDisplayDrawable DOWN = new EnergyDisplayDrawable(Direction.DOWN, 5000);
	public static final EnergyDisplayDrawable STATIC = new EnergyDisplayDrawable(Direction.STATIC, 0);

	public final IDrawable overlay;

	public EnergyDisplayDrawable(Direction direction, int millisPerCycle) {
		if(millisPerCycle > 0) {
			switch(direction) {
			case UP -> {
				overlay = new AnimatedDrawable(POWER_BAR_OVERLAY, AnimatedDrawable.Type.BOTTOM_FILL, millisPerCycle);
			}
			case DOWN -> {
				overlay = new AnimatedDrawable(POWER_BAR_OVERLAY, AnimatedDrawable.Type.BOTTOM_EMPTY, millisPerCycle);
			}
			default -> {
				overlay = POWER_BAR_OVERLAY;
			}
			}
		}
		else {
			overlay = POWER_BAR_OVERLAY;
		}
	}

	public static EnergyDisplayDrawable up(int duration) {
		return new EnergyDisplayDrawable(Direction.UP, duration);
	}

	public static EnergyDisplayDrawable down(int duration) {
		return new EnergyDisplayDrawable(Direction.DOWN, duration);
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
		POWER_BAR_BASE.draw(poseStack, xOffset, yOffset);
		overlay.draw(poseStack, xOffset + 1, yOffset + 1);
	}

	public static boolean isMouseOver(int x, int y, double mouseX, double mouseY) {
		return mouseX >= x && mouseX < x + WIDTH && mouseY >= y && mouseY < y + HEIGHT;
	}

	public enum Direction {
		UP,
		DOWN,
		STATIC;
	}
}
