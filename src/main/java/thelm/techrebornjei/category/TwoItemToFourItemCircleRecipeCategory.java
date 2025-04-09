package thelm.techrebornjei.category;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.common.crafting.RebornRecipe;

public class TwoItemToFourItemCircleRecipeCategory<R extends RebornRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public TwoItemToFourItemCircleRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public TwoItemToFourItemCircleRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 33, 10).addItemStacks(getInput(recipe, 0)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.INPUT, 33, 30).addItemStacks(getInput(recipe, 1)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 75, 20).addItemStack(getOutput(recipe, 0)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 1).addItemStack(getOutput(recipe, 1)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 20).addItemStack(getOutput(recipe, 2)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 39).addItemStack(getOutput(recipe, 3)).setBackground(standardSlot(), -1, -1);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		drawProgressBar(poseStack, 54, 23, recipe, GuiBuilder.ProgressDirection.RIGHT);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, 21, 0, 0xFF808080);
	}
}
