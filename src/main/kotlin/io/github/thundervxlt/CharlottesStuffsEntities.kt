package io.github.thundervxlt

import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory

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
    }
}