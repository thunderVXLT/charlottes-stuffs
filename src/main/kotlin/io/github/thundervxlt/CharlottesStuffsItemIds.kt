package io.github.thundervxlt

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item

object CharlottesStuffsItemIds {
    fun create(name: String): ResourceKey<Item> =
        ResourceKey.create(Registries.ITEM, CharlotteSStuffs.id(name))

    val PROTON: ResourceKey<Item> = create("proton")
    val NEUTRON: ResourceKey<Item> = create("neutron")
    val ELECTRON: ResourceKey<Item> = create("electron")
    val HYDROGEN: ResourceKey<Item> = create("hydrogen")
    val HELIUM: ResourceKey<Item> = create("helium")
    val LITHIUM: ResourceKey<Item> = create("lithium")
    val BALLOON: ResourceKey<Item> = create("balloon")
    val LITHIUM_BATTERY: ResourceKey<Item> = create("lithium_battery")
    val STEEL_SCRAP: ResourceKey<Item> = create("steel_scrap")
    val STEEL_INGOT: ResourceKey<Item> = create("steel_ingot")
    val SEAL_SPAWN_EGG = create("seal_spawn_egg")
}