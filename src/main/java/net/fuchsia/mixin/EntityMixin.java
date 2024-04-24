package net.fuchsia.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.common.objects.tooltip.ItemValueTooltipComponent;
import net.fuchsia.common.objects.tooltip.ItemValueTooltipData;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.FabricUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract float getStandingEyeHeight();
    @Shadow private float standingEyeHeight;
    @Shadow protected UUID uuid;
    private Entity entity = ((Entity) ((Object) this));


    @Environment(EnvType.CLIENT)
    @Inject(at = @At("HEAD"), method = "getStandingEyeHeight", cancellable = true)
    private void eyeHeightClient(CallbackInfoReturnable<Float> cir) {
        if(entity instanceof PlayerEntity) {
            RaceData data = ClientRaceCache.get(uuid);
            if (data.getRace() != null && data.getRace().size().y != 1f) {
                cir.setReturnValue(this.standingEyeHeight - (1 - data.getRace().size().y * data.getRace().size().y + 0.1f));
            }
        }
    }

    @Environment(EnvType.SERVER)
    @Inject(at = @At("HEAD"), method = "getStandingEyeHeight", cancellable = true)
    private void eyeHeightServer(CallbackInfoReturnable<Float> cir) {
        if(entity instanceof PlayerEntity) {
            RaceData data = ServerRaceCache.getCache().get(uuid);
            if (data.getRace() != null && data.getRace().size().y != 1f) {
                cir.setReturnValue(this.standingEyeHeight - (1 - data.getRace().size().y * data.getRace().size().y + 0.1f));
            }
        }
    }
}
