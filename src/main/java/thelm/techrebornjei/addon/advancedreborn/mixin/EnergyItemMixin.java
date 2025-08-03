package thelm.techrebornjei.addon.advancedreborn.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.pitan76.advancedreborn.items.AdvancedBattery;
import net.pitan76.advancedreborn.items.NanoSuitItem;
import reborncore.common.powerSystem.RcEnergyItem;

@Mixin({AdvancedBattery.class, NanoSuitItem.class})
public abstract class EnergyItemMixin extends Item implements RcEnergyItem {

	private EnergyItemMixin(Item.Properties properties) {
		super(properties);
	}

	@Override
	public void fillItemCategory(CreativeModeTab category, NonNullList<ItemStack> items) {
		super.fillItemCategory(category, items);
		if(allowdedIn(category) && getEnergyCapacity() > 0) {
			ItemStack stack = new ItemStack(this);
			setStoredEnergy(stack, getEnergyCapacity());
			items.add(stack);
		}
	}
}
