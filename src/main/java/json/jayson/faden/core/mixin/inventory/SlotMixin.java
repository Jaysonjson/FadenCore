package json.jayson.faden.core.mixin.inventory;

import json.jayson.faden.core.common.slot.ISlot;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin implements ISlot {

    @Unique
    boolean enabled = true;

    @Inject(method = "isEnabled", at = @At("TAIL"), cancellable = true)
    public void isEnabled(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(enabled);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
