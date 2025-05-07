package thelm.techrebornjei.gui.render;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiSprites;
import thelm.jeidrawables.gui.render.SpriteDrawable;
import thelm.jeidrawables.mixin.ScreenAccessor;

public record RecipeClickAreaRenderable(GuiBase<?> guiBase, int x, int y) implements Renderable {

	public static final SpriteDrawable JEI_ICON = new SpriteDrawable(() -> GuiBase.getSprite(GuiSprites.JEI_ICON), 2, 2, 12, 12, 16, 16);

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		if(!guiBase.hideGuiElements()) {
			JEI_ICON.draw(guiGraphics, guiBase.getGuiLeft() + x, guiBase.getGuiTop() + y);
		}
	}

	static final List<Entry> ENTRIES = new ArrayList<>();

	public static void addEntry(Predicate<GuiBase<?>> predicate, int x, int y) {
		ENTRIES.add(new Entry(predicate, x, y));
	}

	public static void addEntry(Predicate<GuiBase<?>> predicate) {
		ENTRIES.add(new Entry(predicate, 158, 5));
	}

	public static void addEntry(Class<? extends GuiBase<?>> guiClass, int x, int y) {
		addEntry(guiClass::isInstance, x, y);
	}

	public static void addEntry(Class<? extends GuiBase<?>> guiClass) {
		addEntry(guiClass::isInstance, 158, 5);
	}

	public static void clearEntries() {
		ENTRIES.clear();
	}

	static {
		ScreenEvents.AFTER_INIT.register(RecipeClickAreaRenderable::onAfterScreenInit);
	}

	static void onAfterScreenInit(Minecraft minecraft, Screen screen, int scaledWidth, int scaledHeight) {
		if(screen instanceof GuiBase<?> guiBase) {
			for(Entry entry : ENTRIES) {
				if(entry.predicate.test(guiBase)) {
					((ScreenAccessor)guiBase).jeidas$addRenderable(new RecipeClickAreaRenderable(guiBase, entry.x, entry.y));
					return;
				}
			}
		}
	}

	record Entry(Predicate<GuiBase<?>> predicate, int x, int y) {}
}
