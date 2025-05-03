package thelm.techrebornjei.recipe.transfer;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.Range;

import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.screen.BuiltScreenHandler;
import thelm.techrebornjei.mixin.BuiltScreenHandlerAccessor;

public record BuiltScreenHandlerTransferInfo<R>(String name, RecipeType<R> recipeType, IntList recipeSlotOffsets) implements IRecipeTransferInfo<BuiltScreenHandler, R> {

	public BuiltScreenHandlerTransferInfo(String name, RecipeType<R> recipeType, IntStream recipeSlotOffsets) {
		this(name, recipeType, IntImmutableList.toList(recipeSlotOffsets));
	}

	@Override
	public Class<BuiltScreenHandler> getContainerClass() {
		return BuiltScreenHandler.class;
	}

	@Override
	public ResourceLocation getRecipeCategoryUid() {
		return recipeType.getUid();
	}

	@Override
	public Class<R> getRecipeClass() {
		return (Class<R>)recipeType.getRecipeClass();
	}

	@Override
	public RecipeType<R> getRecipeType() {
		return recipeType;
	}

	@Override
	public boolean canHandle(BuiltScreenHandler screenHandler, R recipe) {
		List<Range<Integer>> playerSlotRanges = ((BuiltScreenHandlerAccessor)screenHandler).trjei$playerSlotRanges();
		List<Range<Integer>> blockEntitySlotRanges = ((BuiltScreenHandlerAccessor)screenHandler).trjei$blockEntitySlotRanges();
		return screenHandler.getName().equals(name) && !playerSlotRanges.isEmpty() && !blockEntitySlotRanges.isEmpty();
	}

	@Override
	public List<Slot> getRecipeSlots(BuiltScreenHandler screenHandler, R recipe) {
		Range<Integer> blockEntitySlotRange = ((BuiltScreenHandlerAccessor)screenHandler).trjei$blockEntitySlotRanges().get(0);
		MachineBaseBlockEntity blockEntity = screenHandler.getBlockEntity();
		int baseOffset = blockEntitySlotRange.getMinimum();
		int upgradeOffset = blockEntity.canBeUpgraded() ? blockEntity.getUpgradeSlotCount() : 0;
		return recipeSlotOffsets.intStream().map(i -> i + baseOffset + upgradeOffset).mapToObj(screenHandler::getSlot).toList();
	}

	@Override
	public List<Slot> getInventorySlots(BuiltScreenHandler screenHandler, R recipe) {
		Range<Integer> playerSlotRange = ((BuiltScreenHandlerAccessor)screenHandler).trjei$playerSlotRanges().get(0);
		return IntStream.rangeClosed(playerSlotRange.getMinimum(), playerSlotRange.getMaximum()).mapToObj(screenHandler::getSlot).toList();
	}
}
