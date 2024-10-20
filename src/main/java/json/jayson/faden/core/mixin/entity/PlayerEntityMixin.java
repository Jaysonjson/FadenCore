package json.jayson.faden.core.mixin.entity;

import json.jayson.faden.core.util.FadenCoreCapeUtil;
import json.jayson.faden.core.common.race.FadenCoreRace;
import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.client.ClientPlayerDatas;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "isPartVisible", cancellable = true)
    public void isPartVisible(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
        if (FadenCoreCapeUtil.playerHasCape(uuid) && modelPart.getName().equalsIgnoreCase("cape")) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getBaseDimensions", cancellable = true)
    public void baseDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        PlayerData data = ClientPlayerDatas.getPlayerData(uuid);
        FadenCoreRace fadenCoreRace = data.getRaceSaveData().getRace();
        if (fadenCoreRace != null) {
            if(fadenCoreRace.getPoseDimensions() != null) {
                cir.setReturnValue((EntityDimensions) fadenCoreRace.getPoseDimensions().getOrDefault(pose, fadenCoreRace.getDimensions()));
            }
        }
    }

}
