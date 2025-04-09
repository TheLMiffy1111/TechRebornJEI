package thelm.techrebornjei;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiSprites;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.fluid.container.ItemFluidInfo;
import techreborn.client.gui.GuiAlloyFurnace;
import techreborn.client.gui.GuiAlloySmelter;
import techreborn.client.gui.GuiAssemblingMachine;
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
import techreborn.recipe.recipes.AssemblingMachineRecipe;
import techreborn.recipe.recipes.BlastFurnaceRecipe;
import techreborn.recipe.recipes.CentrifugeRecipe;
import techreborn.recipe.recipes.FluidGeneratorRecipe;
import techreborn.recipe.recipes.FluidReplicatorRecipe;
import techreborn.recipe.recipes.FusionReactorRecipe;
import techreborn.recipe.recipes.IndustrialGrinderRecipe;
import techreborn.recipe.recipes.IndustrialSawmillRecipe;
import techreborn.recipe.recipes.RollingMachineRecipe;
import thelm.techrebornjei.addon.advancedreborn.AdvancedRebornJEIPlugin;
import thelm.techrebornjei.category.FluidGeneratorRecipeCategory;
import thelm.techrebornjei.category.FusionReactorRecipeCategory;
import thelm.techrebornjei.category.ItemFluidToFourItemRecipeCategory;
import thelm.techrebornjei.category.ItemFluidToThreeItemRecipeCategory;
import thelm.techrebornjei.category.ItemToFluidRecipeCategory;
import thelm.techrebornjei.category.ItemToItemRecipeCategory;
import thelm.techrebornjei.category.RollingMachineRecipeCategory;
import thelm.techrebornjei.category.TwoItemToFourItemCircleRecipeCategory;
import thelm.techrebornjei.category.TwoItemToFourItemRecipeCategory;
import thelm.techrebornjei.category.TwoItemToItemCenterRecipeCategory;
import thelm.techrebornjei.category.TwoItemToItemRecipeCategory;
import thelm.techrebornjei.category.TwoItemToThreeItemRecipeCategory;
import thelm.techrebornjei.category.TwoItemToTwoItemRecipeCategory;
import thelm.techrebornjei.mixin.ScreenAccessor;

public class TechRebornJEIPlugin implements IModPlugin {

	public static final ResourceLocation UID = ResourceLocation.parse("techrebornjei:techreborn");

	public static IJeiHelpers jeiHelpers;
	public static IJeiRuntime jeiRuntime;

	public static final RecipeType<RecipeHolder<RebornRecipe>> ALLOY_SMELTER = RecipeType.createFromVanilla(ModRecipes.ALLOY_SMELTER);
	public static final RecipeType<RecipeHolder<AssemblingMachineRecipe>> ASSEMBLING_MACHINE = RecipeType.createFromVanilla(ModRecipes.ASSEMBLING_MACHINE);
	public static final RecipeType<RecipeHolder<BlastFurnaceRecipe>> BLAST_FURNACE = RecipeType.createFromVanilla(ModRecipes.BLAST_FURNACE);
	public static final RecipeType<RecipeHolder<CentrifugeRecipe>> CENTRIFUGE = RecipeType.createFromVanilla(ModRecipes.CENTRIFUGE);
	public static final RecipeType<RecipeHolder<RebornRecipe>> CHEMICAL_REACTOR = RecipeType.createFromVanilla(ModRecipes.CHEMICAL_REACTOR);
	public static final RecipeType<RecipeHolder<RebornRecipe>> COMPRESSOR = RecipeType.createFromVanilla(ModRecipes.COMPRESSOR);
	public static final RecipeType<RecipeHolder<RebornRecipe>> DISTILLATION_TOWER = RecipeType.createFromVanilla(ModRecipes.DISTILLATION_TOWER);
	public static final RecipeType<RecipeHolder<RebornRecipe>> EXTRACTOR = RecipeType.createFromVanilla(ModRecipes.EXTRACTOR);
	public static final RecipeType<RecipeHolder<FluidReplicatorRecipe>> FLUID_REPLICATOR = RecipeType.createFromVanilla(ModRecipes.FLUID_REPLICATOR);
	public static final RecipeType<RecipeHolder<FusionReactorRecipe>> FUSION_REACTOR = RecipeType.createFromVanilla(ModRecipes.FUSION_REACTOR);
	public static final RecipeType<RecipeHolder<RebornRecipe>> GRINDER = RecipeType.createFromVanilla(ModRecipes.GRINDER);
	public static final RecipeType<RecipeHolder<RebornRecipe>> IMPLOSION_COMPRESSOR = RecipeType.createFromVanilla(ModRecipes.IMPLOSION_COMPRESSOR);
	public static final RecipeType<RecipeHolder<RebornRecipe>> INDUSTRIAL_ELECTROLYZER = RecipeType.createFromVanilla(ModRecipes.INDUSTRIAL_ELECTROLYZER);
	public static final RecipeType<RecipeHolder<IndustrialGrinderRecipe>> INDUSTRIAL_GRINDER = RecipeType.createFromVanilla(ModRecipes.INDUSTRIAL_GRINDER);
	public static final RecipeType<RecipeHolder<IndustrialSawmillRecipe>> INDUSTRIAL_SAWMILL = RecipeType.createFromVanilla(ModRecipes.INDUSTRIAL_SAWMILL);
	public static final RecipeType<RecipeHolder<RollingMachineRecipe>> ROLLING_MACHINE = RecipeType.createFromVanilla(ModRecipes.ROLLING_MACHINE);
	public static final RecipeType<RecipeHolder<RebornRecipe>> SCRAPBOX = RecipeType.createFromVanilla(ModRecipes.SCRAPBOX);
	public static final RecipeType<RecipeHolder<RebornRecipe>> SOLID_CANNING_MACHINE = RecipeType.createFromVanilla(ModRecipes.SOLID_CANNING_MACHINE);
	public static final RecipeType<RecipeHolder<RebornRecipe>> VACUUM_FREEZER = RecipeType.createFromVanilla(ModRecipes.VACUUM_FREEZER);
	public static final RecipeType<RecipeHolder<RebornRecipe>> WIRE_MILL = RecipeType.createFromVanilla(ModRecipes.WIRE_MILL);

	public static final RecipeType<RecipeHolder<FluidGeneratorRecipe>> THERMAL_GENERATOR = RecipeType.createFromVanilla(ModRecipes.THERMAL_GENERATOR);
	public static final RecipeType<RecipeHolder<FluidGeneratorRecipe>> GAS_GENERATOR = RecipeType.createFromVanilla(ModRecipes.GAS_GENERATOR);
	public static final RecipeType<RecipeHolder<FluidGeneratorRecipe>> DIESEL_GENERATOR = RecipeType.createFromVanilla(ModRecipes.DIESEL_GENERATOR);
	public static final RecipeType<RecipeHolder<FluidGeneratorRecipe>> SEMI_FLUID_GENERATOR = RecipeType.createFromVanilla(ModRecipes.SEMI_FLUID_GENERATOR);
	public static final RecipeType<RecipeHolder<FluidGeneratorRecipe>> PLASMA_GENERATOR = RecipeType.createFromVanilla(ModRecipes.PLASMA_GENERATOR);

	public static final Set<Class<? extends GuiBase<?>>> ADD_JEI_BUTTON = new HashSet<>();

	public static final ResourceLocation ELEMENTS = ResourceLocation.parse("techrebornjei:textures/gui/elements.png");
	public static IDrawable outputSlot1;
	public static IDrawable outputSlot2;
	public static IDrawable outputSlot3;
	public static IDrawable outputSlot4;

	public static final List<IModPlugin> ADDONS = new ArrayList<>();

	public TechRebornJEIPlugin() {
		ADD_JEI_BUTTON.add(GuiAlloyFurnace.class);
		ADD_JEI_BUTTON.add(GuiAlloySmelter.class);
		ADD_JEI_BUTTON.add(GuiAssemblingMachine.class);
		ADD_JEI_BUTTON.add(GuiBlastFurnace.class);
		ADD_JEI_BUTTON.add(GuiCentrifuge.class);
		ADD_JEI_BUTTON.add(GuiChemicalReactor.class);
		ADD_JEI_BUTTON.add(GuiCompressor.class);
		ADD_JEI_BUTTON.add(GuiDistillationTower.class);
		ADD_JEI_BUTTON.add(GuiExtractor.class);
		ADD_JEI_BUTTON.add(GuiFluidReplicator.class);
		ADD_JEI_BUTTON.add(GuiFusionReactor.class);
		ADD_JEI_BUTTON.add(GuiGrinder.class);
		ADD_JEI_BUTTON.add(GuiImplosionCompressor.class);
		ADD_JEI_BUTTON.add(GuiIndustrialElectrolyzer.class);
		ADD_JEI_BUTTON.add(GuiIndustrialGrinder.class);
		ADD_JEI_BUTTON.add(GuiIndustrialSawmill.class);
		ADD_JEI_BUTTON.add(GuiRollingMachine.class);
		ADD_JEI_BUTTON.add(GuiScrapboxinator.class);
		ADD_JEI_BUTTON.add(GuiSolidCanningMachine.class);
		ADD_JEI_BUTTON.add(GuiVacuumFreezer.class);
		ADD_JEI_BUTTON.add(GuiWireMill.class);

		ADD_JEI_BUTTON.add(GuiThermalGenerator.class);
		ADD_JEI_BUTTON.add(GuiGasTurbine.class);
		ADD_JEI_BUTTON.add(GuiDieselGenerator.class);
		ADD_JEI_BUTTON.add(GuiSemifluidGenerator.class);
		ADD_JEI_BUTTON.add(GuiPlasmaGenerator.class);

		//ADD_JEI_BUTTON.add(GuiAutoCrafting.class);
		ADD_JEI_BUTTON.add(GuiIronFurnace.class);
		ADD_JEI_BUTTON.add(GuiElectricFurnace.class);

		ADD_JEI_BUTTON.add(GuiGenerator.class);

		if(FabricLoader.getInstance().isModLoaded("advanced_reborn")) {
			ADDONS.add(new AdvancedRebornJEIPlugin());
		}

		ScreenEvents.AFTER_INIT.register((minecraft, screen, scaledWidth, scaledHeight) -> {
			if(ADD_JEI_BUTTON.contains(screen.getClass())) {
				GuiBase<?> guiBase = (GuiBase<?>)screen;
				((ScreenAccessor)guiBase).trjei$addRenderable((guiGraphics, mouseX, mouseY, partialTick) -> {
					if(!guiBase.hideGuiElements()) {
						GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(GuiSprites.JEI_ICON), guiBase.getGuiLeft() + 158, guiBase.getGuiTop() + 5, 2, 2, 12, 12, 16, 16);
					}
				});
			}
		});
	}

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(TRContent.CELL, new ISubtypeInterpreter<ItemStack>() {
			@Override
			public Object getSubtypeData(ItemStack ingredient, UidContext context) {
				if(ingredient.getItem() instanceof ItemFluidInfo info) {
					return info.getFluid(ingredient);
				}
				return null;
			}

			@Override
			public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
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
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		outputSlot1 = guiHelper.drawableBuilder(ELEMENTS, 0, 0, 26, 26).setTextureSize(128, 64).build();
		outputSlot2 = guiHelper.drawableBuilder(ELEMENTS, 0, 26, 46, 26).setTextureSize(128, 64).build();
		outputSlot3 = guiHelper.drawableBuilder(ELEMENTS, 46, 26, 66, 26).setTextureSize(128, 64).build();
		outputSlot4 = guiHelper.drawableBuilder(ELEMENTS, 26, 0, 86, 26).setTextureSize(128, 64).build();

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
		registration.addRecipeCategories(new FluidGeneratorRecipeCategory(GAS_GENERATOR, Component.translatable("techreborn:gas_turbine")));
		registration.addRecipeCategories(new FluidGeneratorRecipeCategory(DIESEL_GENERATOR));
		registration.addRecipeCategories(new FluidGeneratorRecipeCategory(SEMI_FLUID_GENERATOR));
		registration.addRecipeCategories(new FluidGeneratorRecipeCategory(PLASMA_GENERATOR));

		ADDONS.forEach(addon -> addon.registerCategories(registration));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
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

		registration.addRecipes(THERMAL_GENERATOR, recipeManager.getAllRecipesFor(ModRecipes.THERMAL_GENERATOR));
		registration.addRecipes(GAS_GENERATOR, recipeManager.getAllRecipesFor(ModRecipes.GAS_GENERATOR));
		registration.addRecipes(DIESEL_GENERATOR, recipeManager.getAllRecipesFor(ModRecipes.DIESEL_GENERATOR));
		registration.addRecipes(SEMI_FLUID_GENERATOR, recipeManager.getAllRecipesFor(ModRecipes.SEMI_FLUID_GENERATOR));
		registration.addRecipes(PLASMA_GENERATOR, recipeManager.getAllRecipesFor(ModRecipes.PLASMA_GENERATOR));

		ADDONS.forEach(addon -> addon.registerRecipes(registration));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.IRON_ALLOY_FURNACE), ALLOY_SMELTER, RecipeTypes.FUELING);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.ALLOY_SMELTER), ALLOY_SMELTER);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.ASSEMBLY_MACHINE), ASSEMBLING_MACHINE);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.INDUSTRIAL_BLAST_FURNACE), BLAST_FURNACE);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.INDUSTRIAL_CENTRIFUGE), CENTRIFUGE);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.CHEMICAL_REACTOR), CHEMICAL_REACTOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.COMPRESSOR), COMPRESSOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.DISTILLATION_TOWER), DISTILLATION_TOWER);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.EXTRACTOR), EXTRACTOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.FLUID_REPLICATOR), FLUID_REPLICATOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.FUSION_CONTROL_COMPUTER), FUSION_REACTOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.GRINDER), GRINDER);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.IMPLOSION_COMPRESSOR), IMPLOSION_COMPRESSOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.INDUSTRIAL_ELECTROLYZER), INDUSTRIAL_ELECTROLYZER);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.INDUSTRIAL_GRINDER), INDUSTRIAL_GRINDER);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.INDUSTRIAL_SAWMILL), INDUSTRIAL_SAWMILL);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.ROLLING_MACHINE), ROLLING_MACHINE);
		registration.addRecipeCatalyst(new ItemStack(TRContent.SCRAP_BOX), SCRAPBOX);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.SCRAPBOXINATOR), SCRAPBOX);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.SOLID_CANNING_MACHINE), SOLID_CANNING_MACHINE);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.VACUUM_FREEZER), VACUUM_FREEZER);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.WIRE_MILL), WIRE_MILL);

		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.THERMAL_GENERATOR), THERMAL_GENERATOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.GAS_TURBINE), GAS_GENERATOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.DIESEL_GENERATOR), DIESEL_GENERATOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.SEMI_FLUID_GENERATOR), SEMI_FLUID_GENERATOR);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.PLASMA_GENERATOR), PLASMA_GENERATOR);

		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.AUTO_CRAFTING_TABLE), RecipeTypes.CRAFTING);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.IRON_FURNACE), RecipeTypes.SMELTING, RecipeTypes.FUELING);
		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.ELECTRIC_FURNACE), RecipeTypes.SMELTING);

		registration.addRecipeCatalyst(new ItemStack(TRContent.Machine.SOLID_FUEL_GENERATOR), RecipeTypes.FUELING);

		ADDONS.forEach(addon -> addon.registerRecipeCatalysts(registration));
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
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

		//registration.addRecipeClickArea(GuiAutoCrafting.class, 158, 5, 12, 12, RecipeTypes.CRAFTING);
		registration.addRecipeClickArea(GuiIronFurnace.class, 158, 5, 12, 12, RecipeTypes.SMELTING, RecipeTypes.FUELING);
		registration.addRecipeClickArea(GuiElectricFurnace.class, 158, 5, 12, 12, RecipeTypes.SMELTING);

		registration.addRecipeClickArea(GuiGenerator.class, 158, 5, 12, 12, RecipeTypes.FUELING);

		registration.addGenericGuiContainerHandler(GuiBase.class, new GuiBaseExtraAreaHandler());

		ADDONS.forEach(addon -> addon.registerGuiHandlers(registration));
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		TechRebornJEIPlugin.jeiRuntime = jeiRuntime;

		ADDONS.forEach(addon -> addon.onRuntimeAvailable(jeiRuntime));
	}
}
