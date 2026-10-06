package io.github.thundervxlt

import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

object OpenFlashlightPayload : CustomPacketPayload {
    val TYPE: CustomPacketPayload.Type<OpenFlashlightPayload> =
        CustomPacketPayload.Type(CharlotteSStuffs.id("open_flashlight"))

    val CODEC: StreamCodec<RegistryFriendlyByteBuf, OpenFlashlightPayload> =
        StreamCodec.unit(OpenFlashlightPayload)

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE
}