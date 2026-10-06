package io.github.thundervxlt

import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.player.Inventory

class FlashlightScreen(
    menu: FlashlightMenu,
    inventory: Inventory,
    title: Component
) : AbstractContainerScreen<FlashlightMenu>(menu, inventory, title) {

    override fun extractBackground(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
        graphics.blit(
            RenderPipelines.GUI_TEXTURED, TEXTURE,
            leftPos, topPos, 0f, 0f, imageWidth, imageHeight, 256, 256
        )
    }

    companion object {
        private val TEXTURE: Identifier =
            CharlotteSStuffs.id("textures/gui/container/flashlight.png")
    }
}