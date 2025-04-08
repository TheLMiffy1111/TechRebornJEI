package thelm.techrebornjei.category;

import java.util.List;

import mezz.jei.api.fabric.constants.FabricTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import reborncore.client.gui.GuiBuilder;
import techreborn.api.generator.FluidGeneratorRecipe;
import thelm.techrebornjei.EntryAnimation;
import thelm.techrebornjei.FluidIngredientRenderer;

public class FluidGeneratorRecipeCategory extends AbstractRecipeCategory<FluidGeneratorRecipe> {

	public FluidGeneratorRecipeCategory(RecipeType<FluidGeneratorRecipe> recipeType, Component title) {
		super(recipeType, title);
	}

	public FluidGeneratorRecipeCategory(RecipeType<FluidGeneratorRecipe> recipeType) {
		super(recipeType);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, FluidGeneratorRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 16, 8).addFluidStack(recipe.fluid(), FluidConstants.BUCKET).setCustomRenderer(FabricTypes.FLUID_STACK, FluidIngredientRenderer.DOWNWARDS);
	}

	@Override
	public void draw(FluidGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		drawEnergyDisplay(guiGraphics, 108, 8, EntryAnimation.UPWARDS);
		drawProgressBar(guiGraphics, 76 - 16, 48 - 19, 5000, GuiBuilder.ProgressDirection.RIGHT);
	}

	@Override
	public void getTooltip(ITooltipBuilder tooltip, FluidGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(isInEnergyDisplay(108, 8, mouseX, mouseY)) {
			tooltip.addAll(List.of(
					Component.translatable("techreborn.jei.recipe.energy"),
					Component.translatable("techreborn.jei.recipe.generator.total", recipe.getEnergyPerBucket()).withStyle(ChatFormatting.GRAY),
					Component.empty(),
					Component.literal(jeiHelpers().getModIdHelper().getFormattedModNameForModId("techreborn"))));
		}
	}

	@Override
	public ResourceLocation getRegistryName(FluidGeneratorRecipe recipe) {
		return BuiltInRegistries.FLUID.getKey(recipe.fluid());
	}
}
