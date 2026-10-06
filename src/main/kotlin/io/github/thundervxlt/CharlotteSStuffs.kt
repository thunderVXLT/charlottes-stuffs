package io.github.thundervxlt

import io.github.thundervxlt.CharlottesStuffsBlocks.PALO_VERDE_LOG
import io.github.thundervxlt.CharlottesStuffsBlocks.PALO_VERDE_WOOD
import io.github.thundervxlt.CharlottesStuffsBlocks.STRIPPED_PALO_VERDE_LOG
import io.github.thundervxlt.CharlottesStuffsBlocks.STRIPPED_PALO_VERDE_WOOD
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.BlockTransformerHelper
import net.minecraft.resources.Identifier
import org.slf4j.LoggerFactory

object CharlotteSStuffs : ModInitializer {
	const val MOD_ID: String = "charlottes-stuffs"

	private val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!")
		CharlottesStuffsItems.initialize()
		CharlottesStuffsBlocks.initialize()
		CharlottesStuffsEntities.init()
		CharlottesStuffsSounds.init()
		CharlottesStuffsMenus.initialize()
		CharlottesStuffsComponents.initialize()
		CharlottesStuffsNetworking.initialize()
		BalloonEvents.register()
		AnvilCrushing.register()
		FlashlightEvents.initialize()
		BlockTransformerHelper.registerStripping(PALO_VERDE_LOG, STRIPPED_PALO_VERDE_LOG)
		BlockTransformerHelper.registerStripping(PALO_VERDE_WOOD, STRIPPED_PALO_VERDE_WOOD)
	}

	fun id(path: String): Identifier
		= Identifier.fromNamespaceAndPath(MOD_ID, path)
}
