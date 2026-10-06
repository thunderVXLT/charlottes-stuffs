package io.github.thundervxlt.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@ModifyArg(
		method = "lambda$static$1",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ExtraCodecs;intRange(II)Lcom/mojang/serialization/Codec;"),
		index = 1
	)
	private static int charlottesStuffs$allowLargerStackCounts(int max) {
		return max == 99 ? 256 : max;
	}
}
