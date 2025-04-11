package thelm.techrebornjei.category;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import techreborn.api.recipe.recipes.FusionReactorRecipe;
import thelm.techrebornjei.EnergyDisplayDrawable;
import thelm.techrebornjei.ProgressBarDrawable;

public class FusionReactorRecipeCategory extends TwoItemToItemCenterRecipeCategory<FusionReactorRecipe> {

	public FusionReactorRecipeCategory(RecipeType<FusionReactorRecipe> recipeType) {
		super(recipeType);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, FusionReactorRecipe recipe, IFocusGroup focuses) {
		builder.addDrawable(recipe.getPower() < 0 ? EnergyDisplayDrawable.DOWN : EnergyDisplayDrawable.UP, 3, 3);
		builder.addDrawable(ProgressBarDrawable.right(recipe), 45, 23);
		builder.addDrawable(ProgressBarDrawable.left(recipe), 95, 23);
	}

	@Override
	public void draw(FusionReactorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		Font font = font();
		Component component = getTimeComponent(recipe);
		guiGraphics.drawString(font, component, getWidth() - font.width(component), 0, 0xFF808080, false);
	}
}
