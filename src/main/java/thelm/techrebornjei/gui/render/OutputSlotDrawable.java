package thelm.techrebornjei.gui.render;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.drawable.IDrawable;
import reborncore.client.gui.guibuilder.GuiBuilder;

public record OutputSlotDrawable(boolean left, boolean center, boolean right) implements IDrawable {

	public static final OutputSlotDrawable SINGLE = new OutputSlotDrawable(true, true, true);
	public static final OutputSlotDrawable LEFT = new OutputSlotDrawable(true, true, false);
	public static final OutputSlotDrawable CENTER = new OutputSlotDrawable(false, true, false);
	public static final OutputSlotDrawable RIGHT = new OutputSlotDrawable(false, true, true);

	public static final ResourceDrawable SLOT_BAR_LEFT = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 150, 122, 3, 26);
	public static final ResourceDrawable SLOT_BAR_CENTER = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 153, 122, 20, 26);
	public static final ResourceDrawable SLOT_BAR_RIGHT = new ResourceDrawable(GuiBuilder.defaultTextureSheet, 173, 122, 3, 26);

	@Override
	public int getWidth() {
		return 26;
	}

	@Override
	public int getHeight() {
		return 26;
	}

	@Override
	public void draw(PoseStack poseStack, int xOffset, int yOffset) {
		if(left) {
			SLOT_BAR_LEFT.draw(poseStack, xOffset, yOffset);
		}
		if(center) {
			SLOT_BAR_CENTER.draw(poseStack, xOffset + 3, yOffset);
		}
		if(right) {
			SLOT_BAR_RIGHT.draw(poseStack, xOffset + 23, yOffset);
		}
	}
}
