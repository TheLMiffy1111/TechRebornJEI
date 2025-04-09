package thelm.techrebornjei.category;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.common.powerSystem.PowerSystem;
import reborncore.common.powerSystem.PowerSystem.EnergySystem;
import thelm.techrebornjei.EntryAnimation;
import thelm.techrebornjei.TechRebornJEIPlugin;

public abstract class AbstractRecipeCategory<R> extends GuiComponent implements IRecipeCategory<R> {

	public static final NumberFormat TIME_FORMAT = new DecimalFormat("###.##");

	public final RecipeType<R> recipeType;
	public final Component title;
	public final Supplier<IDrawable> background = Suppliers.memoize(() -> guiHelper().createBlankDrawable(getWidth(), getHeight()));

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
	public IDrawable getBackground() {
		return background.get();
	}

	@Override
	public int getWidth() {
		return 150;
	}

	@Override
	public int getHeight() {
		return 66;
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

	public IDrawable standardSlot() {
		return guiHelper().getSlotDrawable();
	}

	public IDrawable outputSlot1() {
		return TechRebornJEIPlugin.outputSlot1;
	}

	public IDrawable outputSlot2() {
		return TechRebornJEIPlugin.outputSlot2;
	}

	public IDrawable outputSlot3() {
		return TechRebornJEIPlugin.outputSlot3;
	}

	public IDrawable outputSlot4() {
		return TechRebornJEIPlugin.outputSlot4;
	}

	public static final int PROGRESS_BAR_LENGTH = 16;
	public static final int PROGRESS_BAR_BREADTH = 10;

	public void drawProgressBar(PoseStack poseStack, int x, int y, int animationDuration, GuiBuilder.ProgressDirection direction) {
		RenderSystem.setShaderTexture(0, GuiBuilder.defaultTextureSheet);
		blit(poseStack, x, y, direction.x, direction.y, direction.width, direction.height);
		int drawLength = Math.round(System.currentTimeMillis() % animationDuration / (float)animationDuration * PROGRESS_BAR_LENGTH);
		if(drawLength < 0) {
			drawLength = 0;
		}
		switch(direction) {
		case RIGHT -> blit(poseStack, x, y, direction.xActive, direction.yActive, drawLength, PROGRESS_BAR_BREADTH);
		case LEFT -> blit(poseStack, x + PROGRESS_BAR_LENGTH - drawLength, y, direction.xActive + PROGRESS_BAR_LENGTH - drawLength, direction.yActive, drawLength, PROGRESS_BAR_BREADTH);
		case UP -> blit(poseStack, x, y + PROGRESS_BAR_LENGTH - drawLength, direction.xActive, direction.yActive + PROGRESS_BAR_LENGTH - drawLength, PROGRESS_BAR_BREADTH, drawLength);
		case DOWN -> blit(poseStack, x, y, direction.xActive, direction.yActive, PROGRESS_BAR_BREADTH, drawLength);
		}
	}

	public static final int ENERGY_DISPLAY_WIDTH = 14;	
	public static final int ENERGY_DISPLAY_HEIGHT = 50;

	public void drawEnergyDisplay(PoseStack poseStack, int x, int y, EntryAnimation animation) {
		int innerWidth = ENERGY_DISPLAY_WIDTH - 2;
		int innerHeight = ENERGY_DISPLAY_HEIGHT - 2;
		EnergySystem displayPower = PowerSystem.getDisplayPower();
		RenderSystem.setShaderTexture(0, GuiBuilder.defaultTextureSheet);
		blit(poseStack, x, y, displayPower.xBar - ENERGY_DISPLAY_WIDTH - 1, displayPower.yBar - 1, ENERGY_DISPLAY_WIDTH, ENERGY_DISPLAY_HEIGHT);
		int drawHeight;
		if(animation.animationType() != EntryAnimation.Type.NONE) {
			drawHeight = Math.round(System.currentTimeMillis() % animation.duration() / (float)animation.duration() * innerHeight);
			if(animation.animationType() == EntryAnimation.Type.DOWNWARDS) {
				drawHeight = innerHeight - drawHeight;
			}
		}
		else {
			drawHeight = innerHeight;
		}
		blit(poseStack, x + 1, y + innerHeight - drawHeight + 1, displayPower.xBar, innerHeight + displayPower.yBar - drawHeight, innerWidth, drawHeight);
	}

	public boolean isInEnergyDisplay(int x, int y, double mouseX, double mouseY) {
		return mouseX >= x && mouseX < x + 14 && mouseY >= y && mouseY < y + 50;
	}
}
