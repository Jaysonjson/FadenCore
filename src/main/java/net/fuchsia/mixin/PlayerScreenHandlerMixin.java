package net.fuchsia.mixin;

import com.mojang.datafixers.util.Pair;
import net.fuchsia.common.objects.item.gear.Gear;
import net.fuchsia.common.slot.ClothSlot;
import net.fuchsia.ExtraInventory;
import net.fuchsia.common.objects.item.cloth.Cloth;
import net.fuchsia.common.slot.GearSlot;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends ScreenHandler{

    @Unique
    private static final Identifier[] EMPTY_CLOTH_SLOT_TEXTURES = new Identifier[]{FadenIdentifier.create("item/feet"), FadenIdentifier.create("item/leg"), FadenIdentifier.create("item/chest"), FadenIdentifier.create("item/head")};
    @Unique
    private static final Identifier[] EMPTY_GEAR_SLOT_TEXTURES = new Identifier[]{FadenIdentifier.create("item/empty_bracelet_slot"), FadenIdentifier.create("item/empty_bracelet_slot"), FadenIdentifier.create("item/empty_belt_slot"), FadenIdentifier.create("item/empty_necklace_slot")};


    @Unique
    ClothSlot[] CLOTH_SLOT_ORDER = new ClothSlot[]{ClothSlot.HEAD, ClothSlot.CHEST, ClothSlot.LEGS, ClothSlot.FEET};
    @Unique
    GearSlot[] GEAR_SLOT_ORDER = new GearSlot[]{GearSlot.BRACELET, GearSlot.BRACELET, GearSlot.BELT, GearSlot.NECKLACE};

    protected PlayerScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        for(int i = 0; i < 4; ++i) {
            final ClothSlot equipmentSlot = CLOTH_SLOT_ORDER[i];
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

        for(int i = 0; i < ExtraInventory.GEAR_END - ExtraInventory.GEAR_START; ++i) {
            final GearSlot equipmentSlot = GEAR_SLOT_ORDER[i];
            addSlot(new Slot(inventory,PlayerInventory.OFF_HAND_SLOT + 5 + i, 8, 8 + i * 18) {
                public void setStack(ItemStack stack, ItemStack previousStack) {
                    super.setStack(stack, previousStack);
                }

                public int getMaxItemCount() {
                    return 1;
                }

                public boolean canInsert(ItemStack stack) {
                    if(stack.getItem() instanceof Gear gear) {
                        return gear.getGearType() == equipmentSlot;
                    }
                    return false;
                }

                public boolean canTakeItems(PlayerEntity playerEntity) {
                    ItemStack itemStack = this.getStack();
                    return !itemStack.isEmpty() && !playerEntity.isCreative() && EnchantmentHelper.hasBindingCurse(itemStack) ? false : super.canTakeItems(playerEntity);
                }

                public Pair<Identifier, Identifier> getBackgroundSprite() {
                    return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EMPTY_GEAR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
                }
            });
        }
    }


    @Inject(method = "quickMove", at = @At("HEAD"), cancellable = true)
    public void quickMove(PlayerEntity player, int slot, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if(itemStack.getItem() instanceof Cloth cloth) {
                ClothSlot clothSlot = cloth.getClothType();
                if(!(this.slots.get(ExtraInventory.CLOTH_END - 1 - clothSlot.getEntitySlotId())).hasStack()) {
                    int i = ExtraInventory.CLOTH_END - 1 - clothSlot.getEntitySlotId();
                    if (!this.insertItem(itemStack2, i, i + 1, false)) {
                        cir.setReturnValue(ItemStack.EMPTY);
                    }
                }
            }
        }
    }



}
