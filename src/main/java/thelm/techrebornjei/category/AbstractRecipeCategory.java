package thelm.techrebornjei.category;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import reborncore.client.gui.guibuilder.GuiBuilder;
import thelm.techrebornjei.BlankDrawable;
import thelm.techrebornjei.ResourceDrawable;
import thelm.techrebornjei.TechRebornJEIPlugin;

public abstract class AbstractRecipeCategory<R> implements IRecipeCategory<R> {

	public static final NumberFormat TIME_FORMAT = new DecimalFormat("###.##");

	public static final ResourceDrawable SLOT = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 150, 0, 18, 18);

	public final RecipeType<R> recipeType;
	public final Component title;
	public final IDrawable background;

	public AbstractRecipeCategory(RecipeType<R> recipeType, Component title) {
		this.recipeType = recipeType;
		this.title = title;
		background = new BlankDrawable(getWidth(), getHeight());
	}

	public AbstractRecipeCategory(RecipeType<R> recipeType) {
		this.recipeType = recipeType;
		this.title = Component.translatable(recipeType.getUid().toString());
		background = new BlankDrawable(getWidth(), getHeight());
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
	public IDrawable getBackground() {
		return background;
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

	public Font font() {
		return Minecraft.getInstance().font;
	}

	public IJeiHelpers jeiHelpers() {
		return TechRebornJEIPlugin.jeiHelpers;
	}

	public IGuiHelper guiHelper() {
		return jeiHelpers().getGuiHelper();
	}
}
