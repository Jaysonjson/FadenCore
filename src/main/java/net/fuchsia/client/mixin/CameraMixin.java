package net.fuchsia.client.mixin;

import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Camera.class)
public class CameraMixin {

    //MOVED TO ENTITY MIXIN
    /*@Shadow private Vec3d pos;

    @Inject(at = @At("HEAD"), method = "getPos", cancellable = true)
    public void getModel(CallbackInfoReturnable<Vec3d> cir) {
        UUID uuid = MinecraftClient.getInstance().player.getUuid();
        RaceData data = ClientRaceCache.get(uuid);
        if(data.getRace() != null) {
            cir.setReturnValue(new Vec3d(this.pos.x, this.pos.y - (1 - data.getRace().size().y), this.pos.z));
        }
    }*/

}
