package thelm.techrebornjei.addon.advancedreborn;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.pitan76.advancedreborn.Blocks;
import net.pitan76.advancedreborn.Recipes;
import net.pitan76.advancedreborn.gui.GuiCanningMachine;
import net.pitan76.advancedreborn.gui.GuiCentrifugalExtractor;
import net.pitan76.advancedreborn.gui.GuiInductionFurnace;
import net.pitan76.advancedreborn.gui.GuiRotaryGrinder;
import net.pitan76.advancedreborn.gui.GuiSingularityCompressor;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import thelm.techrebornjei.RecipeClickAreaRenderer;
import thelm.techrebornjei.TechRebornJEIPlugin;
import thelm.techrebornjei.category.TwoItemToItemRecipeCategory;

public class AdvancedRebornJEIPlugin implements IModPlugin {

	public static final ResourceLocation UID = new ResourceLocation("techrebornjei:advancedreborn");

	public static final RecipeType<RecipeHolder<RebornRecipe>> CANNING_MACHINE = RecipeType.createFromVanilla((RebornRecipeType<RebornRecipe>)Recipes.CANNING_MACHINE);

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new TwoItemToItemRecipeCategory<>(CANNING_MACHINE));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(CANNING_MACHINE, recipeManager.getAllRecipesFor((RebornRecipeType<RebornRecipe>)Recipes.CANNING_MACHINE));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(Blocks.CANNING_MACHINE.getOrNull()), CANNING_MACHINE);

		registration.addRecipeCatalyst(new ItemStack(Blocks.ROTARY_GRINDER.getOrNull()), TechRebornJEIPlugin.GRINDER);
		registration.addRecipeCatalyst(new ItemStack(Blocks.CENTRIFUGAL_EXTRACTOR.getOrNull()), TechRebornJEIPlugin.EXTRACTOR);
		registration.addRecipeCatalyst(new ItemStack(Blocks.SINGULARITY_COMPRESSOR.getOrNull()), TechRebornJEIPlugin.COMPRESSOR);

		registration.addRecipeCatalyst(new ItemStack(Blocks.INDUCTION_FURNACE.getOrNull()), RecipeTypes.SMELTING);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(GuiCanningMachine.class, 158, 5, 12, 12, CANNING_MACHINE);

		registration.addRecipeClickArea(GuiRotaryGrinder.class, 158, 5, 12, 12, TechRebornJEIPlugin.GRINDER);
		registration.addRecipeClickArea(GuiCentrifugalExtractor.class, 158, 5, 12, 12, TechRebornJEIPlugin.EXTRACTOR);
		registration.addRecipeClickArea(GuiSingularityCompressor.class, 158, 5, 12, 12, TechRebornJEIPlugin.COMPRESSOR);

		registration.addRecipeClickArea(GuiInductionFurnace.class, 158, 5, 12, 12, RecipeTypes.SMELTING);

		RecipeClickAreaRenderer.addEntry(GuiCanningMachine.class);

		RecipeClickAreaRenderer.addEntry(GuiRotaryGrinder.class);
		RecipeClickAreaRenderer.addEntry(GuiCentrifugalExtractor.class);
		RecipeClickAreaRenderer.addEntry(GuiSingularityCompressor.class);

		RecipeClickAreaRenderer.addEntry(GuiInductionFurnace.class);
	}
}
