package io.github.thundervxlt.mixin;

import io.github.thundervxlt.TorchLifecycle;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public abstract class FlintAndSteelItemMixin {
	@Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
	private void charlottesStuffs$relightTorch(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState litState = TorchLifecycle.litState(level.getBlockState(pos));
		if (litState == null) {
			return;
		}

		Player player = context.getPlayer();
		if (level.isClientSide()) {
			cir.setReturnValue(InteractionResult.SUCCESS);
			return;
		}

		level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
		level.setBlock(pos, litState, 11);
		level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		if (player instanceof ServerPlayer serverPlayer) {
			ItemStack tool = context.getItemInHand();
			tool.hurtAndBreak(1, serverPlayer, context.getHand().asEquipmentSlot());
		}
		cir.setReturnValue(InteractionResult.SUCCESS);
	}
}
