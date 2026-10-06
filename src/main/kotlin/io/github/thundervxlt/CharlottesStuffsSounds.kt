package io.github.thundervxlt

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvent

object CharlottesStuffsSounds {
    val SEAL_AMBIENT: SoundEvent = register("entity.seal.ambient")
    val SEAL_HURT: SoundEvent = register("entity.seal.hurt")
    val SEAL_DEATH: SoundEvent = register("entity.seal.death")
    val FLASHLIGHT_CLICK: SoundEvent = register("item.flashlight.click")

    private fun register(name: String): SoundEvent {
        val id = CharlotteSStuffs.id(name)
        return Registry.register(
            BuiltInRegistries.SOUND_EVENT,
            id,
            SoundEvent.createVariableRangeEvent(id)
        )
    }

    fun init() {}
}