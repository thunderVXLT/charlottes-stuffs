package io.github.thundervxlt

import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.ResultContainer
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class ComponentizerMenu(
    containerId: Int,
    playerInventory: Inventory,
    private val access: ContainerLevelAccess
) : AbstractContainerMenu(CharlottesStuffsMenus.COMPONENTIZER, containerId) {

    // Used by the client, which doesn't know the block position
    constructor(containerId: Int, playerInventory: Inventory) :
            this(containerId, playerInventory, ContainerLevelAccess.NULL)

    private val inputs: SimpleContainer = object : SimpleContainer(2) {
        override fun setChanged() {
            super.setChanged()
            this@ComponentizerMenu.slotsChanged(this)
        }
    }
    private val result = ResultContainer()

    init {
        addSlot(Slot(inputs, 0, 27, 47))
        addSlot(Slot(inputs, 1, 76, 47))
        addSlot(ComponentizerResultSlot(result, 134, 47))

        for (row in 0 until 3) {
            for (col in 0 until 9) {
                addSlot(Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18))
            }
        }
        for (col in 0 until 9) {
            addSlot(Slot(playerInventory, col, 8 + col * 18, 142))
        }
    }

    private inner class ComponentizerResultSlot(container: Container, x: Int, y: Int) :
        Slot(container, 0, x, y) {
        override fun mayPlace(stack: ItemStack): Boolean = false
        override fun onTake(player: Player, stack: ItemStack) {
            this@ComponentizerMenu.craftTaken(player, stack)
        }
    }

    /** Called when the player takes the result: uses up one of each input. */
    private fun craftTaken(player: Player, stack: ItemStack) {
        stack.onCraftedBy(player, stack.count)
        inputs.removeItem(0, 1)
        inputs.removeItem(1, 1)
    }

    override fun slotsChanged(container: Container) {
        super.slotsChanged(container)
        if (container !== inputs) return
        val found = ComponentizerRecipes.find(inputs.getItem(0), inputs.getItem(1))
        result.setItem(0, found ?: ItemStack.EMPTY)
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
                if (!moveItemStackTo(stack, 0, 2, false)) {
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
        AbstractContainerMenu.stillValid(access, player, CharlottesStuffsBlocks.COMPONENTIZER)

    override fun removed(player: Player) {
        super.removed(player)
        access.execute { _, _ -> clearContainer(player, inputs) }
    }

    companion object {
        private const val RESULT = 2
        private const val INVENTORY_START = 3
        private const val HOTBAR_START = 30
        private const val HOTBAR_END = 39
    }
}