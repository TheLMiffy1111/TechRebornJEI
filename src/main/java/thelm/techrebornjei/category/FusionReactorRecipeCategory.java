package thelm.techrebornjei.category;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.client.gui.guibuilder.GuiBuilder;
import techreborn.api.recipe.recipes.FusionReactorRecipe;
import thelm.techrebornjei.EntryAnimation;

public class FusionReactorRecipeCategory extends TwoItemToItemCenterRecipeCategory<FusionReactorRecipe> {

	public FusionReactorRecipeCategory(RecipeType<FusionReactorRecipe> recipeType) {
		super(recipeType);
	}

	@Override
	public void draw(FusionReactorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		drawEnergyDisplay(poseStack, 3, 3, recipe.getPower() < 0 ? EntryAnimation.DOWNWARDS : EntryAnimation.UPWARDS);
		drawProgressBar(poseStack, 45, 23, recipe, GuiBuilder.ProgressDirection.RIGHT);
		drawProgressBar(poseStack, 95, 23, recipe, GuiBuilder.ProgressDirection.LEFT);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, getWidth() - font.width(component), 0, 0xFF808080);
	}
}
