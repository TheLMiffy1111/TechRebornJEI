package thelm.techrebornjei.ingredient.subtype;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import reborncore.common.powerSystem.RcEnergyItem;

public class EnergyItemSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {

	@Override
	public String apply(ItemStack ingredient, UidContext context) {
		if(context == UidContext.Ingredient &&
				ingredient.getItem() instanceof RcEnergyItem energyItem &&
				energyItem.getEnergyCapacity(ingredient) > 0 &&
				energyItem.getStoredEnergy(ingredient) >= energyItem.getEnergyCapacity(ingredient)) {
			return "f";
		}
		return NONE;
	}
}
