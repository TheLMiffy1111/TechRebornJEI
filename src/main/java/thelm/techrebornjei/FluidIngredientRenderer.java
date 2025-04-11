package thelm.techrebornjei;

import java.text.NumberFormat;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import mezz.jei.api.fabric.ingredients.fluids.IJeiFluidIngredient;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluids;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiSprites;

public record FluidIngredientRenderer(EntryAnimation animation) implements IIngredientRenderer<IJeiFluidIngredient> {

	public static final FluidIngredientRenderer UP = new FluidIngredientRenderer(EntryAnimation.UP);
	public static final FluidIngredientRenderer DOWN = new FluidIngredientRenderer(EntryAnimation.DOWN);
	public static final FluidIngredientRenderer STATIC = new FluidIngredientRenderer(EntryAnimation.STATIC);

	public static final int WIDTH = 16;
	public static final int HEIGHT = 50;

	public static final NumberFormat INTEGER_FORMAT = NumberFormat.getIntegerInstance();

	public static final SpriteDrawable TANK_BACKGROUND = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.TANK_BACKGROUND), WIDTH + 6, HEIGHT + 6);
	public static final SpriteDrawable TANK_FOREGROUND = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.TANK_FOREGROUND), WIDTH, HEIGHT);

	public static FluidIngredientRenderer up(int duration) {
		return new FluidIngredientRenderer(EntryAnimation.up(duration));
	}

	public static FluidIngredientRenderer down(int duration) {
		return new FluidIngredientRenderer(EntryAnimation.down(duration));
	}

	@Override
	public void render(GuiGraphics guiGraphics, IJeiFluidIngredient ingredient) {
		TANK_BACKGROUND.draw(guiGraphics, -3, -3);
		float drawHeight = HEIGHT;
		if(animation.direction() != EntryAnimation.Direction.STATIC) {
			drawHeight = System.currentTimeMillis() % animation.duration() / (float)animation.duration() * HEIGHT;
			if(animation.direction() == EntryAnimation.Direction.DOWN) {
				drawHeight = HEIGHT - drawHeight;
			}
		}
		drawFluid(guiGraphics, getFluidVariant(ingredient), drawHeight);
		TANK_FOREGROUND.draw(guiGraphics);
	}

	public void drawFluid(GuiGraphics guiGraphics, FluidVariant fluidVariant, float drawHeight) {
		TextureAtlasSprite sprite = FluidVariantRendering.getSprite(fluidVariant);
		if(sprite == null) {
			return;
		}
		int color = FluidVariantRendering.getColor(fluidVariant);
		RenderSystem.setShaderColor((color >> 16 & 0xFF) / 255F, (color >> 8 & 0xFF) / 255F, (color & 0xFF) / 255F, 1F);
		GuiRenderUtil.blitTiledSprite(guiGraphics, sprite, 0, getHeight() - drawHeight, getWidth(), drawHeight, 16, 16);
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	@Override
	public List<Component> getTooltip(IJeiFluidIngredient ingredient, TooltipFlag tooltipFlag) {
		if(ingredient.getFluid() == Fluids.EMPTY) {
			return List.of();
		}
		List<Component> tooltip = FluidVariantRendering.getTooltip(getFluidVariant(ingredient), tooltipFlag);
		long mb = ingredient.getAmount() / (FluidConstants.BUCKET / 1000);
		long sp = ingredient.getAmount() % (FluidConstants.BUCKET / 1000);
		if(sp > 0) {
			tooltip.add(Component.translatable("jei.tooltip.liquid.amount.with.fraction", INTEGER_FORMAT.format(mb), INTEGER_FORMAT.format(sp)).withStyle(ChatFormatting.GRAY));
		}
		else {
			tooltip.add(Component.translatable("jei.tooltip.liquid.amount", INTEGER_FORMAT.format(mb)).withStyle(ChatFormatting.GRAY));
		}
		return tooltip;
	}

	public FluidVariant getFluidVariant(IJeiFluidIngredient ingredient) {
		return FluidVariant.of(ingredient.getFluid(), ingredient.getTag().orElse(null));
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}
}
