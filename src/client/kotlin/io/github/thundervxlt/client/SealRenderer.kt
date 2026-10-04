package io.github.thundervxlt.client

import io.github.thundervxlt.CharlotteSStuffs
import io.github.thundervxlt.SealEntity
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer

class SealRenderer(context: EntityRendererProvider.Context) :
    MobRenderer<SealEntity, SealRenderState, SealModel>(
        context,
        SealModel(context.bakeLayer(SealModel.LAYER)),
        0.5f
    ) {

    override fun createRenderState() = SealRenderState()

    override fun extractRenderState(entity: SealEntity, state: SealRenderState, partialTick: Float) {
        super.extractRenderState(entity, state, partialTick)
        state.idleAnimationState.copyFrom(entity.idleAnimationState)
        state.walkAnimationState.copyFrom(entity.walkAnimationState)
        state.swimAnimationState.copyFrom(entity.swimAnimationState)
        state.swimAltAnimationState.copyFrom(entity.swimAltAnimationState)
    }

    override fun getTextureLocation(state: SealRenderState) =
        CharlotteSStuffs.id("textures/entity/seal/seal.png")
}