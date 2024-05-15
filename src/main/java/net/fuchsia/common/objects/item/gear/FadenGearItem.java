package net.fuchsia.common.objects.item.gear;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.common.objects.item.FadenItem;
import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.objects.item.ItemToolTipEntryRenderer;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.FadenTooltipData;
import net.fuchsia.common.objects.tooltip.ToolTipEntry;
import net.fuchsia.common.slot.GearSlot;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.item.TooltipData;
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

        if(itemStack.contains(FadenDataComponents.ITEM_TIER)) {
            ItemTier itemTier = ItemTier.valueOf(itemStack.get(FadenDataComponents.ITEM_TIER));
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

        entries.add(new ToolTipEntry() {
            @Override
            public Text getText(FadenTooltipComponent component) {
                return getGearType() == GearSlot.BRACELET ? Text.translatable("tooltip.faden.bracelet") : getGearType() == GearSlot.BELT ? Text.translatable("tooltip.faden.belt") : Text.translatable("tooltip.faden.necklace");
            }

            @Override
            public @NotNull Identifier getTexture(FadenTooltipComponent component) {
                return getGearType() == GearSlot.BRACELET ? FadenIdentifier.create("textures/item/empty_bracelet_slot.png") : getGearType() == GearSlot.BELT ? FadenIdentifier.create("textures/item/empty_belt_slot.png") : FadenIdentifier.create("textures/item/empty_necklace_slot.png");
            }
        });

        //ADD COINS
        entries.addAll(super.getToolTipEntries(component));

        if(itemStack.getOrDefault(FadenDataComponents.FREE_WATER_MOVEMENT, false)) {
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Item getItem(FadenTooltipComponent component) {
                    return Items.WATER_BUCKET;
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.translatable("tooltip.faden.free_water_movement");
                }
            });
        }

        if(itemStack.contains(FadenDataComponents.DAMAGE_INCREASE_VALUE)) {
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Item getItem(FadenTooltipComponent component) {
                    return Items.IRON_SWORD;
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.literal(Text.translatable("tooltip.faden.damage_increase_value").getString().replaceAll("%s", String.valueOf(component.data.itemStack.getOrDefault(FadenDataComponents.DAMAGE_INCREASE_VALUE, 0f))));
                }
            });
        }

        if(itemStack.contains(FadenDataComponents.DAMAGE_INCREASE_PERCENTAGE)) {
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Item getItem(FadenTooltipComponent component) {
                    return Items.IRON_SWORD;
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.literal(Text.translatable("tooltip.faden.damage_increase_percentage").getString().replaceAll("%s", String.valueOf(component.data.itemStack.getOrDefault(FadenDataComponents.DAMAGE_INCREASE_VALUE, 0f))));
                }
            });
        }

        if(itemStack.contains(FadenDataComponents.JUMP_INCREASE_VALUE)) {
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Item getItem(FadenTooltipComponent component) {
                    return Items.RABBIT_FOOT;
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.literal(Text.translatable("tooltip.faden.jump_increase_value").getString().replaceAll("%s", String.valueOf(component.data.itemStack.getOrDefault(FadenDataComponents.DAMAGE_INCREASE_VALUE, 0f))));
                }
            });
        }

        if(itemStack.contains(FadenDataComponents.JUMP_INCREASE_PERCENTAGE)) {
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Item getItem(FadenTooltipComponent component) {
                    return Items.RABBIT_FOOT;
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.literal(Text.translatable("tooltip.faden.jump_increase_percentage").getString().replaceAll("%s", String.valueOf(component.data.itemStack.getOrDefault(FadenDataComponents.DAMAGE_INCREASE_VALUE, 0f))));
                }
            });
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
        if(itemStack.contains(FadenDataComponents.DAMAGE_INCREASE_VALUE)) {
            dmg += itemStack.get(FadenDataComponents.DAMAGE_INCREASE_VALUE);
            damageItem = true;
        } else if(itemStack.contains(FadenDataComponents.DAMAGE_INCREASE_PERCENTAGE)) {
            dmg = damageAmount + (damageAmount / 100.0f * itemStack.get(FadenDataComponents.DAMAGE_INCREASE_PERCENTAGE));
            damageItem = true;
        }
        if(damageItem) {
            itemStack.damage(1, player, EquipmentSlot.CHEST);
        }
        return dmg;
    }

    public float jumpVelocity(PlayerEntity player, ItemStack itemStack, float velocityAmount) {
        float strength = 0f;
        boolean damageItem = false;
        if(itemStack.contains(FadenDataComponents.JUMP_INCREASE_VALUE)) {
            strength += itemStack.get(FadenDataComponents.JUMP_INCREASE_VALUE);
            damageItem = true;
        } else if(itemStack.contains(FadenDataComponents.JUMP_INCREASE_PERCENTAGE)) {
            strength = velocityAmount + (velocityAmount / 100.0f * itemStack.get(FadenDataComponents.JUMP_INCREASE_PERCENTAGE));
            damageItem = true;
        }
        if(damageItem) {
            itemStack.damage(1, player, EquipmentSlot.CHEST);
        }
        return strength;
    }
}
