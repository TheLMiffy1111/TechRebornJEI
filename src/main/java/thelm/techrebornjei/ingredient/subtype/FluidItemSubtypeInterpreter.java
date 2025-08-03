package thelm.techrebornjei.ingredient.subtype;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import reborncore.common.fluid.container.ItemFluidInfo;

public class FluidItemSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {

	@Override
	public String apply(ItemStack ingredient, UidContext context) {
		if(ingredient.getItem() instanceof ItemFluidInfo info) {
			return Registry.FLUID.getKey(info.getFluid(ingredient)).toString();
		}
		return IIngredientSubtypeInterpreter.NONE;
	}
}
