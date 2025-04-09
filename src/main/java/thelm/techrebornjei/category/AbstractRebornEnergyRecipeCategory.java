package thelm.techrebornjei.category;

import java.util.List;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import reborncore.common.crafting.RebornRecipe;
import thelm.techrebornjei.EntryAnimation;

public abstract class AbstractRebornEnergyRecipeCategory<R extends RebornRecipe> extends AbstractRebornRecipeCategory<R> {

	public AbstractRebornEnergyRecipeCategory(RecipeType<RecipeHolder<R>> recipeType) {
		super(recipeType);
	}

	public AbstractRebornEnergyRecipeCategory(RecipeType<RecipeHolder<R>> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		drawEnergyDisplay(guiGraphics, 3, 3, EntryAnimation.DOWNWARDS);
	}

	@Override
	public void getTooltip(ITooltipBuilder tooltip, R recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
		if(isInEnergyDisplay(3, 3, mouseX, mouseY)) {
			tooltip.addAll(List.of(
					Component.translatable("techreborn.jei.recipe.energy"),
					Component.translatable("techreborn.jei.recipe.running.cost", "E", recipe.power()).withStyle(ChatFormatting.GRAY),
					Component.translatable("techreborn.jei.recipe.generator.total", recipe.power() * recipe.time()).withStyle(ChatFormatting.GRAY),
					Component.empty(),
					Component.literal(jeiHelpers().getModIdHelper().getFormattedModNameForModId("techreborn"))));
		}
	}
}
