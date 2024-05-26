package net.fuchsia.common.npc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public interface INPC {

    NPCTexture getTexture();
    Identifier getId();
    ActionResult interaction(PlayerEntity player, Vec3d hitPos, Hand hand);
    
}
