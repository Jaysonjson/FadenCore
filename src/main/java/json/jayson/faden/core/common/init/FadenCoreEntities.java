package json.jayson.faden.core.common.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import json.jayson.faden.core.common.npc.entity.NPCEntity;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FadenCoreEntities {

    //public static final EntityType<NPCEntity> NPC = registerEntity("npc", EntityType.Builder.create(SpawnGroup.MISC).dimensions(0.6F, 1.8F));

    public static final EntityType<NPCEntity> NPC = registerEntity("npc", SpawnGroup.MISC, (type, world) -> new NPCEntity(world), 0.6F, 1.8F);

    public static <T extends Entity> EntityType<T> registerEntity(String name, SpawnGroup spawnGroup, EntityType.EntityFactory<T> entity,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE, FadenCoreIdentifier.create(name), FabricEntityTypeBuilder.create(spawnGroup, entity).dimensions(EntityDimensions.changing(width, height)).build());
    }

    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.Builder builder) {
        return Registry.register(Registries.ENTITY_TYPE, FadenCoreIdentifier.create(name), builder.build());
    }

    public static void init() {}
}