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

	public static final FluidIngredientRenderer UPWARDS = new FluidIngredientRenderer(EntryAnimation.UPWARDS);
	public static final FluidIngredientRenderer DOWNWARDS = new FluidIngredientRenderer(EntryAnimation.DOWNWARDS);
	public static final FluidIngredientRenderer NONE = new FluidIngredientRenderer(EntryAnimation.NONE);

	public static final NumberFormat INTEGER_FORMAT = NumberFormat.getIntegerInstance();

	@Override
	public void render(GuiGraphics guiGraphics, IJeiFluidIngredient ingredient) {
		int width = getWidth();
		int height = getHeight();
		GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(GuiSprites.TANK_BACKGROUND), -3, -3, width + 6, height + 6);
		float drawHeight;
		if(animation.type() != EntryAnimation.Type.NONE) {
			drawHeight = System.currentTimeMillis() % animation.duration() / (float)animation.duration() * height;
			if(animation.type() == EntryAnimation.Type.DOWNWARDS) {
				drawHeight = height - drawHeight;
			}
		}
		else {
			drawHeight = height;
		}
		drawFluid(guiGraphics, getFluidVariant(ingredient), drawHeight);
		GuiRenderUtil.blitSprite(guiGraphics, GuiBase.getSprite(GuiSprites.TANK_FOREGROUND), 0, 0, width, height);
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

	@SuppressWarnings("removal")
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
	public int getHeight() {
		return 50;
	}
}
