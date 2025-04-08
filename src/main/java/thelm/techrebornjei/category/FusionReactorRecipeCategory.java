package thelm.techrebornjei.category;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import reborncore.client.gui.GuiBuilder;
import techreborn.api.recipe.recipes.FusionReactorRecipe;
import thelm.techrebornjei.EntryAnimation;

public class FusionReactorRecipeCategory extends TwoItemToItemCenterRecipeCategory<FusionReactorRecipe> {

	public FusionReactorRecipeCategory(RecipeType<FusionReactorRecipe> recipeType) {
		super(recipeType);
	}

	@Override
	public void draw(FusionReactorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		drawEnergyDisplay(guiGraphics, 8, 8, recipe.getPower() < 0 ? EntryAnimation.DOWNWARDS : EntryAnimation.UPWARDS);
		drawProgressBar(guiGraphics, 29 + 21, 30, recipe, GuiBuilder.ProgressDirection.RIGHT);
		drawProgressBar(guiGraphics, 29 + 71, 30, recipe, GuiBuilder.ProgressDirection.LEFT);
		Font font = font();
		Component component = getTimeComponent(recipe);
		guiGraphics.drawString(font, component, getWidth() - font.width(component) - 5, 5, 0xFF404040, false);
	}
}
