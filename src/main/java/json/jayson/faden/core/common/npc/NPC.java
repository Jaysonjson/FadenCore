package json.jayson.faden.core.common.npc;

import json.jayson.faden.core.common.race.Race;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import json.jayson.faden.core.server.PlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public abstract class NPC {

    public abstract NPCTexture getTexture();
    public abstract ActionResult interaction(PlayerEntity player, Vec3d hitPos, Hand hand);
    public abstract Race getRace();
    public abstract String getRaceSub();
    public abstract PlayerData.RaceDataCosmetics getRaceCosmetics();

    public Identifier getIdentifier() {
        return FadenCoreRegistry.NPC.getId(this);
    }

}
