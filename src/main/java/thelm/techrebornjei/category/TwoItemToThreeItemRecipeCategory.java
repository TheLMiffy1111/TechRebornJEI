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
import reborncore.client.gui.GuiBuilder;
import reborncore.common.crafting.RebornRecipe;

public class TwoItemToThreeItemRecipeCategory<R extends RebornRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public TwoItemToThreeItemRecipeCategory(RecipeType<RecipeHolder<R>> recipeType) {
		super(recipeType);
	}

	public TwoItemToThreeItemRecipeCategory(RecipeType<RecipeHolder<R>> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 27, 10).addItemStacks(getInput(recipe, 0)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.INPUT, 27, 30).addItemStacks(getInput(recipe, 1)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 20).addItemStack(getOutput(recipe, 0)).setBackground(OUTPUT_SLOT_LEFT, -5, -5);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 20).addItemStack(getOutput(recipe, 1)).setBackground(OUTPUT_SLOT_CENTER, -5, -5);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 20).addItemStack(getOutput(recipe, 2)).setBackground(OUTPUT_SLOT_RIGHT, -5, -5);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		drawProgressBar(guiGraphics, 48, 23, recipe, GuiBuilder.ProgressDirection.RIGHT);
		Font font = font();
		Component component = getTimeComponent(recipe);
		guiGraphics.drawString(font, component, getWidth() - font.width(component), 0, 0xFF808080, false);
	}
}
