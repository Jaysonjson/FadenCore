package json.jayson.faden.core.common.npc;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.npc.entity.NPCEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NPCUtil {

    public static void summon(NPC npc, World world, Vec3d pos) {
        try {
            NPCEntity entity = npc.getEntity().create(world);
            if (entity != null) {
                entity.setPosition(pos);
                world.spawnEntity(entity);
                entity.setNpc(npc);
                entity.getDataTracker().set(NPCEntity.NPC_DATA, npc.getIdentifier().toString());
            } else {
                FadenCore.LOGGER.warn("Failed to summon NPC: {}", npc.getIdentifier());
            }
        } catch (Exception e) {
            FadenCore.LOGGER.error("Failed to summon NPC: {}", npc.getIdentifier());
            e.printStackTrace();
        }
    }

}
