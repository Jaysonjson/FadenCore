package net.fuchsia.mixin.packets;

import net.minecraft.network.encoding.StringEncoding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(StringEncoding.class)
public class StringEncodingMixin {

    @ModifyVariable(method = "encode", at = @At(value = "HEAD"), argsOnly = true)
    private static int maxLengthEncode(int maxLength) {
        return 96252;
    }

    @ModifyVariable(method = "decode", at = @At(value = "HEAD"), argsOnly = true)
    private static int maxLengthDecode(int maxLength) {
        return 96252;
    }

}

