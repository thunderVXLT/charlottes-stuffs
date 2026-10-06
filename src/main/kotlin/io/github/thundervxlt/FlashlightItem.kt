package io.github.thundervxlt

import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import net.minecraft.world.level.Level

class FlashlightItem(properties: Properties) : Item(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        val stack = player.getItemInHand(hand)
        val isOn = stack.has(CharlottesStuffsComponents.FLASHLIGHT_ON)

        if (!isOn && !hasBattery(stack)) {
            if (!level.isClientSide) {
                player.sendOverlayMessage(Component.translatable("message.charlottes-stuffs.flashlight.needs_battery"))
            }
            return InteractionResult.SUCCESS
        }

        if (isOn) {
            stack.remove(CharlottesStuffsComponents.FLASHLIGHT_ON)
        } else {
            stack.set(CharlottesStuffsComponents.FLASHLIGHT_ON, true)
        }

        if (!level.isClientSide) {
            level.playSound(
                null,
                player.blockPosition(),
                CharlottesStuffsSounds.FLASHLIGHT_CLICK,
                SoundSource.PLAYERS,
                0.6f,
                if (isOn) 0.8f else 1.2f
            )
            player.sendOverlayMessage(
                Component.literal(if (isOn) "Flashlight off" else "Flashlight on")
            )
        }
        return InteractionResult.SUCCESS
    }

    companion object {
        fun hasBattery(stack: ItemStack): Boolean {
            val battery = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyOne()
            return battery.item == CharlottesStuffsItems.LITHIUM_BATTERY
        }
    }
}