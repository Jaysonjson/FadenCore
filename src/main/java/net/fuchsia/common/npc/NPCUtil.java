package net.fuchsia.common.npc;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NPCUtil {

    public static void summon(INPC npc, World world, Vec3d pos) {
        NPCEntity entity = new NPCEntity(world, npc.getId().toString());
        entity.setPosition(pos);
        world.spawnEntity(entity);
    }

}
