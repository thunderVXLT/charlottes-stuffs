package io.github.thundervxlt

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

object ComponentizerRecipes {
    class Recipe(val a: Item, val b: Item, val result: ItemStack)

    val RECIPES: List<Recipe> = listOf(
        Recipe(Items.GLOWSTONE_DUST, Items.BLAZE_POWDER, ItemStack(CharlottesStuffsItems.PROTON)),
        Recipe(Items.LAPIS_LAZULI, Items.GLOWSTONE_DUST, ItemStack(CharlottesStuffsItems.NEUTRON)),
        Recipe(Items.QUARTZ, Items.GUNPOWDER, ItemStack(CharlottesStuffsItems.ELECTRON))
    )

    /** Returns a fresh copy of the result, or null if no recipe matches. */
    fun find(first: ItemStack, second: ItemStack): ItemStack? {
        for (r in RECIPES) {
            val match = (first.item == r.a && second.item == r.b) ||
                    (first.item == r.b && second.item == r.a)
            if (match) return r.result.copy()
        }
        return null
    }
}