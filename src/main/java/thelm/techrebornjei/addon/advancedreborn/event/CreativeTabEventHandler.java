package thelm.techrebornjei.addon.advancedreborn.event;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.pitan76.advancedreborn.AdvancedReborn;
import net.pitan76.advancedreborn.Items;
import reborncore.common.powerSystem.RcEnergyItem;

public class CreativeTabEventHandler implements ItemGroupEvents.ModifyEntriesAll {

	public final CreativeModeTab tab = BuiltInRegistries.CREATIVE_MODE_TAB.get(AdvancedReborn.INSTANCE.id("item_group"));

	@Override
	public void modifyEntries(CreativeModeTab tab, FabricItemGroupEntries entries) {
		if(tab == this.tab) {
			addCharged(entries, Items.ADVANCED_BATTERY.get());
			addCharged(entries, Items.ADVANCED_BATTERY_2.get());
			addCharged(entries, Items.ADVANCED_BATTERY_3.get());
			addCharged(entries, Items.ADVANCED_BATTERY_4.get());
			addCharged(entries, Items.ADVANCED_BATTERY_5.get());
			entries.accept(Items.BATPACK_4.get());
			addCharged(entries, Items.BATPACK_4.get());
			entries.accept(Items.BATPACK_16.get());
			addCharged(entries, Items.BATPACK_16.get());
			entries.accept(Items.BATPACK_64.get());
			addCharged(entries, Items.BATPACK_64.get());
			entries.accept(Items.BATPACK_128.get());
			addCharged(entries, Items.BATPACK_128.get());
		}
	}

	public void addCharged(FabricItemGroupEntries entries, Item item) {
		ItemStack stack = new ItemStack(item);
		if(item instanceof RcEnergyItem energyItem && energyItem.getEnergyCapacity(stack) > 0) {
			energyItem.setStoredEnergy(stack, energyItem.getEnergyCapacity(stack));
			entries.addAfter(item, stack);
		}
	}
}
