package thelm.techrebornjei.recipe.category;

import java.util.List;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
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
	public List<Component> getTooltipStrings(RecipeHolder<R> recipeHolder, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		return getTooltipStrings(recipeHolder.value(), recipeSlotsView, mouseX, mouseY);
	}

	public List<Component> getTooltipStrings(R recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		return List.of();
	}

	public List<ItemStack> getInput(RebornRecipe recipe, int index) {
		if(index >= 0 && index < recipe.getRebornIngredients().size()) {
			return recipe.getRebornIngredients().get(index).getPreviewStacks();
		}
		return List.of();
	}

	public ItemStack getOutput(RebornRecipe recipe, int index) {
		if(index >= 0 && index < recipe.getOutputs(registryAccess()).size()) {
			return recipe.getOutputs(registryAccess()).get(index);
		}
		return ItemStack.EMPTY;
	}

	public Component getTimeComponent(RebornRecipe recipe) {
		return Component.translatable("techreborn.jei.recipe.processing.time.3", TIME_FORMAT.format(recipe.getTime() / 20D));
	}
}
