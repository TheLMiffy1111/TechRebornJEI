package thelm.techrebornjei.category;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.fabric.constants.FabricTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.common.crafting.RebornFluidRecipe;
import thelm.techrebornjei.FluidIngredientRenderer;
import thelm.techrebornjei.ProgressBarDrawable;

public class ItemToFluidRecipeCategory<R extends RebornFluidRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public ItemToFluidRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public ItemToFluidRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 41, 20).addItemStacks(getInput(recipe, 0)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 3).addIngredient(FabricTypes.FLUID_STACK, getFluid(recipe)).setCustomRenderer(FabricTypes.FLUID_STACK, FluidIngredientRenderer.UP);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		ProgressBarDrawable.right(recipe).draw(poseStack, 62, 23);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, 21, 0, 0xFF808080);
	}
}
