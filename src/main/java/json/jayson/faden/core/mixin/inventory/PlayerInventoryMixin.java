package json.jayson.faden.core.mixin.inventory;

import com.google.common.collect.ImmutableList;
import json.jayson.faden.core.common.objects.item.IClothItem;
import json.jayson.faden.core.mixin_interfaces.ExtraInventory;
import json.jayson.faden.core.common.slot.ClothSlot;
import json.jayson.faden.core.mixin_interfaces.IPlayerInventory;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements IPlayerInventory {

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
                nbtCompound.putByte("Slot", (byte) (i + 230));
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

            if(j >= 230 && j < this.gear.size() + 230) {
                this.gear.set(j - 230, itemStack);
            }
        }
    }

    @Override
    public ItemStack getClothOrArmor(EquipmentSlot slot, ClothSlot clothSlot) {
        ItemStack stack = armor.get(slot.getEntitySlotId());
        if(stack.getItem() instanceof IClothItem) {
           return stack;
        }  else {
            return clothes.get(clothSlot.getEntitySlotId());
        }
    }

    @Override
    public DefaultedList<ItemStack> getClothes() {
        return clothes;
    }

    @Override
    public Collection<ItemStack> getBracelets() {
        return Arrays.asList(gear.get(0), gear.get(1));
    }

    @Override
    public ItemStack getBelt() {
        return gear.get(2);
    }

    @Override
    public ItemStack getGearStack(int slot) {
        return gear.get(slot);
    }

    @Override
    public ItemStack getNecklace() {
        return gear.get(3);
    }
}
