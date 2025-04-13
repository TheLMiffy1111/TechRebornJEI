package thelm.techrebornjei.category;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.common.crafting.RebornRecipe;
import thelm.techrebornjei.ProgressBarDrawable;

public class TwoItemToFourItemCircleRecipeCategory<R extends RebornRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public TwoItemToFourItemCircleRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public TwoItemToFourItemCircleRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 33, 10, getInput(recipe, 0), SLOT);
		addItem(builder, RecipeIngredientRole.INPUT, 33, 30, getInput(recipe, 1), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 75, 20, getOutput(recipe, 0), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 94, 1, getOutput(recipe, 1), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 113, 20, getOutput(recipe, 2), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 94, 39, getOutput(recipe, 3), SLOT);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		ProgressBarDrawable.right(recipe).draw(poseStack, 54, 23);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, 21, 0, 0xFF808080);
	}
}
