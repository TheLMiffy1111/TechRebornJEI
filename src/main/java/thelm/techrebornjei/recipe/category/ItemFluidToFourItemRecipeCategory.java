package thelm.techrebornjei.recipe.category;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.common.crafting.RebornFluidRecipe;
import thelm.techrebornjei.gui.render.EnergyDisplayDrawable;
import thelm.techrebornjei.gui.render.ProgressBarDrawable;

public class ItemFluidToFourItemRecipeCategory<R extends RebornFluidRecipe> extends AbstractRebornRecipeCategory<R> {

	public ItemFluidToFourItemRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public ItemFluidToFourItemRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 50, 28, getInput(recipe, 0), SLOT);
		addFluid(builder, RecipeIngredientRole.INPUT, 24, 11, recipe.getFluidInstance());
		addItem(builder, RecipeIngredientRole.OUTPUT, 96, 1, getOutput(recipe, 0), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 96, 19, getOutput(recipe, 1), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 96, 37, getOutput(recipe, 2), SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 96, 55, getOutput(recipe, 3), SLOT);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		EnergyDisplayDrawable.DOWN.draw(poseStack, 3, 11);
		ProgressBarDrawable.right(recipe).draw(poseStack, 73, 31);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, 46, 0, 0xFF808080);
	}

	@Override
	public List<Component> getTooltipStrings(R recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(EnergyDisplayDrawable.isMouseOver(3, 11, mouseX, mouseY)) {
			return List.of(
					Component.translatable("techreborn.jei.recipe.energy"),
					Component.translatable("techreborn.jei.recipe.running.cost", "E", recipe.getPower()).withStyle(ChatFormatting.GRAY),
					Component.translatable("techreborn.jei.recipe.generator.total", recipe.getPower() * recipe.getTime()).withStyle(ChatFormatting.GRAY),
					Component.literal(jeiHelpers().getModIdHelper().getFormattedModNameForModId("techreborn")));
		}
		return List.of();
	}

	@Override
	public int getHeight() {
		return 72;
	}
}
