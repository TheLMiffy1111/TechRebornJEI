package thelm.techrebornjei.mixin;

import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.serialization.MapCodec;

import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RecipeManager;

@Mixin(RecipeManager.class)
public class RebornRecipeManagerMixin {

	@Inject(method = "newRecipeType(Lnet/minecraft/resources/ResourceLocation;Ljava/util/function/Function;Ljava/util/function/Function;)Lnet/minecraft/world/item/crafting/RecipeType;", at = @At("TAIL"))
	private static <R extends RebornRecipe> void onNewRecipeType(ResourceLocation name, Function<RecipeType<R>, MapCodec<R>> codec, Function<RecipeType<R>, StreamCodec<RegistryFriendlyByteBuf, R>> packetCodec, CallbackInfoReturnable<RecipeType<R>> ci) {
		BuiltInRegistries.RECIPE_SERIALIZER.getOptional(name).ifPresent(RecipeSynchronization::synchronizeRecipeSerializer);
	}
}
