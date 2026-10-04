package io.github.thundervxlt

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.IceCrystalBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction

object CharlottesStuffsBlocks {
    fun register(
        name: String,
        blockFactory: (BlockBehaviour.Properties) -> Block,
        settings: BlockBehaviour.Properties
    ): Block {
        val blockKey = ResourceKey.create(Registries.BLOCK, CharlotteSStuffs.id(name))
        val block = blockFactory(settings.setId(blockKey))

        // Blocks need an item too, so they can sit in your inventory.
        val itemKey = ResourceKey.create(Registries.ITEM, CharlotteSStuffs.id(name))
        val blockItem = BlockItem(block, Item.Properties().setId(itemKey).useBlockDescriptionPrefix())
        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem)

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block)
    }

    val COMPRESSED_DIORITE: Block = register(
        "compressed_diorite",
        ::Block,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIORITE)
    )
    val REFINED_DIORITE: Block = register(
        "refined_diorite",
        ::Block,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DIORITE)
    )
    val COMPONENTIZER: Block = register(
        "componentizer",
        ::ComponentizerBlock,
        BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    )
    val ICICLE: Block = register(
        "icicle", ::IcicleBlock,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.ICE)
            .instrument(NoteBlockInstrument.CHIME)
            .sound(SoundType.GLASS)
            .strength(0.5f)
            .noOcclusion()
            .randomTicks()
            .dynamicShape()
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.POPPED)
    )
    val ICE_CRYSTAL: Block = register(
        "ice_crystal",
        { props -> IceCrystalBlock(7.0f, 3.0f, props) },
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.ICE)
            .instrument(NoteBlockInstrument.CHIME)
            .sound(SoundType.GLASS)
            .strength(0.3f)
            .noOcclusion()
            .lightLevel { 4 }
            .pushReaction(PushReaction.POPPED)
    )

    fun initialize() {}
}