package io.github.thundervxlt

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.ButtonBlock
import net.minecraft.world.level.block.DoorBlock
import net.minecraft.world.level.block.FenceBlock
import net.minecraft.world.level.block.FenceGateBlock
import net.minecraft.world.level.block.FlowerBlock
import net.minecraft.world.level.block.FlowerPotBlock
import net.minecraft.world.level.block.IceCrystalBlock
import net.minecraft.world.level.block.PressurePlateBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.SaplingBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.TrapDoorBlock
import net.minecraft.world.level.block.TintedParticleLeavesBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.block.state.properties.WoodType
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
    val PALO_VERDE_PLANKS = register(
        "palo_verde_planks",
        ::Block,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_GREEN)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0f, 3.0f)
            .sound(SoundType.WOOD)
            .ignitedByLava()
    )
    val PALO_VERDE_LOG = register(
        "palo_verde_log", ::RotatedPillarBlock,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_GREEN)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0f)
            .sound(SoundType.WOOD)
            .ignitedByLava()
    )
    val STRIPPED_PALO_VERDE_LOG = register("stripped_palo_verde_log", ::RotatedPillarBlock,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_GREEN)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0f)
            .sound(SoundType.WOOD)
            .ignitedByLava()
    )
    val PALO_VERDE_WOOD = register("palo_verde_wood", ::RotatedPillarBlock,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_GREEN)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0f)
            .sound(SoundType.WOOD)
            .ignitedByLava()
    )
    val STRIPPED_PALO_VERDE_WOOD = register("stripped_palo_verde_wood", ::RotatedPillarBlock,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_GREEN)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0f)
            .sound(SoundType.WOOD)
            .ignitedByLava()
    )
    val PALO_VERDE_LEAVES = register(
        "palo_verde_leaves",
        { settings -> TintedParticleLeavesBlock(0.0f, settings) },
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2f)
            .randomTicks()
            .sound(SoundType.GRASS)
            .noOcclusion()
            .ignitedByLava()
            .pushReaction(PushReaction.POPPED)
            .isSuffocating { _, _, _ -> false }
            .isRedstoneConductor { _, _, _ -> false }
    )
    val PALO_VERDE_SAPLING = register(
        "palo_verde_sapling",
        { settings -> SaplingBlock(CharlottesStuffsTreeGrowers.PALO_VERDE, settings) },
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .pushReaction(PushReaction.POPPED)
    )
    val PALO_VERDE_STAIRS = register(
        "palo_verde_stairs",
        { settings -> StairBlock(PALO_VERDE_PLANKS.defaultBlockState(), settings) },
        BlockBehaviour.Properties.ofFullCopy(PALO_VERDE_PLANKS)
    )
    val PALO_VERDE_SLAB = register(
        "palo_verde_slab",
        ::SlabBlock,
        BlockBehaviour.Properties.ofFullCopy(PALO_VERDE_PLANKS)
    )
    val PALO_VERDE_FENCE = register(
        "palo_verde_fence",
        ::FenceBlock,
        BlockBehaviour.Properties.ofFullCopy(PALO_VERDE_PLANKS)
    )
    val PALO_VERDE_FENCE_GATE = register(
        "palo_verde_fence_gate",
        { settings -> FenceGateBlock(WoodType.OAK, settings) },
        BlockBehaviour.Properties.ofFullCopy(PALO_VERDE_PLANKS)
    )
    val PALO_VERDE_PRESSURE_PLATE = register(
        "palo_verde_pressure_plate",
        { settings -> PressurePlateBlock(BlockSetType.OAK, settings) },
        BlockBehaviour.Properties.ofFullCopy(PALO_VERDE_PLANKS).noCollision()
    )
    val PALO_VERDE_BUTTON = register(
        "palo_verde_button",
        { settings -> ButtonBlock(BlockSetType.OAK, 30, settings) },
        BlockBehaviour.Properties.ofFullCopy(PALO_VERDE_PLANKS).noCollision()
    )
    val PALO_VERDE_TRAPDOOR = register(
        "palo_verde_trapdoor",
        { settings -> TrapDoorBlock(BlockSetType.OAK, settings) },
        BlockBehaviour.Properties.ofFullCopy(PALO_VERDE_PLANKS)
    )
    val PALO_VERDE_DOOR = register(
        "palo_verde_door",
        { settings -> DoorBlock(BlockSetType.OAK, settings) },
        BlockBehaviour.Properties.ofFullCopy(PALO_VERDE_PLANKS).noOcclusion()
    )
    val FIREWHEEL = register(
        "firewheel",
        { settings -> FlowerBlock(MobEffects.FIRE_RESISTANCE, 5.0f, settings) },
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .sound(SoundType.GRASS)
            .pushReaction(PushReaction.POPPED)
    )
    val POTTED_FIREWHEEL = register(
        "potted_firewheel",
        { settings -> FlowerPotBlock(FIREWHEEL, settings) },
        BlockBehaviour.Properties.of()
            .instabreak()
            .noOcclusion()
            .pushReaction(PushReaction.POPPED)
    )

    fun initialize() {}
}