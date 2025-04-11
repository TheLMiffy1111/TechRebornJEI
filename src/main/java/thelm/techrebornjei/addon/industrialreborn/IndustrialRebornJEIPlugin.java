package thelm.techrebornjei.addon.industrialreborn;

import me.munchii.industrialreborn.client.gui.GuiFluidTransposer;
import me.munchii.industrialreborn.init.IRContent;
import me.munchii.industrialreborn.init.IRRecipes;
import me.munchii.industrialreborn.recipe.FluidTransposerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import thelm.techrebornjei.RecipeClickAreaRenderable;
import thelm.techrebornjei.TechRebornJEIPlugin;
import thelm.techrebornjei.category.ItemFluidToItemRecipeCategory;

public class IndustrialRebornJEIPlugin implements IModPlugin {

	public static final ResourceLocation UID = new ResourceLocation("techrebornjei:industrialreborn");

	public static final RecipeType<FluidTransposerRecipe> FLUID_TRANSPOSER = TechRebornJEIPlugin.createRecipeType(IRRecipes.FLUID_TRANSPOSER, FluidTransposerRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new ItemFluidToItemRecipeCategory<>(FLUID_TRANSPOSER, Component.translatable("block.industrialreborn.fluid_transposer")));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(FLUID_TRANSPOSER, recipeManager.getAllRecipesFor(IRRecipes.FLUID_TRANSPOSER));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(IRContent.Machine.FLUID_TRANSPOSER), FLUID_TRANSPOSER);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(GuiFluidTransposer.class, 158, 5, 12, 12, FLUID_TRANSPOSER);

		RecipeClickAreaRenderable.addEntry(GuiFluidTransposer.class);
	}
}
