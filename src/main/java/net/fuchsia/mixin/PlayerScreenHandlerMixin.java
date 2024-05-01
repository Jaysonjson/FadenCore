package net.fuchsia.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.fuchsia.ClothSlot;
import net.fuchsia.IClothInventory;
import net.fuchsia.common.objects.item.Cloth;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends ScreenHandler{

    @Unique
    private static final Identifier[] EMPTY_CLOTH_SLOT_TEXTURES = new Identifier[]{FadenIdentifier.create("item/feet"), FadenIdentifier.create("item/leg"), FadenIdentifier.create("item/chest"), FadenIdentifier.create("item/head")};


    @Unique
    ClothSlot[] EQUIPMENT_SLOT_ORDER_F = new ClothSlot[]{ClothSlot.HEAD, ClothSlot.CHEST, ClothSlot.LEGS, ClothSlot.FEET};
    protected PlayerScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        for(int i = 0; i < 4; ++i) {
            final ClothSlot equipmentSlot = EQUIPMENT_SLOT_ORDER_F[i];
            addSlot(new Slot(inventory,PlayerInventory.OFF_HAND_SLOT + 1 + i, 8, 8 + i * 18) {
                public void setStack(ItemStack stack, ItemStack previousStack) {
                    super.setStack(stack, previousStack);
                }

                public int getMaxItemCount() {
                    return 1;
                }

                public boolean canInsert(ItemStack stack) {
                    if(stack.getItem() instanceof Cloth cloth) {
                        return cloth.getClothType() == equipmentSlot;
                    }
                    return false;
                }

                public boolean canTakeItems(PlayerEntity playerEntity) {
                    ItemStack itemStack = this.getStack();
                    return !itemStack.isEmpty() && !playerEntity.isCreative() && EnchantmentHelper.hasBindingCurse(itemStack) ? false : super.canTakeItems(playerEntity);
                }

                public Pair<Identifier, Identifier> getBackgroundSprite() {
                    return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EMPTY_CLOTH_SLOT_TEXTURES[3 - equipmentSlot.getEntitySlotId()]);
                }
            });
        }
    }


}
