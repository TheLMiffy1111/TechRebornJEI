package thelm.techrebornjei.addon.advancedreborn.event;

import ml.pkom.advancedreborn.AdvancedReborn;
import ml.pkom.advancedreborn.Items;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import reborncore.common.powerSystem.RcEnergyItem;

public class CreativeTabEventHandler implements ItemGroupEvents.ModifyEntriesAll {

	@Override
	public void modifyEntries(CreativeModeTab tab, FabricItemGroupEntries entries) {
		if(tab == AdvancedReborn.AR_GROUP) {
			addCharged(entries, Items.ADVANCED_BATTERY);
			addCharged(entries, Items.ADVANCED_BATTERY_2);
			addCharged(entries, Items.ADVANCED_BATTERY_3);
			addCharged(entries, Items.ADVANCED_BATTERY_4);
			addCharged(entries, Items.ADVANCED_BATTERY_5);
			addCharged(entries, Items.NANO_SUIT_HELMET);
			addCharged(entries, Items.NANO_SUIT_BODY_ARMOR);
			addCharged(entries, Items.NANO_SUIT_LEGGINGS);
			addCharged(entries, Items.NANO_SUIT_BOOTS);
			entries.accept(Items.BATPACK_4);
			addCharged(entries, Items.BATPACK_4);
			entries.accept(Items.BATPACK_16);
			addCharged(entries, Items.BATPACK_16);
			entries.accept(Items.BATPACK_64);
			addCharged(entries, Items.BATPACK_64);
			entries.accept(Items.BATPACK_128);
			addCharged(entries, Items.BATPACK_128);
		}
	}

	public void addCharged(FabricItemGroupEntries entries, Item item) {
		if(item instanceof RcEnergyItem energyItem && energyItem.getEnergyCapacity() > 0) {
			ItemStack stack = new ItemStack(item);
			energyItem.setStoredEnergy(stack, energyItem.getEnergyCapacity());
			entries.addAfter(item, stack);
		}
	}
}
