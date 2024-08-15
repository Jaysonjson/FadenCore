package json.jayson.faden.core.common.npc;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NPCUtil {

    public static void summon(NPC npc, World world, Vec3d pos) {
        NPCEntity entity = new NPCEntity(world, npc.getIdentifier().toString());
        entity.setPosition(pos);
        entity.getDataTracker().set(NPCEntity.NPC_DATA, npc.getIdentifier().toString());
        world.spawnEntity(entity);
        entity.getDataTracker().set(NPCEntity.NPC_DATA, npc.getIdentifier().toString());
    }

}
