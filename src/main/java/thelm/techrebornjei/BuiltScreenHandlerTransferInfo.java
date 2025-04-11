package thelm.techrebornjei;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import reborncore.common.screen.BuiltScreenHandler;

public record BuiltScreenHandlerTransferInfo<R>(String name, RecipeType<R> recipeType, IntList recipeSlots, IntList inventorySlots) implements IRecipeTransferInfo<BuiltScreenHandler, R> {

	public BuiltScreenHandlerTransferInfo(String name, RecipeType<R> recipeType, IntList recipeSlots, IntStream inventorySlots) {
		this(name, recipeType, recipeSlots, IntImmutableList.toList(inventorySlots));
	}

	public BuiltScreenHandlerTransferInfo(String name, RecipeType<R> recipeType, IntStream recipeSlots, IntStream inventorySlots) {
		this(name, recipeType, IntImmutableList.toList(recipeSlots), IntImmutableList.toList(inventorySlots));
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
		return screenHandler.getName().equals(name);
	}

	@Override
	public List<Slot> getRecipeSlots(BuiltScreenHandler screenHandler, R recipe) {
		List<Slot> slots = new ArrayList<>(recipeSlots.size());
		recipeSlots.forEach(i -> slots.add(screenHandler.getSlot(i)));
		return slots;
	}

	@Override
	public List<Slot> getInventorySlots(BuiltScreenHandler screenHandler, R recipe) {
		List<Slot> slots = new ArrayList<>(inventorySlots.size());
		inventorySlots.forEach(i -> slots.add(screenHandler.getSlot(i)));
		return slots;
	}
}
