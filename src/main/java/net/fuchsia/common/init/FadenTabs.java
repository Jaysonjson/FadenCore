package net.fuchsia.common.init;

import net.fuchsia.common.init.blocks.FadenBuildingBlocks;
import net.fuchsia.common.objects.item.gear.bracelet.BraceletItem;
import net.fuchsia.common.objects.item.cloth.Cloth;
import net.fuchsia.common.objects.item.cloth.ClothItem;
import net.fuchsia.common.objects.item.gear.Gear;
import net.fuchsia.common.objects.item.gear.necklace.NecklaceItem;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.fuchsia.datagen.holders.FadenDataItem;
import net.fuchsia.util.FadenIdentifier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class FadenTabs {

    public static final ItemGroup FADEN = Registry.register(Registries.ITEM_GROUP, FadenIdentifier.create("misc_tab"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.faden.misc")).icon(() -> new ItemStack(FadenItems.COPPER_COIN)).entries((displayContext, entries) -> {
                for (FadenDataItem item : FadenItems.ITEMS) {
                    if(item.item() instanceof Cloth || item.item() instanceof Gear) {
                        continue;
                    }
                    entries.add(item.item());
                }
            }).build());

    public static final ItemGroup FADEN_CLOTHES = Registry.register(Registries.ITEM_GROUP, FadenIdentifier.create("clothes_tab"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.faden.clothes")).icon(() -> new ItemStack(FadenItems.AMETHYST_COIN)).entries((displayContext, entries) -> {
                for (ClothItem cloth : FadenCloths.CLOTHS) {
                    entries.add(cloth);
                }
            }).build());

    public static final ItemGroup FADEN_GEAR = Registry.register(Registries.ITEM_GROUP, FadenIdentifier.create("gear_tab"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.faden.gear")).icon(() -> new ItemStack(FadenGear.WARRIORS_BRACELET)).entries((displayContext, entries) -> {
                for (BraceletItem bracelet : FadenGear.BRACELETS) {
                    entries.add(bracelet);
                }

                for (NecklaceItem necklace : FadenGear.NECKLACES) {
                    entries.add(necklace);
                }
            }).build());

    public static final ItemGroup FADEN_BUILDING = Registry.register(Registries.ITEM_GROUP, FadenIdentifier.create("building_tabd"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.faden.building")).icon(() -> new ItemStack(FadenBuildingBlocks.GRANITE_BRICKS)).entries((displayContext, entries) -> {
                for (BuildingBlockDataEntry buildingBlock : FadenBuildingBlocks.BUILDING_BLOCKS) {
                    entries.add(buildingBlock.block());
                }
            }).build());


    public static void register() {}

}
