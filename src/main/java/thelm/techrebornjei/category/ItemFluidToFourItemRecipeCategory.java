package thelm.techrebornjei.category;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.fabric.constants.FabricTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.common.crafting.RebornFluidRecipe;
import thelm.techrebornjei.EntryAnimation;
import thelm.techrebornjei.FluidIngredientRenderer;

public class ItemFluidToFourItemRecipeCategory<R extends RebornFluidRecipe> extends AbstractRebornRecipeCategory<R> {

	public ItemFluidToFourItemRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public ItemFluidToFourItemRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 50, 28).addItemStacks(getInput(recipe, 0)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.INPUT, 24, 11).addIngredient(FabricTypes.FLUID_STACK, getFluid(recipe)).setCustomRenderer(FabricTypes.FLUID_STACK, FluidIngredientRenderer.DOWNWARDS);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 1).addItemStack(getOutput(recipe, 0)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 19).addItemStack(getOutput(recipe, 1)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 37).addItemStack(getOutput(recipe, 2)).setBackground(SLOT, -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 55).addItemStack(getOutput(recipe, 3)).setBackground(SLOT, -1, -1);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		drawEnergyDisplay(poseStack, 3, 11, EntryAnimation.DOWNWARDS);
		drawProgressBar(poseStack, 73, 31, recipe, GuiBuilder.ProgressDirection.RIGHT);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, 46, 0, 0xFF808080);
	}

	@Override
	public List<Component> getTooltipStrings(R recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(isInEnergyDisplay(3, 11, mouseX, mouseY)) {
			return List.of(
					Component.translatable("techreborn.jei.recipe.energy"),
					Component.translatable("techreborn.jei.recipe.running.cost", "E", recipe.getPower()).withStyle(ChatFormatting.GRAY),
					Component.translatable("techreborn.jei.recipe.generator.total", recipe.getPower() * recipe.getTime()).withStyle(ChatFormatting.GRAY),
					Component.empty(),
					Component.literal(jeiHelpers().getModIdHelper().getFormattedModNameForModId("techreborn")));
		}
		return List.of();
	}

	@Override
	public int getHeight() {
		return 72;
	}
}
