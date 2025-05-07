package thelm.techrebornjei.gui.render;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiBuilder;
import reborncore.common.crafting.RebornRecipe;
import thelm.jeidrawables.gui.render.AnimatedDrawable;
import thelm.jeidrawables.gui.render.SpriteDrawable;

public class ProgressBarDrawable implements IDrawable {

	public static final int LENGTH = 16;
	public static final int BREADTH = 10;

	public static final SpriteDrawable PROGRESS_RIGHT_BASE = new SpriteDrawable(() -> GuiBase.getSprite(GuiBuilder.ProgressDirection.RIGHT.baseSprite), LENGTH, BREADTH);
	public static final SpriteDrawable PROGRESS_LEFT_BASE = new SpriteDrawable(() -> GuiBase.getSprite(GuiBuilder.ProgressDirection.LEFT.baseSprite), LENGTH, BREADTH);
	public static final SpriteDrawable PROGRESS_DOWN_BASE = new SpriteDrawable(() -> GuiBase.getSprite(GuiBuilder.ProgressDirection.DOWN.baseSprite), BREADTH, LENGTH);
	public static final SpriteDrawable PROGRESS_UP_BASE = new SpriteDrawable(() -> GuiBase.getSprite(GuiBuilder.ProgressDirection.UP.baseSprite), BREADTH, LENGTH);
	public static final SpriteDrawable PROGRESS_RIGHT_OVERLAY = new SpriteDrawable(() -> GuiBase.getSprite(GuiBuilder.ProgressDirection.RIGHT.overlaySprite), LENGTH, BREADTH);
	public static final SpriteDrawable PROGRESS_LEFT_OVERLAY = new SpriteDrawable(() -> GuiBase.getSprite(GuiBuilder.ProgressDirection.LEFT.overlaySprite), LENGTH, BREADTH);
	public static final SpriteDrawable PROGRESS_DOWN_OVERLAY = new SpriteDrawable(() -> GuiBase.getSprite(GuiBuilder.ProgressDirection.DOWN.overlaySprite), BREADTH, LENGTH);
	public static final SpriteDrawable PROGRESS_UP_OVERLAY = new SpriteDrawable(() -> GuiBase.getSprite(GuiBuilder.ProgressDirection.UP.overlaySprite), BREADTH, LENGTH);

	public final IDrawable base;
	public final IDrawable overlay;
	public final int width;
	public final int height;

	public ProgressBarDrawable(Direction direction, int millisPerCycle) {
		switch(direction) {
		case RIGHT -> {
			base = PROGRESS_RIGHT_BASE;
			overlay = new AnimatedDrawable(PROGRESS_RIGHT_OVERLAY, AnimatedDrawable.Type.LEFT_FILL, millisPerCycle);
			width = LENGTH;
			height = BREADTH;
		}
		case LEFT -> {
			base = PROGRESS_LEFT_BASE;
			overlay = new AnimatedDrawable(PROGRESS_LEFT_OVERLAY, AnimatedDrawable.Type.RIGHT_FILL, millisPerCycle);
			width = LENGTH;
			height = BREADTH;
		}
		case DOWN -> {
			base = PROGRESS_DOWN_BASE;
			overlay = new AnimatedDrawable(PROGRESS_DOWN_OVERLAY, AnimatedDrawable.Type.TOP_FILL, millisPerCycle);
			height = LENGTH;
			width = BREADTH;
		}
		case UP -> {
			base = PROGRESS_UP_BASE;
			overlay = new AnimatedDrawable(PROGRESS_UP_OVERLAY, AnimatedDrawable.Type.BOTTOM_FILL, millisPerCycle);
			height = LENGTH;
			width = BREADTH;
		}
		default -> throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	public static ProgressBarDrawable right(int duration) {
		return new ProgressBarDrawable(Direction.RIGHT, duration);
	}

	public static ProgressBarDrawable right(RebornRecipe recipe) {
		return right(recipe.getTime() * 50);
	}

	public static ProgressBarDrawable left(int duration) {
		return new ProgressBarDrawable(Direction.LEFT, duration);
	}

	public static ProgressBarDrawable left(RebornRecipe recipe) {
		return left(recipe.getTime() * 50);
	}

	public static ProgressBarDrawable down(int duration) {
		return new ProgressBarDrawable(Direction.DOWN, duration);
	}

	public static ProgressBarDrawable down(RebornRecipe recipe) {
		return down(recipe.getTime() * 50);
	}

	public static ProgressBarDrawable up(int duration) {
		return new ProgressBarDrawable(Direction.UP, duration);
	}

	public static ProgressBarDrawable up(RebornRecipe recipe) {
		return up(recipe.getTime() * 50);
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
	public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
		base.draw(guiGraphics, xOffset, yOffset);
		overlay.draw(guiGraphics, xOffset, yOffset);
	}

	public enum Direction {
		RIGHT,
		LEFT,
		DOWN,
		UP;
	}
}
