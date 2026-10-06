package io.github.thundervxlt.client

import io.github.thundervxlt.AtomicScreen
import io.github.thundervxlt.CharlottesStuffsEntities
import io.github.thundervxlt.CharlottesStuffsMenus
import io.github.thundervxlt.ComponentizerScreen
import io.github.thundervxlt.SealEntity
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.renderer.entity.EntityRenderers

object CharlotteSStuffsClient : ClientModInitializer {
	override fun onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(SealModel.LAYER, SealModel::createBodyLayer)
		EntityRenderers.register<SealEntity>(CharlottesStuffsEntities.SEAL) { context -> SealRenderer(context) }
		MenuScreens.register(CharlottesStuffsMenus.COMPONENTIZER, ::ComponentizerScreen)
		MenuScreens.register(CharlottesStuffsMenus.ATOMIC, ::AtomicScreen)
	}
}