package io.github.thundervxlt.client

import io.github.thundervxlt.CharlotteSStuffs
import io.github.thundervxlt.client.SealAnimation
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import net.minecraft.world.entity.AnimationState

class SealRenderState : LivingEntityRenderState() {
    val idleAnimationState = AnimationState()
    val walkAnimationState = AnimationState()
    val swimAnimationState = AnimationState()
    val swimAltAnimationState = AnimationState()
}

class SealModel(root: ModelPart) : EntityModel<SealRenderState>(root) {
    private val idle = SealAnimation.idle.bake(root)
    private val walk = SealAnimation.walk.bake(root)
    private val swim = SealAnimation.swim.bake(root)
    private val swimAlt = SealAnimation.swimalt.bake(root)

    override fun setupAnim(state: SealRenderState) {
        super.setupAnim(state)
        idle.apply(state.idleAnimationState, state.ageInTicks)
        walk.apply(state.walkAnimationState, state.ageInTicks)
        swim.apply(state.swimAnimationState, state.ageInTicks)
        swimAlt.apply(state.swimAltAnimationState, state.ageInTicks)
    }

    companion object {
        val LAYER = ModelLayerLocation(CharlotteSStuffs.id("seal"), "main")

        fun createBodyLayer(): LayerDefinition {
            val mesh = MeshDefinition()
            val root = mesh.root

            val waist = root.addOrReplaceChild("waist",
                CubeListBuilder.create().texOffs(59, 59).addBox(-0.7917f, -1f, 13.25f, 1f, 1f, 1f),
                PartPose.offset(0.4583f, 24f, -6.25f))

            waist.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(66, 101).addBox(-13f, -9f, 0f, 13f, 9f, 18f),
                PartPose.offset(6.4583f, 0f, -1.75f))

            val leftFlipper = waist.addOrReplaceChild("left_flipper", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-5.5417f, -0.75f, 3.75f, 0f, 1.5708f, 0f))
            leftFlipper.addOrReplaceChild("cube_r1",
                CubeListBuilder.create()
                    .texOffs(12, 121).addBox(0f, 0f, 0f, 0f, 2f, 5f)
                    .texOffs(0, 109).addBox(-8f, -1f, 0f, 8f, 1f, 5f),
                PartPose.offsetAndRotation(-2.5f, 0.75f, -9f, 0f, 1.5708f, 0f))

            val rightFlipper = waist.addOrReplaceChild("right_flipper", CubeListBuilder.create(),
                PartPose.offsetAndRotation(5.4583f, 0.25f, 3.75f, 0f, -1.5708f, 0f))
            rightFlipper.addOrReplaceChild("cube_r2",
                CubeListBuilder.create()
                    .texOffs(12, 121).addBox(0f, 0f, 0f, 0f, 2f, 5f)
                    .texOffs(0, 109).addBox(-8f, -1f, 0f, 8f, 1f, 5f),
                PartPose.offsetAndRotation(-2.5f, -0.25f, -9f, 0f, 1.5708f, 0f))

            val head = waist.addOrReplaceChild("head",
                CubeListBuilder.create()
                    .texOffs(104, 0).addBox(-11.5f, -6f, 7f, 7f, 6f, 5f)
                    .texOffs(100, 23).addBox(-10.5f, -3f, 4f, 5f, 3f, 3f),
                PartPose.offset(7.9583f, 0f, -13.75f))

            val lwhisker = head.addOrReplaceChild("lwhisker", CubeListBuilder.create(),
                PartPose.offset(-4.5f, -1.5f, 5f))
            lwhisker.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(100, 18).addBox(-1f, -1.5f, 0f, 2f, 3f, 0f),
                PartPose.offsetAndRotation(0f, 0f, 0f, 0f, -0.1309f, 0f))

            val rwhisker = head.addOrReplaceChild("rwhisker", CubeListBuilder.create(),
                PartPose.offset(-11.5f, -1.5f, 5f))
            rwhisker.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(74, 29).addBox(-1f, -1.5f, 0f, 2f, 3f, 0f),
                PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0.1309f, 0f))

            val legs = waist.addOrReplaceChild("legs",
                CubeListBuilder.create().texOffs(0, 0).addBox(-20f, -6f, 22f, 8f, 6f, 12f),
                PartPose.offset(15.9583f, 0f, -5.75f))

            val lfoot = legs.addOrReplaceChild("lfoot", CubeListBuilder.create(),
                PartPose.offset(-15.5564f, -3.8756f, 40.1502f))
            lfoot.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(13, 18).addBox(-1f, -2f, 0f, 2f, 4f, 7f),
                PartPose.offsetAndRotation(1.2894f, 0.9914f, -3.3812f, 0f, -0.3491f, 0f))

            val rfoot = legs.addOrReplaceChild("rfoot", CubeListBuilder.create(),
                PartPose.offset(-13.5f, -2f, 33.5f))
            rfoot.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(13, 18).addBox(-1f, -2f, 0f, 2f, 4f, 7f),
                PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0.3491f, 0f))

            return LayerDefinition.create(mesh, 128, 128)
        }
    }
}