package thelm.techrebornjei.addon.advancedreborn;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.pitan76.advancedreborn.Blocks;
import net.pitan76.advancedreborn.Items;
import net.pitan76.advancedreborn.Recipes;
import net.pitan76.advancedreborn.gui.GuiCanningMachine;
import net.pitan76.advancedreborn.gui.GuiCentrifugalExtractor;
import net.pitan76.advancedreborn.gui.GuiInductionFurnace;
import net.pitan76.advancedreborn.gui.GuiRotaryGrinder;
import net.pitan76.advancedreborn.gui.GuiSingularityCompressor;
import reborncore.common.crafting.RebornRecipe;
import thelm.techrebornjei.TechRebornJEI;
import thelm.techrebornjei.addon.advancedreborn.event.CreativeTabEventHandler;
import thelm.techrebornjei.gui.render.RecipeClickAreaRenderable;
import thelm.techrebornjei.ingredient.subtype.EnergyItemSubtypeInterpreter;
import thelm.techrebornjei.recipe.category.TwoItemToItemRecipeCategory;

public class AdvancedRebornJEI implements IModPlugin {

	public static final ResourceLocation UID = new ResourceLocation("techrebornjei:advancedreborn");

	public static final RecipeType<RebornRecipe> CANNING_MACHINE = TechRebornJEI.createRecipeType(Recipes.CANNING_MACHINE);

	public AdvancedRebornJEI() {
		ItemGroupEvents.MODIFY_ENTRIES_ALL.register(new CreativeTabEventHandler());
	}

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		EnergyItemSubtypeInterpreter energy = new EnergyItemSubtypeInterpreter();
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY, energy);
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY_2, energy);
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY_3, energy);
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY_4, energy);
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY_5, energy);
		registration.registerSubtypeInterpreter(Items.NANO_SUIT_HELMET, energy);
		registration.registerSubtypeInterpreter(Items.NANO_SUIT_BODY_ARMOR, energy);
		registration.registerSubtypeInterpreter(Items.NANO_SUIT_LEGGINGS, energy);
		registration.registerSubtypeInterpreter(Items.NANO_SUIT_BOOTS, energy);
		registration.registerSubtypeInterpreter(Items.BATPACK_4, energy);
		registration.registerSubtypeInterpreter(Items.BATPACK_16, energy);
		registration.registerSubtypeInterpreter(Items.BATPACK_64, energy);
		registration.registerSubtypeInterpreter(Items.BATPACK_128, energy);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new TwoItemToItemRecipeCategory<>(CANNING_MACHINE));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(CANNING_MACHINE, recipeManager.getAllRecipesFor(Recipes.CANNING_MACHINE));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(Blocks.CANNING_MACHINE, CANNING_MACHINE);

		registration.addRecipeCatalyst(Blocks.ROTARY_GRINDER, TechRebornJEI.GRINDER);
		registration.addRecipeCatalyst(Blocks.CENTRIFUGAL_EXTRACTOR, TechRebornJEI.EXTRACTOR);
		registration.addRecipeCatalyst(Blocks.SINGULARITY_COMPRESSOR, TechRebornJEI.COMPRESSOR);

		registration.addRecipeCatalyst(Blocks.INDUCTION_FURNACE, RecipeTypes.SMELTING);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(GuiCanningMachine.class, 158, 5, 12, 12, CANNING_MACHINE);

		registration.addRecipeClickArea(GuiRotaryGrinder.class, 158, 5, 12, 12, TechRebornJEI.GRINDER);
		registration.addRecipeClickArea(GuiCentrifugalExtractor.class, 158, 5, 12, 12, TechRebornJEI.EXTRACTOR);
		registration.addRecipeClickArea(GuiSingularityCompressor.class, 158, 5, 12, 12, TechRebornJEI.COMPRESSOR);

		registration.addRecipeClickArea(GuiInductionFurnace.class, 158, 5, 12, 12, RecipeTypes.SMELTING);

		RecipeClickAreaRenderable.addEntry(GuiCanningMachine.class);

		RecipeClickAreaRenderable.addEntry(GuiRotaryGrinder.class);
		RecipeClickAreaRenderable.addEntry(GuiCentrifugalExtractor.class);
		RecipeClickAreaRenderable.addEntry(GuiSingularityCompressor.class);

		RecipeClickAreaRenderable.addEntry(GuiInductionFurnace.class);
	}
}
