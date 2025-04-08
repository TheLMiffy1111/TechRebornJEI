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
import thelm.techrebornjei.TechRebornJEIPlugin;

public class TwoItemToFourItemRecipeCategory<R extends RebornRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public TwoItemToFourItemRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public TwoItemToFourItemRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 55 - 20, 41).addItemStacks(getInput(recipe, 0)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.INPUT, 55, 41).addItemStacks(getInput(recipe, 1)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55 + 17 - 9 - 20, 36 - 22).addItemStack(getOutput(recipe, 0)).setBackground(TechRebornJEIPlugin.outputSlot4, -5, -5);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55 + 17 - 9, 36 - 22).addItemStack(getOutput(recipe, 1));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55 + 17 - 9 + 20, 36 - 22).addItemStack(getOutput(recipe, 2));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55 + 17 - 9 + 40, 36 - 22).addItemStack(getOutput(recipe, 3));
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		drawProgressBar(poseStack, 55 + 21, 36 + 4, recipe, GuiBuilder.ProgressDirection.UP);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, getWidth() - font.width(component) - 17, getHeight() - 13, 0xFF404040);
	}
}
