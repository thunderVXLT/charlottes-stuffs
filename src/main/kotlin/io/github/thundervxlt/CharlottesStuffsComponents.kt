package io.github.thundervxlt

import com.mojang.serialization.Codec
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.codec.ByteBufCodecs

object CharlottesStuffsComponents {
    val FLASHLIGHT_ON: DataComponentType<Boolean> = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        CharlotteSStuffs.id("flashlight_on"),
        DataComponentType.builder<Boolean>()
            .persistent(Codec.BOOL)
            .networkSynchronized(ByteBufCodecs.BOOL)
            .build()
    )

    fun initialize() {}
}