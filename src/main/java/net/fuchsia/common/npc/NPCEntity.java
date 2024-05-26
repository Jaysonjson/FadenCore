package net.fuchsia.common.npc;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NPCEntity extends PathAwareEntity {

    /*
        TODO: TRACKED DATA OF STRING, TRACKING INPC getId, STORE INPC ON AN ARRAYLIST TO ITERATE TROUGH
     */

    public NPCEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        player.sendMessage(Text.literal("Gingers have no souls"));
        return super.interactAt(player, hitPos, hand);
    }
}
