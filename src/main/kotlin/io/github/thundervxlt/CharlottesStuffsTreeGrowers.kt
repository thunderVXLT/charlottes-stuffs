import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.util.random.WeightedList
import net.minecraft.world.level.block.grower.TreeGrower
import net.minecraft.world.level.levelgen.feature.Feature

object CharlottesStuffsTreeGrowers {
    val PALO_VERDE_TREE: ResourceKey<Feature> = ResourceKey.create(
        Registries.FEATURE,
        Identifier.fromNamespaceAndPath("charlottes-stuffs", "palo_verde_tree")
    )

    val PALO_VERDE = TreeGrower(
        "palo_verde",
        WeightedList.of(PALO_VERDE_TREE),
        WeightedList.of<ResourceKey<Feature>>(),
        WeightedList.of<ResourceKey<Feature>>(),
        PALO_VERDE_TREE
    )
}