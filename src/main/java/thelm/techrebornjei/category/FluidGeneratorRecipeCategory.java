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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
		builder.addSlot(RecipeIngredientRole.INPUT, 16, 8).addFluidStack(recipe.fluid(), FluidConstants.BUCKET).setCustomRenderer(FabricTypes.FLUID_STACK, FluidIngredientRenderer.DOWNWARDS);
	}

	@Override
	public void draw(FluidGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		drawEnergyDisplay(poseStack, 108, 8, EntryAnimation.UPWARDS);
		drawProgressBar(poseStack, 76 - 16, 48 - 19, 5000, GuiBuilder.ProgressDirection.RIGHT);
	}

	@Override
	public List<Component> getTooltipStrings(FluidGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(isInEnergyDisplay(108, 8, mouseX, mouseY)) {
			return List.of(
					new TranslatableComponent("techreborn.jei.recipe.energy"),
					new TranslatableComponent("techreborn.jei.recipe.generator.total", recipe.getEnergyPerBucket()).withStyle(ChatFormatting.GRAY),
					TextComponent.EMPTY,
					new TextComponent(jeiHelpers().getModIdHelper().getFormattedModNameForModId("techreborn")));
		}
		return List.of();
	}

	@Override
	public ResourceLocation getRegistryName(FluidGeneratorRecipe recipe) {
		return Registry.FLUID.getKey(recipe.fluid());
	}
}
