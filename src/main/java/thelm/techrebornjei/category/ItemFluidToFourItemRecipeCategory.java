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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.common.crafting.RebornFluidRecipe;
import thelm.techrebornjei.EntryAnimation;
import thelm.techrebornjei.FluidIngredientRenderer;

public class ItemFluidToFourItemRecipeCategory<R extends RebornFluidRecipe> extends AbstractRebornRecipeCategory<R> {

	public ItemFluidToFourItemRecipeCategory(RecipeType<R> rebornRecipeType) {
		super(rebornRecipeType);
	}

	public ItemFluidToFourItemRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 55, 36).addItemStacks(getInput(recipe, 0)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.INPUT, 55 - 26, 18).addIngredient(FabricTypes.FLUID_STACK, getFluid(recipe)).setCustomRenderer(FabricTypes.FLUID_STACK, FluidIngredientRenderer.DOWNWARDS);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55 + 46, 36 - 9 - 18).addItemStack(getOutput(recipe, 0)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55 + 46, 36 - 9).addItemStack(getOutput(recipe, 1)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55 + 46, 36 - 9 + 18).addItemStack(getOutput(recipe, 2)).setBackground(standardSlot(), -1, -1);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55 + 46, 36 - 9 + 36).addItemStack(getOutput(recipe, 3)).setBackground(standardSlot(), -1, -1);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		drawEnergyDisplay(poseStack, 8, 18, EntryAnimation.DOWNWARDS);
		drawProgressBar(poseStack, 55 + 21, 36 + 4, recipe, GuiBuilder.ProgressDirection.RIGHT);
		Font font = font();
		Component component = getTimeComponent(recipe);
		font.draw(poseStack, component, 51, 15, 0xFF404040);
	}

	@Override
	public List<Component> getTooltipStrings(R recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(isInEnergyDisplay(8, 18, mouseX, mouseY)) {
			return List.of(
					new TranslatableComponent("techreborn.jei.recipe.energy"),
					new TranslatableComponent("techreborn.jei.recipe.running.cost", "E", recipe.getPower()).withStyle(ChatFormatting.GRAY),
					new TranslatableComponent("techreborn.jei.recipe.generator.total", recipe.getPower() * recipe.getTime()).withStyle(ChatFormatting.GRAY),
					TextComponent.EMPTY,
					new TextComponent(jeiHelpers().getModIdHelper().getFormattedModNameForModId("techreborn")));
		}
		return List.of();
	}

	@Override
	public int getHeight() {
		return 88;
	}
}
