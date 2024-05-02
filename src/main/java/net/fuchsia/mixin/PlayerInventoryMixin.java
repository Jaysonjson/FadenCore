package net.fuchsia.mixin;

import com.google.common.collect.ImmutableList;
import net.fuchsia.ExtraInventory;
import net.fuchsia.common.slot.ClothSlot;
import net.fuchsia.IClothInventory;
import net.fuchsia.common.objects.item.Cloth;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements IClothInventory {

    @Shadow @Final public PlayerEntity player;
    @Mutable
    @Shadow @Final private List<DefaultedList<ItemStack>> combinedInventory;
    @Shadow @Final public DefaultedList<ItemStack> main;
    @Shadow @Final public DefaultedList<ItemStack> armor;
    @Shadow @Final public DefaultedList<ItemStack> offHand;

    public DefaultedList<ItemStack> clothes;
    public DefaultedList<ItemStack> gear;


    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(PlayerEntity player, CallbackInfo ci) {
        clothes = DefaultedList.ofSize(ExtraInventory.CLOTH_END - ExtraInventory.CLOTH_START, ItemStack.EMPTY);
        gear = DefaultedList.ofSize(ExtraInventory.GEAR_END - ExtraInventory.GEAR_START, ItemStack.EMPTY);
        combinedInventory = ImmutableList.of(main, armor, offHand, clothes, gear);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"), cancellable = true)
    public void writeNbt(NbtList nbtList, CallbackInfoReturnable<NbtList> cir) {
        NbtCompound nbtCompound;
        for(int i = 0; i < clothes.size(); ++i) {
            if (!(clothes.get(i)).isEmpty()) {
                nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte) (i + 200));
                nbtList.add((clothes.get(i)).encode(player.getRegistryManager(), nbtCompound));
            }
        }
        for(int i = 0; i < gear.size(); ++i) {
            if (!(gear.get(i)).isEmpty()) {
                nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte) (i + 250));
                nbtList.add((gear.get(i)).encode(player.getRegistryManager(), nbtCompound));
            }
        }
        cir.setReturnValue(nbtList);
    }


    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readNbt(NbtList nbtList, CallbackInfo ci) {
        this.clothes.clear();
        this.gear.clear();
        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.fromNbt(this.player.getRegistryManager(), nbtCompound).orElse(ItemStack.EMPTY);
            if(j >= 200 && j < this.clothes.size() + 200) {
                this.clothes.set(j - 200, itemStack);
            }

            if(j >= 250 && j < this.gear.size() + 250) {
                this.gear.set(j - 250, itemStack);
            }
        }
    }

    @Override
    public ItemStack getClothOrArmor(EquipmentSlot slot, ClothSlot clothSlot) {
        ItemStack stack = armor.get(slot.getEntitySlotId());
        if(stack.getItem() instanceof Cloth) {
           return stack;
        }  else {
            return clothes.get(clothSlot.getEntitySlotId());
        }
    }

    @Override
    public DefaultedList<ItemStack> getClothes() {
        return clothes;
    }
}
