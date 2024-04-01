package json.jayson.mixin;


import json.jayson.common.objects.ItemValueTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Item.class)
public class ItemMixin {


    @Inject(at = @At("HEAD"), method = "getTooltipData", cancellable = true)
    private void getTooltipData(ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        if(cir.getReturnValue() == null && !(stack.getItem() instanceof BundleItem)) {
            cir.setReturnValue(Optional.of(new ItemValueTooltipData()));
        } else if(!cir.getReturnValue().isPresent()) {
            cir.setReturnValue(Optional.of(new ItemValueTooltipData()));
        }
    }

}
