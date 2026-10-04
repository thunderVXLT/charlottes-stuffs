package io.github.thundervxlt

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.LightLayer
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SpeleothemBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.SpeleothemThickness

class IcicleBlock(properties: BlockBehaviour.Properties) :
    SpeleothemBlock(Blocks.PACKED_ICE.defaultBlockState(), properties) {

    companion object {
        private const val MELT_LIGHT_LEVEL = 11
    }

    override fun getStalactiteLandingSound(): Int = 1045

    override fun getMaxGrowthLength(): Int = 5

    override fun growStalactiteOrStalagmiteIfPossible(
        stalactiteStartState: BlockState,
        level: ServerLevel,
        stalactiteStartPos: BlockPos,
        random: RandomSource
    ) {
        if (!canGrow(level, stalactiteStartPos)) return

        val tipPos = SpeleothemBlock.findTip(
            stalactiteStartState, level, stalactiteStartPos, getMaxGrowthLength(), false
        ) ?: return

        val tipState = level.getBlockState(tipPos)
        if (!SpeleothemBlock.isFreeHangingStalactite(tipState)) return

        val below = tipPos.below()
        if (!level.getBlockState(below).isAir) return

        level.setBlockAndUpdate(
            below,
            defaultBlockState()
                .setValue(SpeleothemBlock.TIP_DIRECTION, Direction.DOWN)
                .setValue(SpeleothemBlock.THICKNESS, SpeleothemThickness.TIP)
        )
    }

    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        super.randomTick(state, level, pos, random) // keeps the growth logic

        if (SpeleothemBlock.isStalactite(state) &&
            level.getBrightness(LightLayer.BLOCK, pos) > MELT_LIGHT_LEVEL
        ) {
            level.scheduleTick(pos, this, 20 + random.nextInt(40))
        }
    }
}