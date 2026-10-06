package io.github.thundervxlt.mixin;

import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OverworldBiomeBuilder.class)
abstract class SierraBadlandsBiomeMixin {
	private static final ResourceKey<Biome> SIERRA_BADLANDS = ResourceKey.create(
		Registries.BIOME,
		Identifier.fromNamespaceAndPath("charlottes-stuffs", "sierra_badlands")
	);

	@Inject(method = "pickBadlandsBiome", at = @At("RETURN"), cancellable = true)
	private void charlottesStuffs$useSierraBadlandsForBadlands(
		int humidityIndex,
		Climate.Parameter weirdness,
		CallbackInfoReturnable<ResourceKey<Biome>> cir
	) {
		if (Biomes.BADLANDS.equals(cir.getReturnValue())) {
			cir.setReturnValue(SIERRA_BADLANDS);
		}
	}
}
