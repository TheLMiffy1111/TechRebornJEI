package thelm.techrebornjei.category;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiBuilder;
import reborncore.client.gui.GuiSprites;
import thelm.techrebornjei.EntryAnimation;
import thelm.techrebornjei.GuiRenderUtil;
import thelm.techrebornjei.OutputSlotDrawable;
import thelm.techrebornjei.SpriteDrawable;
import thelm.techrebornjei.TechRebornJEIPlugin;

public abstract class AbstractRecipeCategory<R> implements IRecipeCategory<R> {

	public static final NumberFormat TIME_FORMAT = new DecimalFormat("###.##");

	public static final IDrawable SLOT = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.SLOT), 18, 18);
	public static final IDrawable OUTPUT_SLOT = new OutputSlotDrawable(true, true, true);
	public static final IDrawable OUTPUT_SLOT_LEFT = new OutputSlotDrawable(true, true, false);
	public static final IDrawable OUTPUT_SLOT_CENTER = new OutputSlotDrawable(false, true, false);
	public static final IDrawable OUTPUT_SLOT_RIGHT = new OutputSlotDrawable(false, true, true);

	public final RecipeType<R> recipeType;
	public final Component title;

	public AbstractRecipeCategory(RecipeType<R> recipeType, Component title) {
		this.recipeType = recipeType;
		this.title = title;
	}

	public AbstractRecipeCategory(RecipeType<R> recipeType) {
		this.recipeType = recipeType;
		this.title = Component.translatable(recipeType.getUid().toString());
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

	public void drawProgressBar(GuiGraphics guiGraphics, int x, int y, int animationDuration, GuiBuilder.ProgressDirection direction) {
		GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(direction.baseSprite), x, y, 0, 0, direction.width, direction.height);
		float drawLength = System.currentTimeMillis() % animationDuration / (float)animationDuration * PROGRESS_BAR_LENGTH;
		if(drawLength < 0) {
			drawLength = 0;
		}
		switch(direction) {
		case RIGHT -> GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(direction.overlaySprite), x, y, 0, 0, drawLength, PROGRESS_BAR_BREADTH, PROGRESS_BAR_LENGTH, PROGRESS_BAR_BREADTH);
		case LEFT -> GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(direction.overlaySprite), x + PROGRESS_BAR_LENGTH - drawLength, y, PROGRESS_BAR_LENGTH - drawLength, 0, drawLength, PROGRESS_BAR_BREADTH, PROGRESS_BAR_LENGTH, PROGRESS_BAR_BREADTH);
		case DOWN -> GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(direction.overlaySprite), x, y, 0, 0, PROGRESS_BAR_BREADTH, drawLength, PROGRESS_BAR_LENGTH, PROGRESS_BAR_BREADTH);
		case UP -> GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(direction.overlaySprite), x, y + PROGRESS_BAR_LENGTH - drawLength, 0, PROGRESS_BAR_LENGTH - drawLength, PROGRESS_BAR_BREADTH, drawLength, PROGRESS_BAR_BREADTH, PROGRESS_BAR_LENGTH);
		}
	}

	public static final int ENERGY_DISPLAY_WIDTH = 14;	
	public static final int ENERGY_DISPLAY_HEIGHT = 50;

	public void drawEnergyDisplay(GuiGraphics guiGraphics, int x, int y, EntryAnimation animation) {
		int innerWidth = ENERGY_DISPLAY_WIDTH - 2;
		int innerHeight = ENERGY_DISPLAY_HEIGHT - 2;
		GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(GuiSprites.POWER_BAR_BASE), x, y, 0, 0, ENERGY_DISPLAY_WIDTH, ENERGY_DISPLAY_HEIGHT);
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
		GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(GuiSprites.POWER_BAR_OVERLAY), x + 1, y + 1 + innerHeight - drawHeight, 0, 0, innerWidth, drawHeight, innerWidth, innerHeight);
	}

	public boolean isInEnergyDisplay(int x, int y, double mouseX, double mouseY) {
		return mouseX >= x && mouseX < x + ENERGY_DISPLAY_WIDTH && mouseY >= y && mouseY < y + ENERGY_DISPLAY_HEIGHT;
	}
}
