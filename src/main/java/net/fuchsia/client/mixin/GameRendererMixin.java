package net.fuchsia.client.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    /*@ModifyVariable(method = "findCrosshairTarget", at = @At(value = "HEAD"), argsOnly = true, ordinal = 0)
    public Vec3d renderItem(Vec3d value) {
        return new Vec3d(value.x, value.y - 0.5f, value.z);
    }*/
}
