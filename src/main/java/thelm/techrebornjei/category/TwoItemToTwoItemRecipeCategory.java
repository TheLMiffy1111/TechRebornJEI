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

public class TwoItemToTwoItemRecipeCategory<R extends RebornRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public TwoItemToTwoItemRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public TwoItemToTwoItemRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 55 - 17, 35 - 19).addItemStacks(getInput(recipe, 0)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.INPUT, 55 - 17, 55 - 19).addItemStacks(getInput(recipe, 1)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 101 - 17, 45 - 19).addItemStack(getOutput(recipe, 0)).setBackground(TechRebornJEIPlugin.outputSlot2, -5, -5);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 101 + 20 - 17, 45 - 19).addItemStack(getOutput(recipe, 1));
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		drawProgressBar(poseStack, 76 - 17, 48 - 19, recipe, GuiBuilder.ProgressDirection.RIGHT);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, getWidth() - font.width(component) - 5, 5, 0xFF404040);
	}
}
