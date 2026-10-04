package io.github.thundervxlt

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class LithiumBatteryItem(properties: Properties) : Item(properties) {

    override fun isBarVisible(stack: ItemStack): Boolean = true

    override fun getBarColor(stack: ItemStack): Int {
        val charge = 1f - stack.damageValue / stack.maxDamage.toFloat()
        return when {
            charge > 0.5f -> 0x55FF55   // green
            charge > 0.2f -> 0xFFAA00   // orange
            else -> 0xFF5555            // red
        }
    }
}