package thelm.techrebornjei.gui.render;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.drawable.IDrawable;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.common.crafting.RebornRecipe;
import thelm.jeidrawables.gui.render.AnimatedDrawable;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public class ProgressBarDrawable implements IDrawable {

	public static final int LENGTH = 16;
	public static final int BREADTH = 10;

	public static final ResourceDrawable PROGRESS_RIGHT_BASE = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 58, 150, LENGTH, BREADTH);
	public static final ResourceDrawable PROGRESS_LEFT_BASE = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 74, 160, LENGTH, BREADTH);
	public static final ResourceDrawable PROGRESS_DOWN_BASE = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 78, 780, BREADTH, LENGTH);
	public static final ResourceDrawable PROGRESS_UP_BASE = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 58, 170, BREADTH, LENGTH);
	public static final ResourceDrawable PROGRESS_RIGHT_OVERLAY = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 74, 150, LENGTH, BREADTH);
	public static final ResourceDrawable PROGRESS_LEFT_OVERLAY = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 58, 160, LENGTH, BREADTH);
	public static final ResourceDrawable PROGRESS_DOWN_OVERLAY = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 88, 780, BREADTH, LENGTH);
	public static final ResourceDrawable PROGRESS_UP_OVERLAY = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 68, 170, BREADTH, LENGTH);

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
	public void draw(PoseStack poseStack, int xOffset, int yOffset) {
		base.draw(poseStack, xOffset, yOffset);
		overlay.draw(poseStack, xOffset, yOffset);
	}

	public enum Direction {
		RIGHT,
		LEFT,
		DOWN,
		UP;
	}
}
