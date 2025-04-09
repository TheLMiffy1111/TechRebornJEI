package thelm.techrebornjei.category;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.fabric.constants.FabricTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import reborncore.client.gui.guibuilder.GuiBuilder;
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
		builder.addSlot(RecipeIngredientRole.INPUT, 11, 3).addFluidStack(recipe.fluid(), FluidConstants.BUCKET).setCustomRenderer(FabricTypes.FLUID_STACK, FluidIngredientRenderer.DOWNWARDS);
	}

	@Override
	public void draw(FluidGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		drawEnergyDisplay(poseStack, 114, 3, EntryAnimation.UPWARDS);
		drawProgressBar(poseStack, 62, 23, 5000, GuiBuilder.ProgressDirection.RIGHT);
	}

	@Override
	public List<Component> getTooltipStrings(FluidGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(isInEnergyDisplay(114, 3, mouseX, mouseY)) {
			return List.of(
					Component.translatable("techreborn.jei.recipe.energy"),
					Component.translatable("techreborn.jei.recipe.generator.total", recipe.getEnergyPerBucket()).withStyle(ChatFormatting.GRAY),
					Component.empty(),
					Component.literal(jeiHelpers().getModIdHelper().getFormattedModNameForModId("techreborn")));
		}
		return List.of();
	}

	@Override
	public ResourceLocation getRegistryName(FluidGeneratorRecipe recipe) {
		return Registry.FLUID.getKey(recipe.fluid());
	}
}
