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
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiSprites;
import thelm.techrebornjei.SpriteDrawable;
import thelm.techrebornjei.TechRebornJEI;

public abstract class AbstractRecipeCategory<R> implements IRecipeCategory<R> {

	public static final NumberFormat TIME_FORMAT = new DecimalFormat("###.##");

	public static final SpriteDrawable SLOT = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.SLOT), 18, 18);

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

	public Font font() {
		return Minecraft.getInstance().font;
	}

	public IJeiHelpers jeiHelpers() {
		return TechRebornJEI.jeiHelpers;
	}

	public IGuiHelper guiHelper() {
		return jeiHelpers().getGuiHelper();
	}
}
