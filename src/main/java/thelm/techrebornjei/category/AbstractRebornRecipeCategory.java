package thelm.techrebornjei.category;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.fabric.ingredients.fluids.IJeiFluidIngredient;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.fabric.ingredients.fluid.JeiFluidIngredient;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.common.crafting.RebornFluidRecipe;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.fluid.container.FluidInstance;

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

	public IJeiFluidIngredient getFluid(RebornRecipe recipe) {
		if(recipe instanceof RebornFluidRecipe fluidRecipe) {
			FluidInstance stack = fluidRecipe.getFluidInstance();
			return new JeiFluidIngredient(stack.getFluid(), stack.getAmount().getRawValue(), stack.getTag());
		}
		return new JeiFluidIngredient(Fluids.EMPTY, 0);
	}

	public ItemStack getOutput(RebornRecipe recipe, int index) {
		if(index >= 0 && index < recipe.getOutputs().size()) {
			return recipe.getOutputs().get(index);
		}
		return ItemStack.EMPTY;
	}

	public Component getTimeComponent(RebornRecipe recipe) {
		return new TranslatableComponent("techreborn.jei.recipe.processing.time.3", TIME_FORMAT.format(recipe.getTime() / 20D));
	}

	public void drawProgressBar(PoseStack poseStack, int x, int y, RebornRecipe recipe, GuiBuilder.ProgressDirection direction) {
		drawProgressBar(poseStack, x, y, recipe.getTime() * 50, direction);
	}
}
