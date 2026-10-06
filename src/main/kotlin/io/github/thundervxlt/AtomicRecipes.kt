package io.github.thundervxlt

import net.minecraft.world.item.ItemStack

object AtomicRecipes {
    class Recipe(val protons: Int, val minNeutrons: Int, val maxNeutrons: Int, val result: ItemStack)

    val RECIPES: List<Recipe> = listOf(
        Recipe(1, 0, 2, ItemStack(CharlottesStuffsItems.HYDROGEN)),   // H-1, H-2, H-3
        Recipe(2, 1, 2, ItemStack(CharlottesStuffsItems.HELIUM)),     // He-3, He-4
        Recipe(3, 3, 4, ItemStack(CharlottesStuffsItems.LITHIUM)),    // Li-6, Li-7
        Recipe(4, 5, 5, ItemStack(CharlottesStuffsItems.BERYLLIUM)),  // Be-9
        Recipe(5, 5, 6, ItemStack(CharlottesStuffsItems.BORON)),      // B-10, B-11
        Recipe(6, 6, 7, ItemStack(CharlottesStuffsItems.CARBON)),     // C-12, C-13
        Recipe(7, 7, 8, ItemStack(CharlottesStuffsItems.NITROGEN)),   // N-14, N-15
        Recipe(8, 8, 10, ItemStack(CharlottesStuffsItems.OXYGEN)),    // O-16, O-17, O-18
        Recipe(9, 10, 10, ItemStack(CharlottesStuffsItems.FLUORINE)), // F-19
        Recipe(10, 10, 12, ItemStack(CharlottesStuffsItems.NEON)),    // Ne-20, 21, 22
        Recipe(11, 12, 12, ItemStack(CharlottesStuffsItems.SODIUM)),  // Na-23
        Recipe(12, 12, 14, ItemStack(CharlottesStuffsItems.MAGNESIUM)), // Mg-24, 25, 26
        Recipe(13, 14, 14, ItemStack(CharlottesStuffsItems.ALUMINUM)), // Al-27
        Recipe(14, 14, 16, ItemStack(CharlottesStuffsItems.SILICON)), // Si-28, 29, 30
        Recipe(15, 16, 16, ItemStack(CharlottesStuffsItems.PHOSPHORUS)) // P-31
    )

    fun find(protons: ItemStack, neutrons: ItemStack, electrons: ItemStack): Recipe? {
        for (r in RECIPES) {
            if (protons.count == r.protons &&
                electrons.count == r.protons &&
                neutrons.count in r.minNeutrons..r.maxNeutrons
            ) return r
        }
        return null
    }
}