package io.github.thundervxlt.client.mixin;

import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapRenderStateExtractor.class)
public class LightmapRenderStateExtractorMixin {

	@Inject(method = "extract", at = @At("TAIL"))
	private void forceHardcoreDarkness(LightmapRenderState renderState, float partialTicks, CallbackInfo ci) {
		renderState.brightness = 0.0F;
	}
}
