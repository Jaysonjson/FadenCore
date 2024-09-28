package json.jayson.faden.core.common.npc;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import json.jayson.faden.core.common.init.FadenCoreEntities;
import json.jayson.faden.core.common.npc.entity.NPCEntity;
import json.jayson.faden.core.common.race.FadenCoreRace;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import json.jayson.faden.core.server.PlayerData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public abstract class NPC {

    public abstract NPCTexture getTexture();
    public Optional<FadenCoreRace> getRace() { return Optional.empty(); }
    public String getRaceSub() { return ""; }
    public PlayerData.RaceDataCosmetics getRaceCosmetics() { return new PlayerData.RaceDataCosmetics(); }

    public EntityType<? extends NPCEntity> getEntity() {
        return FadenCoreEntities.NPC;
    }

    public Identifier getIdentifier() {
        return FadenCoreRegistry.NPC.getId(this);
    }

    public abstract ActionResult interaction(PlayerEntity player, Vec3d hitPos, Hand hand);

    public boolean isInvulnerable() { return true; }

    public void initGoals(GoalSelector goalSelector, NPCEntity entity) {}

    public Brain.Profile<?> createBrainProfile(NPCEntity entity) {
        return Brain.createProfile(ImmutableList.of(), ImmutableList.of());
    }

    public Brain<?> deserializeBrain(Dynamic<?> dynamic, NPCEntity entity) {
        return this.createBrainProfile(entity).deserialize(dynamic);
    }

    public void initBrain(NPCEntity entity) {

    }
}
