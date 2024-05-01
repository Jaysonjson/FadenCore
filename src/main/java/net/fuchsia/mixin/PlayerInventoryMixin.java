package net.fuchsia.mixin;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fuchsia.ClothSlot;
import net.fuchsia.IClothInventory;
import net.fuchsia.client.render.feature.ChestFeatureRenderer;
import net.fuchsia.client.render.feature.ClothFeatureRenderer;
import net.fuchsia.client.render.feature.HeadFeatureRenderer;
import net.fuchsia.common.objects.item.Cloth;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
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


    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(PlayerEntity player, CallbackInfo ci) {
        clothes = DefaultedList.ofSize(4, ItemStack.EMPTY);
        combinedInventory = ImmutableList.of(main, armor, offHand, clothes);
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
        cir.setReturnValue(nbtList);
    }


    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readNbt(NbtList nbtList, CallbackInfo ci) {
        this.clothes.clear();
        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.fromNbt(this.player.getRegistryManager(), nbtCompound).orElse(ItemStack.EMPTY);
            if(j >= 200 && j < this.clothes.size() + 200) {
                this.clothes.set(j - 200, itemStack);
            }
        }
    }

    @Override
    public ItemStack getClothOrArmor(EquipmentSlot slot, ClothSlot clothSlot) {
        ItemStack stack = armor.get(slot.getEntitySlotId());
        if(stack.getItem() instanceof Cloth cloth) {
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
