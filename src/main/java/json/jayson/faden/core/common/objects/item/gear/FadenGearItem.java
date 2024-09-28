package json.jayson.faden.core.common.objects.item.gear;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import net.minecraft.item.tooltip.TooltipData;
import org.jetbrains.annotations.Nullable;

import json.jayson.faden.core.common.init.FadenCoreDataComponents;
import json.jayson.faden.core.common.objects.item.FadenItem;
import json.jayson.faden.core.common.objects.item.ItemTier;
import json.jayson.faden.core.common.objects.tooltip.ItemToolTipEntryRenderer;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipComponent;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipData;
import json.jayson.faden.core.common.objects.tooltip.ToolTipEntry;
import json.jayson.faden.core.common.slot.GearSlot;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class FadenGearItem extends FadenItem implements Gear, ItemToolTipEntryRenderer {
    public FadenGearItem(Settings settings) {
        super(settings.maxCount(1));
    }


    @Override
    public Collection<ToolTipEntry> getToolTipEntries(FadenTooltipComponent component) {
        ArrayList<ToolTipEntry> entries = new ArrayList<>();
        ItemStack itemStack = component.data.itemStack;

        if(itemStack.contains(FadenCoreDataComponents.ITEM_TIER)) {
            ItemTier itemTier = ItemTier.valueOf(itemStack.get(FadenCoreDataComponents.ITEM_TIER));
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Identifier getTexture(FadenTooltipComponent component) {
                    return itemTier.getIcon();
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.literal(itemTier.name());
                }

                @Override
                public int getTextColor(FadenTooltipComponent component) {
                    return itemTier.getColor();
                }
            });
        }

        entries.add(ToolTipEntry.of(getGearType() == GearSlot.BRACELET ? Text.translatable("tooltip.faden.bracelet") : getGearType() == GearSlot.BELT ? Text.translatable("tooltip.faden.belt") : Text.translatable("tooltip.faden.necklace"), getGearType() == GearSlot.BRACELET ? FadenCoreIdentifier.create("textures/item/empty_bracelet_slot.png") : getGearType() == GearSlot.BELT ? FadenCoreIdentifier.create("textures/item/empty_belt_slot.png") : FadenCoreIdentifier.create("textures/item/empty_necklace_slot.png")));

        //ADD COINS
        entries.addAll(super.getToolTipEntries(component));

        if(itemStack.getOrDefault(FadenCoreDataComponents.FREE_WATER_MOVEMENT, false)) {
            entries.add(ToolTipEntry.of(Text.translatable("tooltip.faden.free_water_movement"), Items.WATER_BUCKET));
        }

        if(itemStack.contains(FadenCoreDataComponents.DAMAGE_INCREASE_VALUE)) {
            entries.add(ToolTipEntry.of(Text.literal(Text.translatable("tooltip.faden.damage_increase_value").getString().replaceAll("%s", String.format("%.02f", component.data.itemStack.getOrDefault(FadenCoreDataComponents.DAMAGE_INCREASE_VALUE, 0f), Items.IRON_SWORD)))));
        }

        if(itemStack.contains(FadenCoreDataComponents.DAMAGE_INCREASE_PERCENTAGE)) {
            entries.add(ToolTipEntry.of(Text.literal(Text.translatable("tooltip.faden.damage_increase_percentage").getString().replaceAll("%s", String.format("%.02f", component.data.itemStack.getOrDefault(FadenCoreDataComponents.DAMAGE_INCREASE_VALUE, 0f), Items.IRON_SWORD)))));
        }

        if(itemStack.contains(FadenCoreDataComponents.FALL_DAMAGE_DECREASE_PERCENTAGE)) {
            entries.add(ToolTipEntry.of(Text.literal(Text.translatable("tooltip.faden.fall_damage_decrease_percentage").getString().replaceAll("%s", String.format("%.02f", component.data.itemStack.getOrDefault(FadenCoreDataComponents.FALL_DAMAGE_DECREASE_PERCENTAGE, 0f), Items.DIAMOND_BOOTS)))));
        }

        if(itemStack.contains(FadenCoreDataComponents.FALL_DAMAGE_DECREASE_BLOCKS)) {
            entries.add(ToolTipEntry.of(Text.literal(Text.translatable("tooltip.faden.fall_damage_decrease_blocks").getString().replaceAll("%s", String.format("%.02f", component.data.itemStack.getOrDefault(FadenCoreDataComponents.FALL_DAMAGE_DECREASE_BLOCKS, 0f), Items.DIAMOND_BOOTS)))));
        }

        if(itemStack.contains(FadenCoreDataComponents.JUMP_INCREASE_VALUE)) {
            entries.add(ToolTipEntry.of(Text.literal(Text.translatable("tooltip.faden.jump_increase_value").getString().replaceAll("%s", String.format("%.02f", component.data.itemStack.getOrDefault(FadenCoreDataComponents.JUMP_INCREASE_VALUE, 0f) * 100f))), Items.RABBIT_FOOT));
        }

        if(itemStack.contains(FadenCoreDataComponents.JUMP_INCREASE_PERCENTAGE)) {
            entries.add(ToolTipEntry.of(Text.literal(Text.translatable("tooltip.faden.jump_increase_percentage").getString().replaceAll("%s", String.format("%.02f", component.data.itemStack.getOrDefault(FadenCoreDataComponents.JUMP_INCREASE_PERCENTAGE, 0f), Items.RABBIT_FOOT)))));
        }

        if(itemStack.contains(DataComponentTypes.MAX_DAMAGE)) {
            int maxDmg = itemStack.get(DataComponentTypes.MAX_DAMAGE);
            int dmg = maxDmg - itemStack.getOrDefault(DataComponentTypes.DAMAGE, 0);
            float perc = (float) dmg / (float) maxDmg * 100.0f;
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Item getItem(FadenTooltipComponent component) {
                    if(perc <= 33) {
                        return Items.CHIPPED_ANVIL;
                    } else if(perc <= 66) {
                        return Items.DAMAGED_ANVIL;
                    }
                    return Items.ANVIL;
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.literal(dmg + "/" + maxDmg);
                }
            });
        }
        return entries;
    }


    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return Optional.of(new FadenTooltipData(stack));
    }

    public float onLivingDamaged(PlayerEntity player, LivingEntity livingEntity, ItemStack itemStack, float damageAmount) {
        float dmg = 0f;
        boolean damageItem = false;
        if(itemStack.contains(FadenCoreDataComponents.DAMAGE_INCREASE_VALUE)) {
            dmg += itemStack.get(FadenCoreDataComponents.DAMAGE_INCREASE_VALUE);
            damageItem = true;
        } else if(itemStack.contains(FadenCoreDataComponents.DAMAGE_INCREASE_PERCENTAGE)) {
            dmg = damageAmount + (damageAmount / 100.0f * itemStack.get(FadenCoreDataComponents.DAMAGE_INCREASE_PERCENTAGE));
            damageItem = true;
        }
        if(damageItem) {
            itemStack.damage(1, player, EquipmentSlot.CHEST);
        }
        return dmg;
    }

    public int onLivingFallDamage(PlayerEntity player, float fallDistance, LivingEntity livingEntity, ItemStack itemStack, int damageAmount) {
        if(itemStack.contains(FadenCoreDataComponents.FALL_DAMAGE_DECREASE_BLOCKS)) {
            if(itemStack.get(FadenCoreDataComponents.FALL_DAMAGE_DECREASE_BLOCKS) >= fallDistance) {
                return 0;
            }
        }

        int dmg = damageAmount;
        boolean damageItem = false;
        if(itemStack.contains(FadenCoreDataComponents.FALL_DAMAGE_DECREASE_PERCENTAGE)) {
            dmg = (int) (damageAmount - (damageAmount / 100.0f * itemStack.get(FadenCoreDataComponents.FALL_DAMAGE_DECREASE_PERCENTAGE)));
            damageItem = true;
        }
        if(damageItem) {
            itemStack.setDamage(itemStack.getDamage() + 1);
            itemStack.damage(0, player, EquipmentSlot.CHEST);
        }
        return dmg;
    }

    /*
    * this only gets called on client, need to fix
    * */
    public float jumpVelocity(PlayerEntity player, ItemStack itemStack, float velocityAmount) {
        float strength = 0f;
        if(itemStack.contains(FadenCoreDataComponents.JUMP_INCREASE_VALUE)) {
            strength += itemStack.get(FadenCoreDataComponents.JUMP_INCREASE_VALUE);
            itemStack.damage(1, player, EquipmentSlot.CHEST);
        } else if(itemStack.contains(FadenCoreDataComponents.JUMP_INCREASE_PERCENTAGE)) {
            strength = velocityAmount + (velocityAmount / 100.0f * itemStack.get(FadenCoreDataComponents.JUMP_INCREASE_PERCENTAGE));
            itemStack.damage(1, player, EquipmentSlot.CHEST);
        }
        return strength;
    }
}
