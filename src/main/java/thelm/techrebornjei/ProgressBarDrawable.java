package thelm.techrebornjei;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.common.crafting.RebornRecipe;

public record ProgressBarDrawable(Direction direction, int duration) implements IDrawable {

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
	public void draw(PoseStack poseStack, int xOffset, int yOffset) {
		Minecraft minecraft = Minecraft.getInstance();
		int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode());
		float mask = LENGTH - Math.round(System.currentTimeMillis() % duration * guiScale * LENGTH / (float)duration) / (float)guiScale;
		switch(direction) {
		case RIGHT -> {
			PROGRESS_RIGHT_BASE.draw(poseStack, xOffset, yOffset);
			PROGRESS_RIGHT_OVERLAY.draw(poseStack, xOffset, yOffset, 0, 0, 0, mask);
		}
		case LEFT -> {
			PROGRESS_LEFT_BASE.draw(poseStack, xOffset, yOffset);
			PROGRESS_LEFT_OVERLAY.draw(poseStack, xOffset, yOffset, 0, 0, mask, 0);
		}
		case DOWN -> {
			PROGRESS_DOWN_BASE.draw(poseStack, xOffset, yOffset);
			PROGRESS_DOWN_OVERLAY.draw(poseStack, xOffset, yOffset, 0, mask, 0, 0);
		}
		case UP -> {
			PROGRESS_UP_BASE.draw(poseStack, xOffset, yOffset);
			PROGRESS_UP_OVERLAY.draw(poseStack, xOffset, yOffset, mask, 0, 0, 0);
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
