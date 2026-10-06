package io.github.thundervxlt

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.core.component.DataComponents
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LightBlock
import net.minecraft.world.phys.HitResult
import java.util.UUID

object FlashlightEvents {
    private const val RANGE = 16.0
    private const val SPACING = 2.0
    private const val LIGHT_LEVEL = 12
    private const val BATTERY_DRAIN_TICKS = 20 * 10

    private val activeLights = HashMap<UUID, Pair<ServerLevel, Set<BlockPos>>>()
    private val batteryUseTicks = HashMap<UUID, Int>()

    fun initialize() {
        ServerTickEvents.END_SERVER_TICK.register { server ->
            for (player in server.playerList.players) {
                update(player)
            }
        }

        ServerPlayConnectionEvents.DISCONNECT.register { handler, _ ->
            clearLights(handler.player.uuid)
            batteryUseTicks.remove(handler.player.uuid)
        }

        ServerLifecycleEvents.SERVER_STOPPING.register { _ ->
            for (uuid in activeLights.keys.toList()) {
                clearLights(uuid)
            }
            batteryUseTicks.clear()
        }
    }

    private fun activeFlashlight(player: ServerPlayer): ItemStack? =
        listOf(player.mainHandItem, player.offhandItem).firstOrNull {
            it.item == CharlottesStuffsItems.FLASHLIGHT &&
                    it.has(CharlottesStuffsComponents.FLASHLIGHT_ON) &&
                    FlashlightItem.hasBattery(it)
        }

    private fun drainBattery(player: ServerPlayer, flashlight: ItemStack) {
        if (player.hasInfiniteMaterials()) return

        val menu = player.containerMenu as? FlashlightMenu
        if (menu != null && menu.isForFlashlight(flashlight)) {
            menu.damageBattery()
            return
        }

        val contents = flashlight.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
        val battery = contents.copyOne()
        val newDamage = battery.damageValue + 1
        if (newDamage >= battery.maxDamage) {
            flashlight.set(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
            flashlight.remove(CharlottesStuffsComponents.FLASHLIGHT_ON)
        } else {
            battery.setDamageValue(newDamage)
            flashlight.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(listOf(battery)))
        }
    }

    private fun addIfFree(level: ServerLevel, pos: BlockPos, positions: MutableSet<BlockPos>) {
        val state = level.getBlockState(pos)
        if (state.isAir || state.block == Blocks.LIGHT) {
            positions.add(pos)
        }
    }

    private fun findLightPositions(player: ServerPlayer, level: ServerLevel): Set<BlockPos> {
        val start = player.eyePosition
        val direction = player.lookAngle
        val end = start.add(direction.scale(RANGE))
        val hit = level.clip(
            ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player)
        )
        val positions = LinkedHashSet<BlockPos>()
        val missed = hit.type == HitResult.Type.MISS
        val beamLength = if (missed) RANGE else hit.location.distanceTo(start)

        var distance = SPACING
        while (distance < beamLength) {
            addIfFree(level, BlockPos.containing(start.add(direction.scale(distance))), positions)
            distance += SPACING
        }
        if (missed) {
            addIfFree(level, BlockPos.containing(end), positions)
        } else {
            addIfFree(level, hit.blockPos.relative(hit.direction), positions)
        }
        return positions
    }

    private fun placeLight(level: ServerLevel, pos: BlockPos) {
        level.setBlock(
            pos,
            Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, LIGHT_LEVEL),
            3
        )
    }

    private fun removeLight(level: ServerLevel, pos: BlockPos) {
        if (level.getBlockState(pos).block == Blocks.LIGHT) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3)
        }
    }

    private fun clearLights(uuid: UUID) {
        val old = activeLights.remove(uuid) ?: return
        for (pos in old.second) removeLight(old.first, pos)
    }

    private fun update(player: ServerPlayer) {
        val level = player.level() as ServerLevel
        val flashlight = activeFlashlight(player)
        if (flashlight == null) {
            batteryUseTicks.remove(player.uuid)
        } else {
            val ticks = batteryUseTicks.getOrDefault(player.uuid, 0) + 1
            if (ticks >= BATTERY_DRAIN_TICKS) {
                drainBattery(player, flashlight)
                batteryUseTicks[player.uuid] = 0
            } else {
                batteryUseTicks[player.uuid] = ticks
            }
        }

        val target: Set<BlockPos> =
            if (activeFlashlight(player) != null) findLightPositions(player, level) else emptySet()
        val old = activeLights[player.uuid]

        if (old != null && old.first != level) {
            for (pos in old.second) removeLight(old.first, pos)
        }
        val oldPositions: Set<BlockPos> = if (old != null && old.first == level) old.second else emptySet()

        for (pos in oldPositions) {
            if (pos !in target) removeLight(level, pos)
        }
        for (pos in target) {
            if (pos !in oldPositions) placeLight(level, pos)
        }

        if (target.isEmpty()) {
            activeLights.remove(player.uuid)
        } else {
            activeLights[player.uuid] = Pair(level, target)
        }
    }
}