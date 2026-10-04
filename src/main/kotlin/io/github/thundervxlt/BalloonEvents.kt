package io.github.thundervxlt

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.world.entity.EquipmentSlot

object BalloonEvents {
    private const val DAMAGE_INTERVAL_TICKS = 1

    fun register() {
        ServerTickEvents.END_SERVER_TICK.register { server ->
            if (server.tickCount % DAMAGE_INTERVAL_TICKS == 0) {
                for (player in server.playerList.players) {
                    val stack = player.getItemBySlot(EquipmentSlot.HEAD)
                    if (stack.`is`(CharlottesStuffsItems.BALLOON)
                        && !player.onGround()
                        && !player.isInWater()
                        && !player.onClimbable()
                        && !player.isInLava()
                    ) {
                        stack.hurtAndBreak(1, player, EquipmentSlot.HEAD)
                    }
                }
            }
        }
    }
}