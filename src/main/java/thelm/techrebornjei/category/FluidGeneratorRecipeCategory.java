package thelm.techrebornjei.category;

import java.util.List;

import mezz.jei.api.fabric.constants.FabricTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import techreborn.api.generator.FluidGeneratorRecipe;
import thelm.techrebornjei.EnergyDisplayDrawable;
import thelm.techrebornjei.FluidIngredientRenderer;
import thelm.techrebornjei.ProgressBarDrawable;

public class FluidGeneratorRecipeCategory extends AbstractRecipeCategory<FluidGeneratorRecipe> {

	public FluidGeneratorRecipeCategory(RecipeType<FluidGeneratorRecipe> recipeType, Component title) {
		super(recipeType, title);
	}

	public FluidGeneratorRecipeCategory(RecipeType<FluidGeneratorRecipe> recipeType) {
		super(recipeType);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, FluidGeneratorRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 11, 3).addFluidStack(recipe.fluid(), FluidConstants.BUCKET).setCustomRenderer(FabricTypes.FLUID_STACK, FluidIngredientRenderer.DOWN);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, FluidGeneratorRecipe recipe, IFocusGroup focuses) {
		builder.addDrawable(EnergyDisplayDrawable.UP, 114, 3);
		builder.addDrawable(ProgressBarDrawable.right(5000), 62, 23);
	}

	@Override
	public void getTooltip(ITooltipBuilder tooltip, FluidGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(EnergyDisplayDrawable.isMouseOver(114, 3, mouseX, mouseY)) {
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
