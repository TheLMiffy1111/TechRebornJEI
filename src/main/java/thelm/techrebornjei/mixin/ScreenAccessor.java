package thelm.techrebornjei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;

@Mixin(Screen.class)
public interface ScreenAccessor {

	@Invoker("addRenderableOnly")
	Widget trjei$addRenderable(Widget renderable);
}
