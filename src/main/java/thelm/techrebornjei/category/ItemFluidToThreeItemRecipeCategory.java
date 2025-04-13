package thelm.techrebornjei.category;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.common.crafting.RebornFluidRecipe;
import thelm.techrebornjei.ProgressBarDrawable;

public class ItemFluidToThreeItemRecipeCategory<R extends RebornFluidRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public ItemFluidToThreeItemRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public ItemFluidToThreeItemRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 50, 20, getInput(recipe, 0), SLOT);
		addFluid(builder, RecipeIngredientRole.INPUT, 24, 3, recipe.getFluidInstance());
		addItem(builder, RecipeIngredientRole.OUTPUT, 96, 2, getOutput(recipe, 0), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 96, 20, getOutput(recipe, 1), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 96, 38, getOutput(recipe, 2), SLOT);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		ProgressBarDrawable.right(recipe).draw(poseStack, 73, 23);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, 46, 0, 0xFF808080);
	}
}
