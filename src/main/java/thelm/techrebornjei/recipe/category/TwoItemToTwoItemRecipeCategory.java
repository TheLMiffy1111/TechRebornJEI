package thelm.techrebornjei.recipe.category;

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
import thelm.techrebornjei.gui.render.OutputSlotDrawable;
import thelm.techrebornjei.gui.render.ProgressBarDrawable;

public class TwoItemToTwoItemRecipeCategory<R extends RebornRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public TwoItemToTwoItemRecipeCategory(RecipeType<RecipeHolder<R>> recipeType) {
		super(recipeType);
	}

	public TwoItemToTwoItemRecipeCategory(RecipeType<RecipeHolder<R>> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 33, 10, getInput(recipe, 0), SLOT);
		addItem(builder, RecipeIngredientRole.INPUT, 33, 30, getInput(recipe, 1), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 79, 20, getOutput(recipe, 0), OutputSlotDrawable.LEFT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 99, 20, getOutput(recipe, 1), OutputSlotDrawable.RIGHT);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		ProgressBarDrawable.right(recipe).draw(guiGraphics, 54, 23);
		Font font = font();
		Component component = getTimeComponent(recipe);
		guiGraphics.drawString(font, component, getWidth() - font.width(component), 0, 0xFF808080, false);
	}
}
