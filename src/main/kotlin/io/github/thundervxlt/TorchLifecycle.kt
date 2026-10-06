package io.github.thundervxlt

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.WallTorchBlock
import net.minecraft.world.level.block.state.BlockState

object TorchLifecycle {
    const val BURN_TIME_TICKS = 12_000

    @JvmStatic
    fun isLitTorch(block: Block): Boolean =
        block == Blocks.TORCH || block == Blocks.WALL_TORCH || block == Blocks.SOUL_TORCH || block == Blocks.SOUL_WALL_TORCH

    @JvmStatic
    fun unlitState(state: BlockState): BlockState? {
        val target = when (state.block) {
            Blocks.TORCH -> CharlottesStuffsBlocks.UNLIT_TORCH
            Blocks.WALL_TORCH -> CharlottesStuffsBlocks.UNLIT_WALL_TORCH
            Blocks.SOUL_TORCH -> CharlottesStuffsBlocks.UNLIT_SOUL_TORCH
            Blocks.SOUL_WALL_TORCH -> CharlottesStuffsBlocks.UNLIT_SOUL_WALL_TORCH
            else -> return null
        }
        return copyFacing(state, target.defaultBlockState())
    }

    @JvmStatic
    fun litState(state: BlockState): BlockState? {
        val target = when (state.block) {
            CharlottesStuffsBlocks.UNLIT_TORCH -> Blocks.TORCH
            CharlottesStuffsBlocks.UNLIT_WALL_TORCH -> Blocks.WALL_TORCH
            CharlottesStuffsBlocks.UNLIT_SOUL_TORCH -> Blocks.SOUL_TORCH
            CharlottesStuffsBlocks.UNLIT_SOUL_WALL_TORCH -> Blocks.SOUL_WALL_TORCH
            else -> return null
        }
        return copyFacing(state, target.defaultBlockState())
    }

    private fun copyFacing(from: BlockState, to: BlockState): BlockState =
        if (from.hasProperty(WallTorchBlock.FACING)) {
            to.setValue(WallTorchBlock.FACING, from.getValue(WallTorchBlock.FACING))
        } else {
            to
        }
}
