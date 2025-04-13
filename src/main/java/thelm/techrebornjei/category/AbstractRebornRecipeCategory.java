package thelm.techrebornjei.category;

import java.util.List;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import reborncore.common.crafting.RebornRecipe;

public abstract class AbstractRebornRecipeCategory<R extends RebornRecipe> extends AbstractRecipeCategory<RecipeHolder<R>> {

	public AbstractRebornRecipeCategory(RecipeType<RecipeHolder<R>> recipeType, Component title) {
		super(recipeType, title);
	}

	public AbstractRebornRecipeCategory(RecipeType<RecipeHolder<R>> recipeType) {
		super(recipeType);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<R> recipeHolder, IFocusGroup focuses) {
		setRecipe(builder, recipeHolder.value(), focuses);
	}

	public abstract void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses);

	@Override
	public void draw(RecipeHolder<R> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		draw(recipe.value(), recipeSlotsView, guiGraphics, mouseX, mouseY);
	}

	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<R> recipeHolder, IFocusGroup focuses) {
		createRecipeExtras(builder, recipeHolder.value(), focuses);
	}

	public void createRecipeExtras(IRecipeExtrasBuilder builder, R recipe, IFocusGroup focuses) {}

	@Override
	public void getTooltip(ITooltipBuilder tooltip, RecipeHolder<R> recipeHolder, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		getTooltip(tooltip, recipeHolder.value(), recipeSlotsView, mouseX, mouseY);
	}

	public void getTooltip(ITooltipBuilder tooltip, R recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {}

	public List<ItemStack> getInput(RebornRecipe recipe, int index) {
		if(index >= 0 && index < recipe.ingredients().size()) {
			return recipe.ingredients().get(index).getPreviewStacks();
		}
		return List.of();
	}

	public ItemStack getOutput(RebornRecipe recipe, int index) {
		if(index >= 0 && index < recipe.outputs().size()) {
			return recipe.outputs().get(index);
		}
		return ItemStack.EMPTY;
	}

	public Component getTimeComponent(RebornRecipe recipe) {
		return Component.translatable("techreborn.jei.recipe.processing.time.3", TIME_FORMAT.format(recipe.time() / 20D));
	}
}
