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
import thelm.techrebornjei.OutputSlotDrawable;
import thelm.techrebornjei.ProgressBarDrawable;

public class TwoItemToTwoItemRecipeCategory<R extends RebornRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public TwoItemToTwoItemRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public TwoItemToTwoItemRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 33, 10).addItemStacks(getInput(recipe, 0)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.INPUT, 33, 30).addItemStacks(getInput(recipe, 1)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 79, 20).addItemStack(getOutput(recipe, 0)).setBackground(OutputSlotDrawable.LEFT, -5, -5);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 20).addItemStack(getOutput(recipe, 1)).setBackground(OutputSlotDrawable.RIGHT, -5, -5);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		ProgressBarDrawable.right(recipe).draw(poseStack, 54, 23);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, getWidth() - font.width(component), 0, 0xFF808080);
	}
}
