package thelm.techrebornjei.category;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.common.powerSystem.PowerSystem;
import reborncore.common.powerSystem.PowerSystem.EnergySystem;
import thelm.techrebornjei.BlankDrawable;
import thelm.techrebornjei.EntryAnimation;
import thelm.techrebornjei.GuiRenderUtil;
import thelm.techrebornjei.OutputSlotDrawable;
import thelm.techrebornjei.ResourceDrawable;
import thelm.techrebornjei.TechRebornJEIPlugin;

public abstract class AbstractRecipeCategory<R> implements IRecipeCategory<R> {

	public static final NumberFormat TIME_FORMAT = new DecimalFormat("###.##");

	public static final IDrawable SLOT = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 150, 0, 18, 18);
	public static final IDrawable OUTPUT_SLOT = new OutputSlotDrawable(true, true, true);
	public static final IDrawable OUTPUT_SLOT_LEFT = new OutputSlotDrawable(true, true, false);
	public static final IDrawable OUTPUT_SLOT_CENTER = new OutputSlotDrawable(false, true, false);
	public static final IDrawable OUTPUT_SLOT_RIGHT = new OutputSlotDrawable(false, true, true);

	public final RecipeType<R> recipeType;
	public final Component title;
	public final IDrawable background;

	public AbstractRecipeCategory(RecipeType<R> recipeType, Component title) {
		this.recipeType = recipeType;
		this.title = title;
		background = new BlankDrawable(getWidth(), getHeight());
	}

	public AbstractRecipeCategory(RecipeType<R> recipeType) {
		this.recipeType = recipeType;
		this.title = Component.translatable(recipeType.getUid().toString());
		background = new BlankDrawable(getWidth(), getHeight());
	}

	@Override
	public RecipeType<R> getRecipeType() {
		return recipeType;
	}

	@Override
	public Component getTitle() {
		return title;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public int getWidth() {
		return 140;
	}

	@Override
	public int getHeight() {
		return 56;
	}

	@Override
	public IDrawable getIcon() {
		return null;
	}

	public Font font() {
		return Minecraft.getInstance().font;
	}

	public IJeiHelpers jeiHelpers() {
		return TechRebornJEIPlugin.jeiHelpers;
	}

	public IGuiHelper guiHelper() {
		return jeiHelpers().getGuiHelper();
	}

	public static final int PROGRESS_BAR_LENGTH = 16;
	public static final int PROGRESS_BAR_BREADTH = 10;

	public void drawProgressBar(PoseStack poseStack, int x, int y, int animationDuration, GuiBuilder.ProgressDirection direction) {
		GuiRenderUtil.blit(poseStack, GuiBuilder.defaultTextureSheet, x, y, direction.x, direction.y, direction.width, direction.height);
		float drawLength = System.currentTimeMillis() % animationDuration / (float)animationDuration * PROGRESS_BAR_LENGTH;
		if(drawLength < 0) {
			drawLength = 0;
		}
		switch(direction) {
		case RIGHT -> GuiRenderUtil.blit(poseStack, GuiBuilder.defaultTextureSheet, x, y, direction.xActive, direction.yActive, drawLength, PROGRESS_BAR_BREADTH);
		case LEFT -> GuiRenderUtil.blit(poseStack, GuiBuilder.defaultTextureSheet, x + PROGRESS_BAR_LENGTH - drawLength, y, direction.xActive + PROGRESS_BAR_LENGTH - drawLength, direction.yActive, drawLength, PROGRESS_BAR_BREADTH);
		case DOWN -> GuiRenderUtil.blit(poseStack, GuiBuilder.defaultTextureSheet, x, y, direction.xActive, direction.yActive, PROGRESS_BAR_BREADTH, drawLength);
		case UP -> GuiRenderUtil.blit(poseStack, GuiBuilder.defaultTextureSheet, x, y + PROGRESS_BAR_LENGTH - drawLength, direction.xActive, direction.yActive + PROGRESS_BAR_LENGTH - drawLength, PROGRESS_BAR_BREADTH, drawLength);
		}
	}

	public static final int ENERGY_DISPLAY_WIDTH = 14;	
	public static final int ENERGY_DISPLAY_HEIGHT = 50;

	public void drawEnergyDisplay(PoseStack poseStack, int x, int y, EntryAnimation animation) {
		int innerWidth = ENERGY_DISPLAY_WIDTH - 2;
		int innerHeight = ENERGY_DISPLAY_HEIGHT - 2;
		EnergySystem displayPower = PowerSystem.getDisplayPower();
		GuiRenderUtil.blit(poseStack, GuiBuilder.defaultTextureSheet, x, y, displayPower.xBar - ENERGY_DISPLAY_WIDTH - 1, displayPower.yBar - 1, ENERGY_DISPLAY_WIDTH, ENERGY_DISPLAY_HEIGHT);
		float drawHeight;
		if(animation.type() != EntryAnimation.Type.NONE) {
			drawHeight = System.currentTimeMillis() % animation.duration() / (float)animation.duration() * innerHeight;
			if(animation.type() == EntryAnimation.Type.DOWNWARDS) {
				drawHeight = innerHeight - drawHeight;
			}
		}
		else {
			drawHeight = innerHeight;
		}
		GuiRenderUtil.blit(poseStack, GuiBuilder.defaultTextureSheet, x + 1, y + innerHeight - drawHeight + 1, displayPower.xBar, innerHeight + displayPower.yBar - drawHeight, innerWidth, drawHeight);
	}

	public boolean isInEnergyDisplay(int x, int y, double mouseX, double mouseY) {
		return mouseX >= x && mouseX < x + ENERGY_DISPLAY_WIDTH && mouseY >= y && mouseY < y + ENERGY_DISPLAY_HEIGHT;
	}
}
