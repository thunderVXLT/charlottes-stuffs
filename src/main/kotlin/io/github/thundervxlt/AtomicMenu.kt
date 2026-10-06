package io.github.thundervxlt

import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.ResultContainer
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class AtomicMenu(
    containerId: Int,
    playerInventory: Inventory,
    private val access: ContainerLevelAccess
) : AbstractContainerMenu(CharlottesStuffsMenus.ATOMIC, containerId) {

    constructor(containerId: Int, playerInventory: Inventory) :
            this(containerId, playerInventory, ContainerLevelAccess.NULL)

    private val inputs: SimpleContainer = object : SimpleContainer(3) {
        override fun setChanged() {
            super.setChanged()
            this@AtomicMenu.slotsChanged(this)
        }
    }
    private val result = ResultContainer()

    init {
        addSlot(ParticleSlot(inputs, 0, 26, 47, CharlottesStuffsItems.PROTON))
        addSlot(ParticleSlot(inputs, 1, 51, 47, CharlottesStuffsItems.NEUTRON))
        addSlot(ParticleSlot(inputs, 2, 76, 47, CharlottesStuffsItems.ELECTRON))
        addSlot(AtomicResultSlot(result, 134, 47))

        for (row in 0 until 3) {
            for (col in 0 until 9) {
                addSlot(Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18))
            }
        }
        for (col in 0 until 9) {
            addSlot(Slot(playerInventory, col, 8 + col * 18, 142))
        }
    }

    private class ParticleSlot(container: Container, index: Int, x: Int, y: Int, private val particle: Item) :
        Slot(container, index, x, y) {
        override fun mayPlace(stack: ItemStack): Boolean = stack.`is`(particle)
    }

    private inner class AtomicResultSlot(container: Container, x: Int, y: Int) :
        Slot(container, 0, x, y) {
        override fun mayPlace(stack: ItemStack): Boolean = false
        override fun onTake(player: Player, stack: ItemStack) {
            this@AtomicMenu.craftTaken(player, stack)
        }
    }

    private fun craftTaken(player: Player, stack: ItemStack) {
        stack.onCraftedBy(player, stack.count)
        for (i in 0 until 3) inputs.removeItem(i, inputs.getItem(i).count)
    }

    override fun slotsChanged(container: Container) {
        super.slotsChanged(container)
        if (container !== inputs) return
        val recipe = AtomicRecipes.find(inputs.getItem(0), inputs.getItem(1), inputs.getItem(2))
        result.setItem(0, recipe?.result?.copy() ?: ItemStack.EMPTY)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        val slot = slots[index]
        if (!slot.hasItem()) return ItemStack.EMPTY

        val stack = slot.item
        val original = stack.copy()

        when {
            index == RESULT -> {
                if (!moveItemStackTo(stack, INVENTORY_START, HOTBAR_END, true)) return ItemStack.EMPTY
                slot.onQuickCraft(stack, original)
            }
            index >= INVENTORY_START -> {
                if (!moveItemStackTo(stack, 0, 3, false)) {
                    if (index < HOTBAR_START) {
                        if (!moveItemStackTo(stack, HOTBAR_START, HOTBAR_END, false)) return ItemStack.EMPTY
                    } else if (!moveItemStackTo(stack, INVENTORY_START, HOTBAR_START, false)) {
                        return ItemStack.EMPTY
                    }
                }
            }
            else -> {
                if (!moveItemStackTo(stack, INVENTORY_START, HOTBAR_END, false)) return ItemStack.EMPTY
            }
        }

        if (stack.isEmpty) slot.setByPlayer(ItemStack.EMPTY) else slot.setChanged()
        if (stack.count == original.count) return ItemStack.EMPTY

        slot.onTake(player, stack)
        return original
    }

    override fun canTakeItemForPickAll(carried: ItemStack, target: Slot): Boolean =
        target.container !== result && super.canTakeItemForPickAll(carried, target)

    override fun stillValid(player: Player): Boolean =
        AbstractContainerMenu.stillValid(access, player, CharlottesStuffsBlocks.ATOMIC_BLOCK)

    override fun removed(player: Player) {
        super.removed(player)
        access.execute { _, _ -> clearContainer(player, inputs) }
    }

    companion object {
        private const val RESULT = 3
        private const val INVENTORY_START = 4
        private const val HOTBAR_START = 31
        private const val HOTBAR_END = 40
    }
}