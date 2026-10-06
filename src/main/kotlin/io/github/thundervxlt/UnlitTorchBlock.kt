package io.github.thundervxlt

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.TorchBlock
import net.minecraft.world.level.block.WallTorchBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState

class UnlitTorchBlock(particle: SimpleParticleType, properties: BlockBehaviour.Properties) : TorchBlock(particle, properties) {
    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {}
}

class UnlitWallTorchBlock(particle: SimpleParticleType, properties: BlockBehaviour.Properties) : WallTorchBlock(particle, properties) {
    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {}
}
