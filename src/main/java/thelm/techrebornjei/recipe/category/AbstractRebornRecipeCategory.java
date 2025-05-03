package thelm.techrebornjei.recipe.category;

import java.util.List;

import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import reborncore.common.crafting.RebornRecipe;

public abstract class AbstractRebornRecipeCategory<R extends RebornRecipe> extends AbstractRecipeCategory<R> {

	public AbstractRebornRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	public AbstractRebornRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public List<ItemStack> getInput(RebornRecipe recipe, int index) {
		if(index >= 0 && index < recipe.getRebornIngredients().size()) {
			return recipe.getRebornIngredients().get(index).getPreviewStacks();
		}
		return List.of();
	}

	public ItemStack getOutput(RebornRecipe recipe, int index) {
		if(index >= 0 && index < recipe.getOutputs().size()) {
			return recipe.getOutputs().get(index);
		}
		return ItemStack.EMPTY;
	}

	public Component getTimeComponent(RebornRecipe recipe) {
		return Component.translatable("techreborn.jei.recipe.processing.time.3", TIME_FORMAT.format(recipe.getTime() / 20D));
	}
}
