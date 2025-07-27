package thelm.techrebornjei;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import reborncore.client.gui.GuiBase;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.fluid.container.ItemFluidInfo;
import techreborn.api.generator.EFluidGenerator;
import techreborn.api.generator.FluidGeneratorRecipe;
import techreborn.api.generator.GeneratorRecipeHelper;
import techreborn.api.recipe.recipes.AssemblingMachineRecipe;
import techreborn.api.recipe.recipes.BlastFurnaceRecipe;
import techreborn.api.recipe.recipes.CentrifugeRecipe;
import techreborn.api.recipe.recipes.FluidReplicatorRecipe;
import techreborn.api.recipe.recipes.FusionReactorRecipe;
import techreborn.api.recipe.recipes.IndustrialGrinderRecipe;
import techreborn.api.recipe.recipes.IndustrialSawmillRecipe;
import techreborn.api.recipe.recipes.RollingMachineRecipe;
import techreborn.client.gui.GuiAlloyFurnace;
import techreborn.client.gui.GuiAlloySmelter;
import techreborn.client.gui.GuiAssemblingMachine;
import techreborn.client.gui.GuiAutoCrafting;
import techreborn.client.gui.GuiBlastFurnace;
import techreborn.client.gui.GuiCentrifuge;
import techreborn.client.gui.GuiChemicalReactor;
import techreborn.client.gui.GuiCompressor;
import techreborn.client.gui.GuiDieselGenerator;
import techreborn.client.gui.GuiDistillationTower;
import techreborn.client.gui.GuiElectricFurnace;
import techreborn.client.gui.GuiExtractor;
import techreborn.client.gui.GuiFluidReplicator;
import techreborn.client.gui.GuiFusionReactor;
import techreborn.client.gui.GuiGasTurbine;
import techreborn.client.gui.GuiGenerator;
import techreborn.client.gui.GuiGrinder;
import techreborn.client.gui.GuiImplosionCompressor;
import techreborn.client.gui.GuiIndustrialElectrolyzer;
import techreborn.client.gui.GuiIndustrialGrinder;
import techreborn.client.gui.GuiIndustrialSawmill;
import techreborn.client.gui.GuiIronFurnace;
import techreborn.client.gui.GuiPlasmaGenerator;
import techreborn.client.gui.GuiRollingMachine;
import techreborn.client.gui.GuiScrapboxinator;
import techreborn.client.gui.GuiSemifluidGenerator;
import techreborn.client.gui.GuiSolidCanningMachine;
import techreborn.client.gui.GuiThermalGenerator;
import techreborn.client.gui.GuiVacuumFreezer;
import techreborn.client.gui.GuiWireMill;
import techreborn.init.ModRecipes;
import techreborn.init.TRContent;
import thelm.techrebornjei.addon.advancedreborn.AdvancedRebornJEI;
import thelm.techrebornjei.addon.industrialreborn.IndustrialRebornJEI;
import thelm.techrebornjei.gui.handler.GuiBaseExtraAreaHandler;
import thelm.techrebornjei.gui.render.RecipeClickAreaRenderable;
import thelm.techrebornjei.recipe.category.FluidGeneratorRecipeCategory;
import thelm.techrebornjei.recipe.category.FusionReactorRecipeCategory;
import thelm.techrebornjei.recipe.category.ItemFluidToFourItemRecipeCategory;
import thelm.techrebornjei.recipe.category.ItemFluidToThreeItemRecipeCategory;
import thelm.techrebornjei.recipe.category.ItemToFluidRecipeCategory;
import thelm.techrebornjei.recipe.category.ItemToItemRecipeCategory;
import thelm.techrebornjei.recipe.category.RollingMachineRecipeCategory;
import thelm.techrebornjei.recipe.category.TwoItemToFourItemCircleRecipeCategory;
import thelm.techrebornjei.recipe.category.TwoItemToFourItemRecipeCategory;
import thelm.techrebornjei.recipe.category.TwoItemToItemCenterRecipeCategory;
import thelm.techrebornjei.recipe.category.TwoItemToItemRecipeCategory;
import thelm.techrebornjei.recipe.category.TwoItemToThreeItemRecipeCategory;
import thelm.techrebornjei.recipe.category.TwoItemToTwoItemRecipeCategory;
import thelm.techrebornjei.recipe.transfer.BuiltScreenHandlerTransferInfo;

public class TechRebornJEI implements IModPlugin {

	public static final ResourceLocation UID = new ResourceLocation("techrebornjei:techreborn");
	public static final Logger LOGGER = LogManager.getLogger();

	public static IJeiHelpers jeiHelpers;
	public static IJeiRuntime jeiRuntime;

	public static final RecipeType<RebornRecipe> ALLOY_SMELTER = createRecipeType(ModRecipes.ALLOY_SMELTER);
	public static final RecipeType<AssemblingMachineRecipe> ASSEMBLING_MACHINE = createRecipeType(ModRecipes.ASSEMBLING_MACHINE, AssemblingMachineRecipe.class);
	public static final RecipeType<BlastFurnaceRecipe> BLAST_FURNACE = createRecipeType(ModRecipes.BLAST_FURNACE, BlastFurnaceRecipe.class);
	public static final RecipeType<CentrifugeRecipe> CENTRIFUGE = createRecipeType(ModRecipes.CENTRIFUGE, CentrifugeRecipe.class);
	public static final RecipeType<RebornRecipe> CHEMICAL_REACTOR = createRecipeType(ModRecipes.CHEMICAL_REACTOR);
	public static final RecipeType<RebornRecipe> COMPRESSOR = createRecipeType(ModRecipes.COMPRESSOR);
	public static final RecipeType<RebornRecipe> DISTILLATION_TOWER = createRecipeType(ModRecipes.DISTILLATION_TOWER);
	public static final RecipeType<RebornRecipe> EXTRACTOR = createRecipeType(ModRecipes.EXTRACTOR);
	public static final RecipeType<FluidReplicatorRecipe> FLUID_REPLICATOR = createRecipeType(ModRecipes.FLUID_REPLICATOR, FluidReplicatorRecipe.class);
	public static final RecipeType<FusionReactorRecipe> FUSION_REACTOR = createRecipeType(ModRecipes.FUSION_REACTOR, FusionReactorRecipe.class);
	public static final RecipeType<RebornRecipe> GRINDER = createRecipeType(ModRecipes.GRINDER);
	public static final RecipeType<RebornRecipe> IMPLOSION_COMPRESSOR = createRecipeType(ModRecipes.IMPLOSION_COMPRESSOR);
	public static final RecipeType<RebornRecipe> INDUSTRIAL_ELECTROLYZER = createRecipeType(ModRecipes.INDUSTRIAL_ELECTROLYZER);
	public static final RecipeType<IndustrialGrinderRecipe> INDUSTRIAL_GRINDER = createRecipeType(ModRecipes.INDUSTRIAL_GRINDER, IndustrialGrinderRecipe.class);
	public static final RecipeType<IndustrialSawmillRecipe> INDUSTRIAL_SAWMILL = createRecipeType(ModRecipes.INDUSTRIAL_SAWMILL, IndustrialSawmillRecipe.class);
	public static final RecipeType<RollingMachineRecipe> ROLLING_MACHINE = createRecipeType(ModRecipes.ROLLING_MACHINE, RollingMachineRecipe.class);
	public static final RecipeType<RebornRecipe> SCRAPBOX = createRecipeType(ModRecipes.SCRAPBOX);
	public static final RecipeType<RebornRecipe> SOLID_CANNING_MACHINE = createRecipeType(ModRecipes.SOLID_CANNING_MACHINE);
	public static final RecipeType<RebornRecipe> VACUUM_FREEZER = createRecipeType(ModRecipes.VACUUM_FREEZER);
	public static final RecipeType<RebornRecipe> WIRE_MILL = createRecipeType(ModRecipes.WIRE_MILL);

	public static final RecipeType<FluidGeneratorRecipe> THERMAL_GENERATOR = createFluidGeneratorRecipeType(TRContent.Machine.THERMAL_GENERATOR);
	public static final RecipeType<FluidGeneratorRecipe> GAS_GENERATOR = createFluidGeneratorRecipeType(TRContent.Machine.GAS_TURBINE);
	public static final RecipeType<FluidGeneratorRecipe> DIESEL_GENERATOR = createFluidGeneratorRecipeType(TRContent.Machine.DIESEL_GENERATOR);
	public static final RecipeType<FluidGeneratorRecipe> SEMI_FLUID_GENERATOR = createFluidGeneratorRecipeType(TRContent.Machine.SEMI_FLUID_GENERATOR);
	public static final RecipeType<FluidGeneratorRecipe> PLASMA_GENERATOR = createFluidGeneratorRecipeType(TRContent.Machine.PLASMA_GENERATOR);

	public static final List<IModPlugin> ADDONS = new ArrayList<>();

	public TechRebornJEI() {
		if(FabricLoader.getInstance().isModLoaded("advanced_reborn")) {
			ADDONS.add(new AdvancedRebornJEI());
		}
		if(FabricLoader.getInstance().isModLoaded("industrialreborn")) {
			ADDONS.add(new IndustrialRebornJEI());
		}
	}

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.registerSubtypeInterpreter(TRContent.CELL, new IIngredientSubtypeInterpreter<ItemStack>() {
			@Override
			public String apply(ItemStack ingredient, UidContext context) {
				if(ingredient.getItem() instanceof ItemFluidInfo info) {
					return BuiltInRegistries.FLUID.getKey(info.getFluid(ingredient)).toString();
				}
				return "";
			}
		});

		ADDONS.forEach(addon -> addon.registerItemSubtypes(registration));
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		jeiHelpers = registration.getJeiHelpers();

		if(checkDisabled()) {
			return;
		}

		registration.addRecipeCategories(new TwoItemToItemCenterRecipeCategory<>(ALLOY_SMELTER));
		registration.addRecipeCategories(new TwoItemToItemRecipeCategory<>(ASSEMBLING_MACHINE));
		registration.addRecipeCategories(new TwoItemToTwoItemRecipeCategory<>(BLAST_FURNACE));
		registration.addRecipeCategories(new TwoItemToFourItemCircleRecipeCategory<>(CENTRIFUGE));
		registration.addRecipeCategories(new TwoItemToItemCenterRecipeCategory<>(CHEMICAL_REACTOR));
		registration.addRecipeCategories(new ItemToItemRecipeCategory<>(COMPRESSOR));
		registration.addRecipeCategories(new TwoItemToThreeItemRecipeCategory<>(DISTILLATION_TOWER));
		registration.addRecipeCategories(new ItemToItemRecipeCategory<>(EXTRACTOR));
		registration.addRecipeCategories(new ItemToFluidRecipeCategory<>(FLUID_REPLICATOR));
		registration.addRecipeCategories(new FusionReactorRecipeCategory(FUSION_REACTOR));
		registration.addRecipeCategories(new ItemToItemRecipeCategory<>(GRINDER));
		registration.addRecipeCategories(new TwoItemToTwoItemRecipeCategory<>(IMPLOSION_COMPRESSOR));
		registration.addRecipeCategories(new TwoItemToFourItemRecipeCategory<>(INDUSTRIAL_ELECTROLYZER));
		registration.addRecipeCategories(new ItemFluidToFourItemRecipeCategory<>(INDUSTRIAL_GRINDER));
		registration.addRecipeCategories(new ItemFluidToThreeItemRecipeCategory<>(INDUSTRIAL_SAWMILL));
		registration.addRecipeCategories(new RollingMachineRecipeCategory(ROLLING_MACHINE));
		registration.addRecipeCategories(new ItemToItemRecipeCategory<>(SCRAPBOX));
		registration.addRecipeCategories(new TwoItemToItemCenterRecipeCategory<>(SOLID_CANNING_MACHINE));
		registration.addRecipeCategories(new ItemToItemRecipeCategory<>(VACUUM_FREEZER));
		registration.addRecipeCategories(new ItemToItemRecipeCategory<>(WIRE_MILL));

		registration.addRecipeCategories(new FluidGeneratorRecipeCategory(THERMAL_GENERATOR));
		registration.addRecipeCategories(new FluidGeneratorRecipeCategory(GAS_GENERATOR));
		registration.addRecipeCategories(new FluidGeneratorRecipeCategory(DIESEL_GENERATOR));
		registration.addRecipeCategories(new FluidGeneratorRecipeCategory(SEMI_FLUID_GENERATOR));
		registration.addRecipeCategories(new FluidGeneratorRecipeCategory(PLASMA_GENERATOR));

		ADDONS.forEach(addon -> addon.registerCategories(registration));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(ALLOY_SMELTER, recipeManager.getAllRecipesFor(ModRecipes.ALLOY_SMELTER));
		registration.addRecipes(ASSEMBLING_MACHINE, recipeManager.getAllRecipesFor(ModRecipes.ASSEMBLING_MACHINE));
		registration.addRecipes(BLAST_FURNACE, recipeManager.getAllRecipesFor(ModRecipes.BLAST_FURNACE));
		registration.addRecipes(CENTRIFUGE, recipeManager.getAllRecipesFor(ModRecipes.CENTRIFUGE));
		registration.addRecipes(CHEMICAL_REACTOR, recipeManager.getAllRecipesFor(ModRecipes.CHEMICAL_REACTOR));
		registration.addRecipes(COMPRESSOR, recipeManager.getAllRecipesFor(ModRecipes.COMPRESSOR));
		registration.addRecipes(DISTILLATION_TOWER, recipeManager.getAllRecipesFor(ModRecipes.DISTILLATION_TOWER));
		registration.addRecipes(EXTRACTOR, recipeManager.getAllRecipesFor(ModRecipes.EXTRACTOR));
		registration.addRecipes(FLUID_REPLICATOR, recipeManager.getAllRecipesFor(ModRecipes.FLUID_REPLICATOR));
		registration.addRecipes(FUSION_REACTOR, recipeManager.getAllRecipesFor(ModRecipes.FUSION_REACTOR));
		registration.addRecipes(GRINDER, recipeManager.getAllRecipesFor(ModRecipes.GRINDER));
		registration.addRecipes(IMPLOSION_COMPRESSOR, recipeManager.getAllRecipesFor(ModRecipes.IMPLOSION_COMPRESSOR));
		registration.addRecipes(INDUSTRIAL_ELECTROLYZER, recipeManager.getAllRecipesFor(ModRecipes.INDUSTRIAL_ELECTROLYZER));
		registration.addRecipes(INDUSTRIAL_GRINDER, recipeManager.getAllRecipesFor(ModRecipes.INDUSTRIAL_GRINDER));
		registration.addRecipes(INDUSTRIAL_SAWMILL, recipeManager.getAllRecipesFor(ModRecipes.INDUSTRIAL_SAWMILL));
		registration.addRecipes(ROLLING_MACHINE, recipeManager.getAllRecipesFor(ModRecipes.ROLLING_MACHINE));
		registration.addRecipes(SCRAPBOX, recipeManager.getAllRecipesFor(ModRecipes.SCRAPBOX));
		registration.addRecipes(SOLID_CANNING_MACHINE, recipeManager.getAllRecipesFor(ModRecipes.SOLID_CANNING_MACHINE));
		registration.addRecipes(VACUUM_FREEZER, recipeManager.getAllRecipesFor(ModRecipes.VACUUM_FREEZER));
		registration.addRecipes(WIRE_MILL, recipeManager.getAllRecipesFor(ModRecipes.WIRE_MILL));

		registration.addRecipes(THERMAL_GENERATOR, getFluidGeneratorRecipes(EFluidGenerator.THERMAL));
		registration.addRecipes(GAS_GENERATOR, getFluidGeneratorRecipes(EFluidGenerator.GAS));
		registration.addRecipes(DIESEL_GENERATOR, getFluidGeneratorRecipes(EFluidGenerator.DIESEL));
		registration.addRecipes(SEMI_FLUID_GENERATOR, getFluidGeneratorRecipes(EFluidGenerator.SEMIFLUID));
		registration.addRecipes(PLASMA_GENERATOR, getFluidGeneratorRecipes(EFluidGenerator.PLASMA));

		ADDONS.forEach(addon -> addon.registerRecipes(registration));
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.addRecipeTransferHandler(new BuiltScreenHandlerTransferInfo<>("rollingmachine", ROLLING_MACHINE, IntStream.range(0, 9)));
		registration.addRecipeTransferHandler(new BuiltScreenHandlerTransferInfo<>("autocraftingtable", RecipeTypes.CRAFTING, IntStream.range(0, 9)));

		ADDONS.forEach(addon -> addon.registerRecipeTransferHandlers(registration));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.addRecipeCatalyst(TRContent.Machine.IRON_ALLOY_FURNACE, ALLOY_SMELTER, RecipeTypes.FUELING);
		registration.addRecipeCatalyst(TRContent.Machine.ALLOY_SMELTER, ALLOY_SMELTER);
		registration.addRecipeCatalyst(TRContent.Machine.ASSEMBLY_MACHINE, ASSEMBLING_MACHINE);
		registration.addRecipeCatalyst(TRContent.Machine.INDUSTRIAL_BLAST_FURNACE, BLAST_FURNACE);
		registration.addRecipeCatalyst(TRContent.Machine.INDUSTRIAL_CENTRIFUGE, CENTRIFUGE);
		registration.addRecipeCatalyst(TRContent.Machine.CHEMICAL_REACTOR, CHEMICAL_REACTOR);
		registration.addRecipeCatalyst(TRContent.Machine.COMPRESSOR, COMPRESSOR);
		registration.addRecipeCatalyst(TRContent.Machine.DISTILLATION_TOWER, DISTILLATION_TOWER);
		registration.addRecipeCatalyst(TRContent.Machine.EXTRACTOR, EXTRACTOR);
		registration.addRecipeCatalyst(TRContent.Machine.FLUID_REPLICATOR, FLUID_REPLICATOR);
		registration.addRecipeCatalyst(TRContent.Machine.FUSION_CONTROL_COMPUTER, FUSION_REACTOR);
		registration.addRecipeCatalyst(TRContent.Machine.GRINDER, GRINDER);
		registration.addRecipeCatalyst(TRContent.Machine.IMPLOSION_COMPRESSOR, IMPLOSION_COMPRESSOR);
		registration.addRecipeCatalyst(TRContent.Machine.INDUSTRIAL_ELECTROLYZER, INDUSTRIAL_ELECTROLYZER);
		registration.addRecipeCatalyst(TRContent.Machine.INDUSTRIAL_GRINDER, INDUSTRIAL_GRINDER);
		registration.addRecipeCatalyst(TRContent.Machine.INDUSTRIAL_SAWMILL, INDUSTRIAL_SAWMILL);
		registration.addRecipeCatalyst(TRContent.Machine.ROLLING_MACHINE, ROLLING_MACHINE);
		registration.addRecipeCatalyst(TRContent.SCRAP_BOX, SCRAPBOX);
		registration.addRecipeCatalyst(TRContent.Machine.SCRAPBOXINATOR, SCRAPBOX);
		registration.addRecipeCatalyst(TRContent.Machine.SOLID_CANNING_MACHINE, SOLID_CANNING_MACHINE);
		registration.addRecipeCatalyst(TRContent.Machine.VACUUM_FREEZER, VACUUM_FREEZER);
		registration.addRecipeCatalyst(TRContent.Machine.WIRE_MILL, WIRE_MILL);

		registration.addRecipeCatalyst(TRContent.Machine.THERMAL_GENERATOR, THERMAL_GENERATOR);
		registration.addRecipeCatalyst(TRContent.Machine.GAS_TURBINE, GAS_GENERATOR);
		registration.addRecipeCatalyst(TRContent.Machine.DIESEL_GENERATOR, DIESEL_GENERATOR);
		registration.addRecipeCatalyst(TRContent.Machine.SEMI_FLUID_GENERATOR, SEMI_FLUID_GENERATOR);
		registration.addRecipeCatalyst(TRContent.Machine.PLASMA_GENERATOR, PLASMA_GENERATOR);

		registration.addRecipeCatalyst(TRContent.Machine.AUTO_CRAFTING_TABLE, RecipeTypes.CRAFTING);
		registration.addRecipeCatalyst(TRContent.Machine.IRON_FURNACE, RecipeTypes.SMELTING, RecipeTypes.FUELING);
		registration.addRecipeCatalyst(TRContent.Machine.ELECTRIC_FURNACE, RecipeTypes.SMELTING);

		registration.addRecipeCatalyst(TRContent.Machine.SOLID_FUEL_GENERATOR, RecipeTypes.FUELING);

		ADDONS.forEach(addon -> addon.registerRecipeCatalysts(registration));
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.addRecipeClickArea(GuiAlloyFurnace.class, 158, 5, 12, 12, ALLOY_SMELTER, RecipeTypes.FUELING);
		registration.addRecipeClickArea(GuiAlloySmelter.class, 158, 5, 12, 12, ALLOY_SMELTER);
		registration.addRecipeClickArea(GuiAssemblingMachine.class, 158, 5, 12, 12, ASSEMBLING_MACHINE);
		registration.addRecipeClickArea(GuiBlastFurnace.class, 158, 5, 12, 12, BLAST_FURNACE);
		registration.addRecipeClickArea(GuiCentrifuge.class, 158, 5, 12, 12, CENTRIFUGE);
		registration.addRecipeClickArea(GuiChemicalReactor.class, 158, 5, 12, 12, CHEMICAL_REACTOR);
		registration.addRecipeClickArea(GuiCompressor.class, 158, 5, 12, 12, COMPRESSOR);
		registration.addRecipeClickArea(GuiDistillationTower.class, 158, 5, 12, 12, DISTILLATION_TOWER);
		registration.addRecipeClickArea(GuiExtractor.class, 158, 5, 12, 12, EXTRACTOR);
		registration.addRecipeClickArea(GuiFluidReplicator.class, 158, 5, 12, 12, FLUID_REPLICATOR);
		registration.addRecipeClickArea(GuiFusionReactor.class, 158, 5, 12, 12, FUSION_REACTOR);
		registration.addRecipeClickArea(GuiGrinder.class, 158, 5, 12, 12, GRINDER);
		registration.addRecipeClickArea(GuiImplosionCompressor.class, 158, 5, 12, 12, IMPLOSION_COMPRESSOR);
		registration.addRecipeClickArea(GuiIndustrialElectrolyzer.class, 158, 5, 12, 12, INDUSTRIAL_ELECTROLYZER);
		registration.addRecipeClickArea(GuiIndustrialGrinder.class, 158, 5, 12, 12, INDUSTRIAL_GRINDER);
		registration.addRecipeClickArea(GuiIndustrialSawmill.class, 158, 5, 12, 12, INDUSTRIAL_SAWMILL);
		registration.addRecipeClickArea(GuiRollingMachine.class, 158, 5, 12, 12, ROLLING_MACHINE);
		registration.addRecipeClickArea(GuiScrapboxinator.class, 158, 5, 12, 12, SCRAPBOX);
		registration.addRecipeClickArea(GuiSolidCanningMachine.class, 158, 5, 12, 12, SOLID_CANNING_MACHINE);
		registration.addRecipeClickArea(GuiVacuumFreezer.class, 158, 5, 12, 12, VACUUM_FREEZER);
		registration.addRecipeClickArea(GuiWireMill.class, 158, 5, 12, 12, WIRE_MILL);

		registration.addRecipeClickArea(GuiThermalGenerator.class, 158, 5, 12, 12, THERMAL_GENERATOR);
		registration.addRecipeClickArea(GuiGasTurbine.class, 158, 5, 12, 12, GAS_GENERATOR);
		registration.addRecipeClickArea(GuiDieselGenerator.class, 158, 5, 12, 12, DIESEL_GENERATOR);
		registration.addRecipeClickArea(GuiSemifluidGenerator.class, 158, 5, 12, 12, SEMI_FLUID_GENERATOR);
		registration.addRecipeClickArea(GuiPlasmaGenerator.class, 158, 5, 12, 12, PLASMA_GENERATOR);

		registration.addRecipeClickArea(GuiAutoCrafting.class, 158, 18, 12, 12, RecipeTypes.CRAFTING);
		registration.addRecipeClickArea(GuiIronFurnace.class, 158, 5, 12, 12, RecipeTypes.SMELTING, RecipeTypes.FUELING);
		registration.addRecipeClickArea(GuiElectricFurnace.class, 158, 5, 12, 12, RecipeTypes.SMELTING);

		registration.addRecipeClickArea(GuiGenerator.class, 158, 5, 12, 12, RecipeTypes.FUELING);

		registration.addGenericGuiContainerHandler(GuiBase.class, new GuiBaseExtraAreaHandler());

		RecipeClickAreaRenderable.clearEntries();;

		RecipeClickAreaRenderable.addEntry(GuiAlloyFurnace.class);
		RecipeClickAreaRenderable.addEntry(GuiAlloySmelter.class);
		RecipeClickAreaRenderable.addEntry(GuiAssemblingMachine.class);
		RecipeClickAreaRenderable.addEntry(GuiBlastFurnace.class);
		RecipeClickAreaRenderable.addEntry(GuiCentrifuge.class);
		RecipeClickAreaRenderable.addEntry(GuiChemicalReactor.class);
		RecipeClickAreaRenderable.addEntry(GuiCompressor.class);
		RecipeClickAreaRenderable.addEntry(GuiDistillationTower.class);
		RecipeClickAreaRenderable.addEntry(GuiExtractor.class);
		RecipeClickAreaRenderable.addEntry(GuiFluidReplicator.class);
		RecipeClickAreaRenderable.addEntry(GuiFusionReactor.class);
		RecipeClickAreaRenderable.addEntry(GuiGrinder.class);
		RecipeClickAreaRenderable.addEntry(GuiImplosionCompressor.class);
		RecipeClickAreaRenderable.addEntry(GuiIndustrialElectrolyzer.class);
		RecipeClickAreaRenderable.addEntry(GuiIndustrialGrinder.class);
		RecipeClickAreaRenderable.addEntry(GuiIndustrialSawmill.class);
		RecipeClickAreaRenderable.addEntry(GuiRollingMachine.class);
		RecipeClickAreaRenderable.addEntry(GuiScrapboxinator.class);
		RecipeClickAreaRenderable.addEntry(GuiSolidCanningMachine.class);
		RecipeClickAreaRenderable.addEntry(GuiVacuumFreezer.class);
		RecipeClickAreaRenderable.addEntry(GuiWireMill.class);

		RecipeClickAreaRenderable.addEntry(GuiThermalGenerator.class);
		RecipeClickAreaRenderable.addEntry(GuiGasTurbine.class);
		RecipeClickAreaRenderable.addEntry(GuiDieselGenerator.class);
		RecipeClickAreaRenderable.addEntry(GuiSemifluidGenerator.class);
		RecipeClickAreaRenderable.addEntry(GuiPlasmaGenerator.class);

		RecipeClickAreaRenderable.addEntry(GuiAutoCrafting.class, 158, 18);
		RecipeClickAreaRenderable.addEntry(GuiIronFurnace.class);
		RecipeClickAreaRenderable.addEntry(GuiElectricFurnace.class);

		RecipeClickAreaRenderable.addEntry(GuiGenerator.class);

		ADDONS.forEach(addon -> addon.registerGuiHandlers(registration));
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		TechRebornJEI.jeiRuntime = jeiRuntime;

		if(checkDisabled()) {
			return;
		}

		ADDONS.forEach(addon -> addon.onRuntimeAvailable(jeiRuntime));
	}

	public static RecipeType<RebornRecipe> createRecipeType(RebornRecipeType<RebornRecipe> rebornRecipeType) {
		return new RecipeType<>(rebornRecipeType.name(), RebornRecipe.class);
	}

	public static <R extends RebornRecipe> RecipeType<R> createRecipeType(RebornRecipeType<R> rebornRecipeType, Class<R> rebornRecipeClass) {
		return new RecipeType<>(rebornRecipeType.name(), rebornRecipeClass);
	}

	public static RecipeType<FluidGeneratorRecipe> createFluidGeneratorRecipeType(TRContent.Machine machine) {
		return RecipeType.create("techreborn", machine.name, FluidGeneratorRecipe.class);
	}

	public static List<FluidGeneratorRecipe> getFluidGeneratorRecipes(EFluidGenerator generator) {
		return GeneratorRecipeHelper.getFluidRecipesForGenerator(generator).getRecipes().stream().
				sorted(Comparator.comparing(recipe -> BuiltInRegistries.FLUID.getKey(recipe.fluid()))).toList();
	}

	public boolean checkDisabled() {
		if(FabricLoader.getInstance().isModLoaded("rei_plugin_compatibilities")) {
			LOGGER.warn("TechRebornJEI is disabled with REIPC as Tech Reborn has native REI support");
			return true;
		}
		if(FabricLoader.getInstance().isModLoaded("extra-mod-integrations")) {
			LOGGER.warn("TechRebornJEI is disabled with ExMI");
			return true;
		}
		if(FabricLoader.getInstance().isModLoaded("emitechreborn")) {
			LOGGER.warn("TechRebornJEI is disabled with EmiTechReborn");
			return true;
		}
		return false;
	}
}
