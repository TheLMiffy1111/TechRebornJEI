package thelm.techrebornjei.gui.render;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiSprites;
import thelm.jeidrawables.gui.render.AnimatedDrawable;
import thelm.jeidrawables.gui.render.SpriteDrawable;

public class EnergyDisplayDrawable implements IDrawable {

	public static final int WIDTH = 14;
	public static final int HEIGHT = 50;

	public static final SpriteDrawable POWER_BAR_BASE = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.POWER_BAR_BASE), WIDTH, HEIGHT);
	public static final SpriteDrawable POWER_BAR_OVERLAY = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.POWER_BAR_OVERLAY), WIDTH - 2, HEIGHT - 2);

	public static final EnergyDisplayDrawable UP = new EnergyDisplayDrawable(Direction.UP, 5000);
	public static final EnergyDisplayDrawable DOWN = new EnergyDisplayDrawable(Direction.DOWN, 5000);
	public static final EnergyDisplayDrawable STATIC = new EnergyDisplayDrawable(Direction.STATIC, 0);

	public final IDrawable overlay;

	public EnergyDisplayDrawable(Direction direction, int millisPerCycle) {
		if(millisPerCycle > 0) {
			switch(direction) {
			case UP -> {
				overlay = new AnimatedDrawable(POWER_BAR_OVERLAY, AnimatedDrawable.Type.BOTTOM_FILL_MOVING, millisPerCycle);
			}
			case DOWN -> {
				overlay = new AnimatedDrawable(POWER_BAR_OVERLAY, AnimatedDrawable.Type.BOTTOM_EMPTY_MOVING, millisPerCycle);
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

	public static EnergyDisplayDrawable up(int millisPerCycle) {
		return new EnergyDisplayDrawable(Direction.UP, millisPerCycle);
	}

	public static EnergyDisplayDrawable down(int millisPerCycle) {
		return new EnergyDisplayDrawable(Direction.DOWN, millisPerCycle);
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
	public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
		POWER_BAR_BASE.draw(guiGraphics, xOffset, yOffset);
		overlay.draw(guiGraphics, xOffset + 1, yOffset + 1);
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
