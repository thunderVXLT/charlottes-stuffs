package io.github.thundervxlt.client

import io.github.thundervxlt.AtomicScreen
import io.github.thundervxlt.CharlottesStuffsEntities
import io.github.thundervxlt.CharlottesStuffsMenus
import io.github.thundervxlt.ComponentizerScreen
import io.github.thundervxlt.FlashlightScreen
import io.github.thundervxlt.CharlotteSStuffs
import io.github.thundervxlt.OpenFlashlightPayload
import io.github.thundervxlt.SealEntity
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.renderer.entity.EntityRenderers

object CharlotteSStuffsClient : ClientModInitializer {
	private lateinit var openFlashlightKey: KeyMapping

	override fun onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(SealModel.LAYER, SealModel::createBodyLayer)
		EntityRenderers.register<SealEntity>(CharlottesStuffsEntities.SEAL) { context -> SealRenderer(context) }
		MenuScreens.register(CharlottesStuffsMenus.COMPONENTIZER, ::ComponentizerScreen)
		MenuScreens.register(CharlottesStuffsMenus.ATOMIC, ::AtomicScreen)
		MenuScreens.register(CharlottesStuffsMenus.FLASHLIGHT, ::FlashlightScreen)

		val category = KeyMapping.Category.register(CharlotteSStuffs.id("general"))
		openFlashlightKey = KeyMappingHelper.registerKeyMapping(
			KeyMapping("key.charlottes-stuffs.open_flashlight", InputConstants.KEY_G, category)
		)
		ClientTickEvents.END_CLIENT_TICK.register { client ->
			while (openFlashlightKey.consumeClick()) {
				if (client.player != null) {
					ClientPlayNetworking.send(OpenFlashlightPayload)
				}
			}
		}
	}
}