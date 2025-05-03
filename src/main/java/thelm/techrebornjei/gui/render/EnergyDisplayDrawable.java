package thelm.techrebornjei.gui.render;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiSprites;

public record EnergyDisplayDrawable(EntryAnimation animation) implements IDrawable {

	public static final EnergyDisplayDrawable UP = new EnergyDisplayDrawable(EntryAnimation.UP);
	public static final EnergyDisplayDrawable DOWN = new EnergyDisplayDrawable(EntryAnimation.DOWN);
	public static final EnergyDisplayDrawable STATIC = new EnergyDisplayDrawable(EntryAnimation.STATIC);

	public static final int WIDTH = 14;
	public static final int HEIGHT = 50;

	public static final SpriteDrawable POWER_BAR_BASE = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.POWER_BAR_BASE), WIDTH, HEIGHT);
	public static final SpriteDrawable POWER_BAR_OVERLAY = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.POWER_BAR_OVERLAY), WIDTH - 2, HEIGHT - 2);

	public static EnergyDisplayDrawable up(int duration) {
		return new EnergyDisplayDrawable(EntryAnimation.up(duration));
	}

	public static EnergyDisplayDrawable down(int duration) {
		return new EnergyDisplayDrawable(EntryAnimation.down(duration));
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
		float mask = 0;
		if(animation.direction() != EntryAnimation.Direction.STATIC) {
			Minecraft minecraft = Minecraft.getInstance();
			int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode());
			mask = Math.round(System.currentTimeMillis() % animation.duration() * guiScale * (HEIGHT - 2) / (float)animation.duration()) / (float)guiScale;
			if(animation.direction() == EntryAnimation.Direction.UP) {
				mask = HEIGHT - 2 - mask;
			}
		}
		POWER_BAR_BASE.draw(guiGraphics, xOffset, yOffset);
		POWER_BAR_OVERLAY.draw(guiGraphics, xOffset + 1, yOffset + 1 + mask, 0, mask, 0, 0);
	}

	public static boolean isMouseOver(int x, int y, double mouseX, double mouseY) {
		return mouseX >= x && mouseX < x + WIDTH && mouseY >= y && mouseY < y + HEIGHT;
	}
}
