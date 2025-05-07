package thelm.techrebornjei.recipe.category;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiSprites;
import reborncore.common.fluid.container.FluidInstance;
import thelm.jeidrawables.gui.render.SpriteDrawable;
import thelm.techrebornjei.TechRebornJEI;

public abstract class AbstractRecipeCategory<R> implements IRecipeCategory<R> {

	public static final NumberFormat TIME_FORMAT = new DecimalFormat("###.##");

	public static final SpriteDrawable SLOT = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.SLOT), 18, 18);
	public static final SpriteDrawable TANK_BACKGROUND = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.TANK_BACKGROUND), 22, 56);
	public static final SpriteDrawable TANK_FOREGROUND = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.TANK_FOREGROUND), 16, 50);

	public final RecipeType<R> recipeType;
	public final Component title;

	public AbstractRecipeCategory(RecipeType<R> recipeType, Component title) {
		this.recipeType = recipeType;
		this.title = title;
	}

	public AbstractRecipeCategory(RecipeType<R> recipeType) {
		this.recipeType = recipeType;
		this.title = Component.translatable(recipeType.getUid().toString());
	}

	@Override
	public RecipeType<R> getRecipeType() {
		return recipeType;
	}

	@Override
	public Component getTitle() {
		return title;
	}

	@Override
	public int getWidth() {
		return 140;
	}

	@Override
	public int getHeight() {
		return 56;
	}

	@Override
	public IDrawable getIcon() {
		return null;
	}

	public RegistryAccess registryAccess() {
		return Minecraft.getInstance().level.registryAccess();
	}

	public Font font() {
		return Minecraft.getInstance().font;
	}

	public IJeiHelpers jeiHelpers() {
		return TechRebornJEI.jeiHelpers;
	}

	public IPlatformFluidHelper<?> fluidHelper() {
		return jeiHelpers().getPlatformFluidHelper();
	}

	public IRecipeSlotBuilder addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, IDrawable background) {
		return builder.addSlot(ingredientRole, x, y).setBackground(background, 8 - background.getWidth() / 2, 8 - background.getHeight() / 2);
	}

	public IRecipeSlotBuilder addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, List<ItemStack> itemStacks, IDrawable background) {
		return addItem(builder, ingredientRole, x, y, background).addItemStacks(itemStacks);
	}

	public IRecipeSlotBuilder addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, ItemStack itemStack, IDrawable background) {
		return addItem(builder, ingredientRole, x, y, background).addItemStack(itemStack);
	}

	public IRecipeSlotBuilder addFluid(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, FluidInstance fluidInstance) {
		Fluid fluid = fluidInstance.getFluid();
		long amount = fluidInstance.getAmount().getRawValue() / (FluidConstants.BUCKET / fluidHelper().bucketVolume());
		CompoundTag data = fluidInstance.getTag() == null ? null : fluidInstance.getTag().copy();
		return builder.addSlot(ingredientRole, x, y).addFluidStack(fluid, amount, data).setBackground(TANK_BACKGROUND, -3, -3).setOverlay(TANK_FOREGROUND, 0, 0).setFluidRenderer(amount, false, 16, 50);
	}
}
