package io.github.thundervxlt

import net.minecraft.core.component.DataComponents
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents

class FlashlightMenu(
    containerId: Int,
    playerInventory: Inventory,
    private val flashlight: ItemStack
) : AbstractContainerMenu(CharlottesStuffsMenus.FLASHLIGHT, containerId) {

    // Used by the client, which doesn't know which flashlight was opened
    constructor(containerId: Int, playerInventory: Inventory) :
            this(containerId, playerInventory, ItemStack.EMPTY)

    private val battery: SimpleContainer = object : SimpleContainer(1) {
        override fun setChanged() {
            super.setChanged()
            saveToFlashlight()
        }
    }

    init {
        if (!flashlight.isEmpty) {
            val stored = flashlight.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
            battery.setItem(0, stored.copyOne())
        }

        addSlot(BatterySlot(battery, 80, 35))

        for (row in 0 until 3) {
            for (col in 0 until 9) {
                addSlot(LockedSlot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18))
            }
        }
        for (col in 0 until 9) {
            addSlot(LockedSlot(playerInventory, col, 8 + col * 18, 142))
        }
    }

    private fun saveToFlashlight() {
        if (flashlight.isEmpty) return
        flashlight.set(
            DataComponents.CONTAINER,
            ItemContainerContents.fromItems(listOf(battery.getItem(0).copy()))
        )
        if (battery.getItem(0).isEmpty) {
            flashlight.remove(CharlottesStuffsComponents.FLASHLIGHT_ON)
        }
    }

    fun isForFlashlight(stack: ItemStack): Boolean = flashlight === stack

    fun damageBattery() {
        val installedBattery = battery.getItem(0)
        if (installedBattery.isEmpty) return

        val newDamage = installedBattery.damageValue + 1
        if (newDamage >= installedBattery.maxDamage) {
            battery.setItem(0, ItemStack.EMPTY)
        } else {
            installedBattery.setDamageValue(newDamage)
            battery.setChanged()
        }
        broadcastChanges()
    }

    private inner class BatterySlot(container: Container, x: Int, y: Int) : Slot(container, 0, x, y) {
        override fun mayPlace(stack: ItemStack): Boolean =
            stack.item == CharlottesStuffsItems.LITHIUM_BATTERY

        override fun getMaxStackSize(): Int = 1
    }

    /** A player inventory slot that can't pick up the flashlight this menu belongs to. */
    private inner class LockedSlot(container: Container, index: Int, x: Int, y: Int) :
        Slot(container, index, x, y) {
        override fun mayPickup(player: Player): Boolean = item !== flashlight
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        val slot = slots[index]
        if (!slot.hasItem()) return ItemStack.EMPTY

        val stack = slot.item
        val original = stack.copy()

        if (index == BATTERY) {
            if (!moveItemStackTo(stack, INVENTORY_START, HOTBAR_END, true)) return ItemStack.EMPTY
        } else {
            if (!moveItemStackTo(stack, BATTERY, BATTERY + 1, false)) {
                if (index < HOTBAR_START) {
                    if (!moveItemStackTo(stack, HOTBAR_START, HOTBAR_END, false)) return ItemStack.EMPTY
                } else if (!moveItemStackTo(stack, INVENTORY_START, HOTBAR_START, false)) {
                    return ItemStack.EMPTY
                }
            }
        }

        if (stack.isEmpty) slot.setByPlayer(ItemStack.EMPTY) else slot.setChanged()
        if (stack.count == original.count) return ItemStack.EMPTY

        slot.onTake(player, stack)
        return original
    }

    override fun stillValid(player: Player): Boolean =
        flashlight.isEmpty || player.mainHandItem === flashlight || player.offhandItem === flashlight

    companion object {
        private const val BATTERY = 0
        private const val INVENTORY_START = 1
        private const val HOTBAR_START = 28
        private const val HOTBAR_END = 37
    }
}