package thelm.techrebornjei.category;

import java.util.List;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import reborncore.common.crafting.RebornRecipe;
import thelm.techrebornjei.EnergyDisplayDrawable;

public abstract class AbstractRebornEnergyRecipeCategory<R extends RebornRecipe> extends AbstractRebornRecipeCategory<R> {

	public AbstractRebornEnergyRecipeCategory(RecipeType<R> recipeType) {
		super(recipeType);
	}

	public AbstractRebornEnergyRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addDrawable(EnergyDisplayDrawable.DOWN, 3, 3);
	}

	@Override
	public void getTooltip(ITooltipBuilder tooltip, R recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(EnergyDisplayDrawable.isMouseOver(3, 3, mouseX, mouseY)) {
			tooltip.addAll(List.of(
					Component.translatable("techreborn.jei.recipe.energy"),
					Component.translatable("techreborn.jei.recipe.running.cost", "E", recipe.getPower()).withStyle(ChatFormatting.GRAY),
					Component.translatable("techreborn.jei.recipe.generator.total", recipe.getPower() * recipe.getTime()).withStyle(ChatFormatting.GRAY),
					Component.empty(),
					Component.literal(jeiHelpers().getModIdHelper().getFormattedModNameForModId("techreborn"))));
		}
	}
}
