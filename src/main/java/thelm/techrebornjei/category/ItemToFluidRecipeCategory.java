package thelm.techrebornjei.category;

import mezz.jei.api.fabric.constants.FabricTypes;
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
import reborncore.common.crafting.RebornFluidRecipe;
import thelm.techrebornjei.FluidIngredientRenderer;

public class ItemToFluidRecipeCategory<R extends RebornFluidRecipe> extends AbstractRebornEnergyRecipeCategory<R> {

	public ItemToFluidRecipeCategory(RecipeType<RecipeHolder<R>> recipeType) {
		super(recipeType);
	}

	public ItemToFluidRecipeCategory(RecipeType<RecipeHolder<R>> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 41, 20).addItemStacks(getInput(recipe, 0)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 3).addIngredient(FabricTypes.FLUID_STACK, getFluid(recipe)).setCustomRenderer(FabricTypes.FLUID_STACK, FluidIngredientRenderer.UPWARDS);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
		drawProgressBar(guiGraphics, 62, 23, recipe, GuiBuilder.ProgressDirection.RIGHT);
		Font font = font();
		Component component = getTimeComponent(recipe);
		guiGraphics.drawString(font, component, 21, 0, 0xFF808080, false);
	}
}
