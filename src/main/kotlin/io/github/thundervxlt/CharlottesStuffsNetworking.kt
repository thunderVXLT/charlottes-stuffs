package io.github.thundervxlt

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.chat.Component
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.inventory.MenuConstructor

object CharlottesStuffsNetworking {
    fun initialize() {
        PayloadTypeRegistry.serverboundPlay().register(OpenFlashlightPayload.TYPE, OpenFlashlightPayload.CODEC)

        ServerPlayNetworking.registerGlobalReceiver(OpenFlashlightPayload.TYPE) { _, context ->
            val player = context.player()
            val stack = listOf(player.mainHandItem, player.offhandItem)
                .firstOrNull { it.item == CharlottesStuffsItems.FLASHLIGHT }
                ?: return@registerGlobalReceiver

            player.openMenu(
                SimpleMenuProvider(
                    MenuConstructor { id, inv, _ -> FlashlightMenu(id, inv, stack) },
                    Component.translatable("item.charlottes-stuffs.flashlight")
                )
            )
        }
    }
}