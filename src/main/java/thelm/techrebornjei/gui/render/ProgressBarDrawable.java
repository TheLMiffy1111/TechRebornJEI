package thelm.techrebornjei.gui.render;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiBuilder;
import reborncore.common.crafting.RebornRecipe;

public record ProgressBarDrawable(Direction direction, int duration) implements IDrawable {

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

	public static ProgressBarDrawable right(int duration) {
		return new ProgressBarDrawable(Direction.RIGHT, duration);
	}

	public static ProgressBarDrawable right(RebornRecipe recipe) {
		return right(recipe.time() * 50);
	}

	public static ProgressBarDrawable left(int duration) {
		return new ProgressBarDrawable(Direction.LEFT, duration);
	}

	public static ProgressBarDrawable left(RebornRecipe recipe) {
		return left(recipe.time() * 50);
	}

	public static ProgressBarDrawable down(int duration) {
		return new ProgressBarDrawable(Direction.DOWN, duration);
	}

	public static ProgressBarDrawable down(RebornRecipe recipe) {
		return down(recipe.time() * 50);
	}

	public static ProgressBarDrawable up(int duration) {
		return new ProgressBarDrawable(Direction.UP, duration);
	}

	public static ProgressBarDrawable up(RebornRecipe recipe) {
		return up(recipe.time() * 50);
	}

	@Override
	public int getWidth() {
		return switch(direction) {
		case RIGHT, LEFT -> LENGTH;
		case DOWN, UP -> BREADTH;
		};
	}

	@Override
	public int getHeight() {
		return switch(direction) {
		case RIGHT, LEFT -> BREADTH;
		case DOWN, UP -> LENGTH;
		};
	}

	@Override
	public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
		Minecraft minecraft = Minecraft.getInstance();
		int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode());
		float mask = LENGTH - Math.round(System.currentTimeMillis() % duration * guiScale * LENGTH / (float)duration) / (float)guiScale;
		switch(direction) {
		case RIGHT -> {
			PROGRESS_RIGHT_BASE.draw(guiGraphics, xOffset, yOffset);
			PROGRESS_RIGHT_OVERLAY.draw(guiGraphics, xOffset, yOffset, 0, 0, 0, mask);
		}
		case LEFT -> {
			PROGRESS_LEFT_BASE.draw(guiGraphics, xOffset, yOffset);
			PROGRESS_LEFT_OVERLAY.draw(guiGraphics, xOffset, yOffset, 0, 0, mask, 0);
		}
		case DOWN -> {
			PROGRESS_DOWN_BASE.draw(guiGraphics, xOffset, yOffset);
			PROGRESS_DOWN_OVERLAY.draw(guiGraphics, xOffset, yOffset, 0, mask, 0, 0);
		}
		case UP -> {
			PROGRESS_UP_BASE.draw(guiGraphics, xOffset, yOffset);
			PROGRESS_UP_OVERLAY.draw(guiGraphics, xOffset, yOffset, mask, 0, 0, 0);
		}
		}
	}

	public enum Direction {
		RIGHT,
		LEFT,
		DOWN,
		UP;
	}
}
