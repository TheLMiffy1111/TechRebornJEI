package thelm.techrebornjei.addon.advancedreborn;

import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeMap;
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

	public static final ResourceLocation UID = ResourceLocation.parse("techrebornjei:advancedreborn");

	public static final IRecipeHolderType<RebornRecipe> CANNING_MACHINE = IRecipeHolderType.create(Recipes.CANNING_MACHINE);

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
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY.get(), energy);
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY_2.get(), energy);
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY_3.get(), energy);
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY_4.get(), energy);
		registration.registerSubtypeInterpreter(Items.ADVANCED_BATTERY_5.get(), energy);
		registration.registerSubtypeInterpreter(Items.BATPACK_4.get(), energy);
		registration.registerSubtypeInterpreter(Items.BATPACK_16.get(), energy);
		registration.registerSubtypeInterpreter(Items.BATPACK_64.get(), energy);
		registration.registerSubtypeInterpreter(Items.BATPACK_128.get(), energy);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new TwoItemToItemRecipeCategory<>(CANNING_MACHINE));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeMap recipeMap = RecipeMap.EMPTY; // JEI doesn't do recipe sync yet
		registration.addRecipes(CANNING_MACHINE, List.copyOf(recipeMap.byType(Recipes.CANNING_MACHINE)));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addCraftingStation(CANNING_MACHINE, Blocks.CANNING_MACHINE.get());

		registration.addCraftingStation(TechRebornJEI.GRINDER, Blocks.ROTARY_GRINDER.get());
		registration.addCraftingStation(TechRebornJEI.EXTRACTOR, Blocks.CENTRIFUGAL_EXTRACTOR.get());
		registration.addCraftingStation(TechRebornJEI.COMPRESSOR, Blocks.SINGULARITY_COMPRESSOR.get());

		registration.addCraftingStation(RecipeTypes.SMELTING, Blocks.INDUCTION_FURNACE.get());
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
