package io.github.thundervxlt

import net.minecraft.sounds.SoundEvent
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.AnimationState
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MoverType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl
import net.minecraft.world.entity.ai.goal.BreathAirGoal
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.PanicGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.PathType
import net.minecraft.world.phys.Vec3

class SealEntity(type: EntityType<out SealEntity>, level: Level) : PathfinderMob(type, level) {
    init {
        moveControl = SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, true)
        lookControl = SmoothSwimmingLookControl(this, 10)
        setPathfindingMalus(PathType.WATER, 0.0f)
    }
    val idleAnimationState = AnimationState()
    val walkAnimationState = AnimationState()
    val swimAnimationState = AnimationState()
    val swimAltAnimationState = AnimationState()

    private var currentAnimation: AnimationState? = null
    private var wasInWaterLastTick = false
    private var useAltSwim = false

    override fun tick() {
        super.tick()
        if (level().isClientSide) updateAnimations()
    }

    private fun updateAnimations() {
        val inWater = isInWater

        val target = when {
            inWater -> {
                if (!wasInWaterLastTick) useAltSwim = random.nextFloat() < 0.25f
                if (useAltSwim) swimAltAnimationState else swimAnimationState
            }
            deltaMovement.horizontalDistanceSqr() > 1.0E-4 -> walkAnimationState
            else -> idleAnimationState
        }

        if (target !== currentAnimation) {
            currentAnimation?.stop()
            target.start(tickCount)
            currentAnimation = target
        }
        wasInWaterLastTick = inWater
    }

    override fun registerGoals() {
        goalSelector.addGoal(0, BreathAirGoal(this))
        goalSelector.addGoal(1, PanicGoal(this, 1.25))
        goalSelector.addGoal(4, RandomSwimmingGoal(this, 1.0, 10))
        goalSelector.addGoal(5, WaterAvoidingRandomStrollGoal(this, 1.0))
        goalSelector.addGoal(6, LookAtPlayerGoal(this, Player::class.java, 6.0f))
        goalSelector.addGoal(7, RandomLookAroundGoal(this))
    }

    override fun createNavigation(level: Level): PathNavigation =
        AmphibiousPathNavigation(this, level)

    override fun isPushedByFluid(): Boolean = false

    override fun travel(input: Vec3) {
        if (isLocalInstanceAuthoritative && isInWater) {
            moveRelative(speed, input)
            move(MoverType.SELF, deltaMovement)
            deltaMovement = deltaMovement.scale(0.9)
            if (target == null) {
                deltaMovement = deltaMovement.add(0.0, -0.005, 0.0)
            }
        } else {
            super.travel(input)
        }
    }

    companion object {
        fun createAttributes(): AttributeSupplier.Builder =
            Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0)
                .add(Attributes.MOVEMENT_SPEED, 2.0)
                .add(Attributes.STEP_HEIGHT, 1.0)
    }

    override fun getAmbientSound(): SoundEvent? = CharlottesStuffsSounds.SEAL_AMBIENT
    override fun getHurtSound(source: DamageSource): SoundEvent? = CharlottesStuffsSounds.SEAL_HURT
    override fun getDeathSound(): SoundEvent? = CharlottesStuffsSounds.SEAL_DEATH
}