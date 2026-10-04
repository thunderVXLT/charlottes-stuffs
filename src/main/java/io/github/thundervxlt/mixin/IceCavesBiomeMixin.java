package io.github.thundervxlt.mixin;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverworldBiomeBuilder.class)
public abstract class IceCavesBiomeMixin {
	@Inject(method = "addUndergroundBiomes", at = @At("TAIL"))
	private void charlottesStuffs$addIceCaves(
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomes,
		CallbackInfo ci
	) {
		biomes.accept(
			Pair.of(
				Climate.parameters(
					Climate.Parameter.span(-1.0F, -0.45F),
					Climate.Parameter.span(-1.0F, 1.0F),
					Climate.Parameter.span(-1.0F, 1.0F),
					Climate.Parameter.span(-1.0F, 1.0F),
					Climate.Parameter.span(0.2F, 0.9F),
					Climate.Parameter.span(-1.0F, 1.0F),
					0.0F
				),
				ResourceKey.create(
					Registries.BIOME,
					Identifier.fromNamespaceAndPath("charlottes-stuffs", "ice_caves")
				)
			)
		);
	}
}
