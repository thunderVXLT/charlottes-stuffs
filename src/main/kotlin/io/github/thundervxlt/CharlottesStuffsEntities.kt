package io.github.thundervxlt

import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.SpawnPlacementTypes
import net.minecraft.world.entity.SpawnPlacements
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.Heightmap

object CharlottesStuffsEntities {
    val SEAL_KEY: ResourceKey<EntityType<*>> =
        ResourceKey.create(Registries.ENTITY_TYPE, CharlotteSStuffs.id("seal"))

    val SEAL: EntityType<SealEntity> = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        SEAL_KEY,
        EntityType.Builder.of(
            { type, level -> SealEntity(type, level) },
            MobCategory.CREATURE
        )
            .sized(1.0f, 0.6f)
            .build(SEAL_KEY)
    )

    fun init() {
        FabricDefaultAttributeRegistry.register(SEAL, SealEntity.createAttributes())

        SpawnPlacements.register(
            SEAL,
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES
        ) { type, level, spawnReason, pos, random ->
            val below = level.getBlockState(pos.below())
            below.`is`(Blocks.SAND) || below.`is`(Blocks.GRAVEL) ||
                    below.`is`(Blocks.STONE) || below.`is`(Blocks.SNOW_BLOCK)
        }

        BiomeModifications.addSpawn(
            BiomeSelectors.includeByKey(Biomes.BEACH, Biomes.SNOWY_BEACH, Biomes.STONY_SHORE),
            MobCategory.CREATURE,
            SEAL,
            8, 2, 4
        )
    }
}