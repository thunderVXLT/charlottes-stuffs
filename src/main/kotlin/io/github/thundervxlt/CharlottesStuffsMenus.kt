package io.github.thundervxlt

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.flag.FeatureFlagSet
import net.minecraft.world.inventory.MenuType

object CharlottesStuffsMenus {
    val COMPONENTIZER: MenuType<ComponentizerMenu> = Registry.register(
        BuiltInRegistries.MENU,
        CharlotteSStuffs.id("componentizer"),
        MenuType(
            MenuType.MenuSupplier { id, inv -> ComponentizerMenu(id, inv) },
            FeatureFlagSet.of()
        )
    )
    val ATOMIC: MenuType<AtomicMenu> = Registry.register(
        BuiltInRegistries.MENU,
        CharlotteSStuffs.id("atomic_block"),
        MenuType(
            MenuType.MenuSupplier { id, inv -> AtomicMenu(id, inv) },
            FeatureFlagSet.of()
        )
    )
    val FLASHLIGHT: MenuType<FlashlightMenu> = Registry.register(
        BuiltInRegistries.MENU,
        CharlotteSStuffs.id("flashlight"),
        MenuType(
            MenuType.MenuSupplier { id, inv -> FlashlightMenu(id, inv) },
            FeatureFlagSet.of()
        )
    )

    fun initialize() {}
}