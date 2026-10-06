package io.github.thundervxlt.mixin;

import io.github.thundervxlt.TorchLifecycle;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public abstract class TorchLifecycleMixin {
	@Inject(method = "onPlace", at = @At("HEAD"))
	private void charlottesStuffs$scheduleTorchBurnout(
		BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston, CallbackInfo ci
	) {
		if (level instanceof ServerLevel serverLevel && oldState.getBlock() != state.getBlock() && TorchLifecycle.isLitTorch(state.getBlock())) {
			serverLevel.scheduleTick(pos, state.getBlock(), TorchLifecycle.BURN_TIME_TICKS);
		}
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void charlottesStuffs$extinguishTorch(
		BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci
	) {
		BlockState unlitState = TorchLifecycle.unlitState(state);
		if (unlitState != null) {
			level.setBlockAndUpdate(pos, unlitState);
			ci.cancel();
		}
	}
}
