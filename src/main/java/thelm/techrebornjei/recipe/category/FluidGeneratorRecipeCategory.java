package thelm.techrebornjei.recipe.category;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.FluidInstance;
import techreborn.api.generator.FluidGeneratorRecipe;
import thelm.techrebornjei.gui.render.EnergyDisplayDrawable;
import thelm.techrebornjei.gui.render.ProgressBarDrawable;

public class FluidGeneratorRecipeCategory extends AbstractRecipeCategory<FluidGeneratorRecipe> {

	public FluidGeneratorRecipeCategory(RecipeType<FluidGeneratorRecipe> recipeType, Component title) {
		super(recipeType, title);
	}

	public FluidGeneratorRecipeCategory(RecipeType<FluidGeneratorRecipe> recipeType) {
		super(recipeType);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, FluidGeneratorRecipe recipe, IFocusGroup focuses) {
		addFluid(builder, RecipeIngredientRole.INPUT, 11, 3, new FluidInstance(recipe.fluid(), FluidValue.BUCKET));
	}

	@Override
	public void draw(FluidGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		EnergyDisplayDrawable.UP.draw(poseStack, 114, 3);
		ProgressBarDrawable.right(5000).draw(poseStack, 62, 23);
	}

	@Override
	public List<Component> getTooltipStrings(FluidGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(EnergyDisplayDrawable.isMouseOver(114, 3, mouseX, mouseY)) {
			return List.of(
					new TranslatableComponent("techreborn.jei.recipe.energy"),
					new TranslatableComponent("techreborn.jei.recipe.generator.total", recipe.getEnergyPerBucket()).withStyle(ChatFormatting.GRAY),
					new TextComponent(jeiHelpers().getModIdHelper().getFormattedModNameForModId("techreborn")));
		}
		return List.of();
	}

	@Override
	public ResourceLocation getRegistryName(FluidGeneratorRecipe recipe) {
		ResourceLocation fluidKey = Registry.FLUID.getKey(recipe.fluid());
		return new ResourceLocation("%s/%s/%s".formatted(recipeType.getUid(), fluidKey.getNamespace(), fluidKey.getPath()));
	}
}
