package io.github.thundervxlt

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class ComponentizerBlock(properties: Properties) : Block(properties) {

    override fun useWithoutItem(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hitResult: BlockHitResult
    ): InteractionResult {
        if (!level.isClientSide) {
            player.openMenu(state.getMenuProvider(level, pos))
        }
        return InteractionResult.SUCCESS
    }

    override fun getMenuProvider(state: BlockState, level: Level, pos: BlockPos): MenuProvider? =
        SimpleMenuProvider(
            { id, inventory, _ -> ComponentizerMenu(id, inventory, ContainerLevelAccess.create(level, pos)) },
            Component.translatable("container.charlottes-stuffs.componentizer")
        )
}