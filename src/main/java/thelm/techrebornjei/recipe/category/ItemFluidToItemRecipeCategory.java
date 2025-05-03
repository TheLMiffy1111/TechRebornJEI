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
import reborncore.common.crafting.RebornFluidRecipe;
import thelm.techrebornjei.gui.render.OutputSlotDrawable;
import thelm.techrebornjei.gui.render.ProgressBarDrawable;

public class ItemFluidToItemRecipeCategory<R extends RebornFluidRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public ItemFluidToItemRecipeCategory(RecipeType<RecipeHolder<R>> recipeType) {
		super(recipeType);
	}

	public ItemFluidToItemRecipeCategory(RecipeType<RecipeHolder<R>> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 50, 20, getInput(recipe, 0), SLOT);
		addFluid(builder, RecipeIngredientRole.INPUT, 24, 3, recipe.getFluidInstance());
		addItem(builder, RecipeIngredientRole.OUTPUT, 100, 20, getOutput(recipe, 0), OutputSlotDrawable.SINGLE);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		ProgressBarDrawable.right(recipe).draw(guiGraphics, 73, 23);
		Font font = font();
		Component component = getTimeComponent(recipe);
		guiGraphics.drawString(font, component, getWidth() - font.width(component), 0, 0xFF808080, false);
	}
}
