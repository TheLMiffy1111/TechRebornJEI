package thelm.techrebornjei.category;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.client.gui.guibuilder.GuiBuilder;
import techreborn.api.recipe.recipes.RollingMachineRecipe;

public class RollingMachineRecipeCategory extends AbstractRebornEnergyRecipeCategory<RollingMachineRecipe> {

	public RollingMachineRecipeCategory(RecipeType<RollingMachineRecipe> recipeType) {
		super(recipeType);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RollingMachineRecipe recipe, IFocusGroup focuses) {
		List<IRecipeSlotBuilder> slots = new ArrayList<>(9);
		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 3; ++x) {
				slots.add(builder.addSlot(RecipeIngredientRole.INPUT, 27 + x * 18, 2 + y * 18).setBackground(standardSlot(), -1, -1));
			}
		}
		int width = recipe.getShapedRecipe().getWidth();
		int height = recipe.getShapedRecipe().getHeight();
		for(int y = 0; y < height; ++y) {
			for(int x = 0; x < width; ++x) {
				if(y * width + x < recipe.getIngredients().size()) {
					slots.get(y * 3 + x).addIngredients(recipe.getIngredients().get(y * width + x));
				}
			}
		}
		builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 20).addItemStack(getOutput(recipe, 0)).setBackground(outputSlot1(), -5, -5);
	}

	@Override
	public void draw(RollingMachineRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
		drawProgressBar(poseStack, 85, 23, recipe, GuiBuilder.ProgressDirection.RIGHT);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, getWidth() - font.width(component), 0, 0xFF808080);
	}
}
