package io.github.thundervxlt

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

object AnvilCrushing {
    private const val STACK_SIZE = 64

    fun register() {
        ServerTickEvents.END_SERVER_TICK.register { server ->
            for (level in server.allLevels) {
                val anvils = level.allEntities
                    .filterIsInstance<FallingBlockEntity>()
                    .filter { it.blockState.`is`(BlockTags.ANVIL) }

                for (anvil in anvils) {
                    val zone = anvil.boundingBox.expandTowards(0.0, -1.0, 0.0)

                    for (itemEntity in level.getEntitiesOfClass(ItemEntity::class.java, zone)) {
                        val stack = itemEntity.item
                        if (stack.`is`(Items.DIORITE) && stack.count >= STACK_SIZE) {
                            stack.shrink(STACK_SIZE)
                            if (stack.isEmpty) itemEntity.discard()

                            val result = ItemEntity(
                                level,
                                itemEntity.x, itemEntity.y, itemEntity.z,
                                ItemStack(CharlottesStuffsBlocks.COMPRESSED_DIORITE)
                            )
                            level.addFreshEntity(result)
                        }
                    }
                }
            }
        }
    }
}

