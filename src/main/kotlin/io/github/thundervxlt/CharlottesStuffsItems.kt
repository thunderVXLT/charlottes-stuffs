package io.github.thundervxlt

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.component.ItemAttributeModifiers
import net.minecraft.world.item.equipment.Equippable

object CharlottesStuffsItems {
    fun register(
        itemKey: ResourceKey<Item>,
        itemFactory: (Item.Properties) -> Item,
        settings: Item.Properties
    ): Item {
        val item = itemFactory(settings.setId(itemKey))
        return Registry.register(BuiltInRegistries.ITEM, itemKey, item)
    }

    val PROTON: Item = register(CharlottesStuffsItemIds.PROTON, ::Item, Item.Properties().stacksTo(256))
    val NEUTRON: Item = register(CharlottesStuffsItemIds.NEUTRON, ::Item, Item.Properties().stacksTo(256))
    val ELECTRON: Item = register(CharlottesStuffsItemIds.ELECTRON, ::Item, Item.Properties().stacksTo(256))
    val HYDROGEN: Item = register(CharlottesStuffsItemIds.HYDROGEN, ::Item, Item.Properties().stacksTo(8))
    val HELIUM: Item = register(CharlottesStuffsItemIds.HELIUM, ::Item, Item.Properties().stacksTo(8))
    val LITHIUM: Item = register(CharlottesStuffsItemIds.LITHIUM, ::Item, Item.Properties().stacksTo(8))
    val BERYLLIUM: Item = register(CharlottesStuffsItemIds.BERYLLIUM, ::Item, Item.Properties().stacksTo(8))
    val BORON: Item = register(CharlottesStuffsItemIds.BORON, ::Item, Item.Properties().stacksTo(8))
    val CARBON: Item = register(CharlottesStuffsItemIds.CARBON, ::Item, Item.Properties().stacksTo(8))
    val NITROGEN: Item = register(CharlottesStuffsItemIds.NITROGEN, ::Item, Item.Properties().stacksTo(8))
    val OXYGEN: Item = register(CharlottesStuffsItemIds.OXYGEN, ::Item, Item.Properties().stacksTo(8))
    val FLUORINE: Item = register(CharlottesStuffsItemIds.FLUORINE, ::Item, Item.Properties().stacksTo(8))
    val NEON: Item = register(CharlottesStuffsItemIds.NEON, ::Item, Item.Properties().stacksTo(8))
    val SODIUM: Item = register(CharlottesStuffsItemIds.SODIUM, ::Item, Item.Properties().stacksTo(8))
    val MAGNESIUM: Item = register(CharlottesStuffsItemIds.MAGNESIUM, ::Item, Item.Properties().stacksTo(8))
    val ALUMINUM: Item = register(CharlottesStuffsItemIds.ALUMINUM, ::Item, Item.Properties().stacksTo(8))
    val SILICON: Item = register(CharlottesStuffsItemIds.SILICON, ::Item, Item.Properties().stacksTo(8))
    val PHOSPHORUS: Item = register(CharlottesStuffsItemIds.PHOSPHORUS, ::Item, Item.Properties().stacksTo(8))
    val BALLOON: Item = register(
        CharlottesStuffsItemIds.BALLOON,
        ::Item,
        Item.Properties()
            .durability(256)
            .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).build())
            .attributes(
                ItemAttributeModifiers.builder()
                    .add(
                        Attributes.GRAVITY,
                        AttributeModifier(
                            CharlotteSStuffs.id("balloon_gravity"),
                            -0.04,
                            AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.HEAD
                    )
                    .add(
                        Attributes.FALL_DAMAGE_MULTIPLIER,
                        AttributeModifier(CharlotteSStuffs.id("balloon_fall_damage"), -1.0, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.HEAD
                    )
                    .build()
            )
    )
    val LITHIUM_BATTERY: Item = register(
        CharlottesStuffsItemIds.LITHIUM_BATTERY,
        ::LithiumBatteryItem,
        Item.Properties()
            .durability(100)
    )
    val STEEL_SCRAP: Item = register(CharlottesStuffsItemIds.STEEL_SCRAP, ::Item, Item.Properties())
    val STEEL_INGOT: Item = register(CharlottesStuffsItemIds.STEEL_INGOT, ::Item, Item.Properties())
    val SEAL_SPAWN_EGG = register(
        CharlottesStuffsItemIds.SEAL_SPAWN_EGG,
        ::SpawnEggItem,
        Item.Properties().spawnEgg(CharlottesStuffsEntities.SEAL)
    )

    val CREATIVE_TAB_KEY: ResourceKey<CreativeModeTab> = ResourceKey.create(
        BuiltInRegistries.CREATIVE_MODE_TAB.key(),
        CharlotteSStuffs.id("creative_tab")
    )

    val CREATIVE_TAB: CreativeModeTab = FabricCreativeModeTab.builder()
        .icon { ItemStack(PROTON) }
        .title(Component.translatable("creativeTab.charlottes-stuffs"))
        .displayItems { _, output ->
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_LOG)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_WOOD)
            output.accept(CharlottesStuffsBlocks.STRIPPED_PALO_VERDE_LOG)
            output.accept(CharlottesStuffsBlocks.STRIPPED_PALO_VERDE_WOOD)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_PLANKS)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_STAIRS)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_SLAB)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_FENCE)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_FENCE_GATE)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_PRESSURE_PLATE)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_BUTTON)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_TRAPDOOR)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_DOOR)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_LEAVES)
            output.accept(CharlottesStuffsBlocks.PALO_VERDE_SAPLING)
            output.accept(CharlottesStuffsBlocks.COMPRESSED_DIORITE)
            output.accept(CharlottesStuffsBlocks.REFINED_DIORITE)
            output.accept(CharlottesStuffsBlocks.COMPONENTIZER)
            output.accept(CharlottesStuffsBlocks.ICICLE)
            output.accept(CharlottesStuffsBlocks.ICE_CRYSTAL)
            output.accept(STEEL_SCRAP)
            output.accept(STEEL_INGOT)
            output.accept(PROTON)
            output.accept(NEUTRON)
            output.accept(ELECTRON)
            output.accept(HYDROGEN)
            output.accept(HELIUM)
            output.accept(LITHIUM)
            output.accept(BERYLLIUM)
            output.accept(BORON)
            output.accept(CARBON)
            output.accept(NITROGEN)
            output.accept(OXYGEN)
            output.accept(FLUORINE)
            output.accept(NEON)
            output.accept(SODIUM)
            output.accept(MAGNESIUM)
            output.accept(ALUMINUM)
            output.accept(SILICON)
            output.accept(PHOSPHORUS)
            output.accept(BALLOON)
            output.accept(LITHIUM_BATTERY)
            output.accept(SEAL_SPAWN_EGG)
        }
        .build()

    fun initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CREATIVE_TAB_KEY, CREATIVE_TAB)
    }
}