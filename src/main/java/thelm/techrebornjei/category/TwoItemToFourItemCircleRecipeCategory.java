package thelm.techrebornjei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import reborncore.common.crafting.RebornRecipe;
import thelm.techrebornjei.ProgressBarDrawable;

public class TwoItemToFourItemCircleRecipeCategory<R extends RebornRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public TwoItemToFourItemCircleRecipeCategory(RecipeType<RecipeHolder<R>> recipeType) {
		super(recipeType);
	}

	public TwoItemToFourItemCircleRecipeCategory(RecipeType<RecipeHolder<R>> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 33, 10).addItemStacks(getInput(recipe, 0)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.INPUT, 33, 30).addItemStacks(getInput(recipe, 1)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 75, 20).addItemStack(getOutput(recipe, 0)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 1).addItemStack(getOutput(recipe, 1)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 20).addItemStack(getOutput(recipe, 2)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 39).addItemStack(getOutput(recipe, 3)).setBackground(SLOT, -1, -1);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		ProgressBarDrawable.right(recipe).draw(guiGraphics, 54, 23);
		Font font = font();
		Component component = getTimeComponent(recipe);
		guiGraphics.drawString(font, component, 21, 0, 0xFF808080, false);
	}
}
