package net.fuchsia.common.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fuchsia.common.npc.NPCEntity;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FadenEntities {

    public static final EntityType<NPCEntity> NPC = registerEntity("npc", EntityType.Builder.create(SpawnGroup.MISC).dimensions(0.6F, 1.8F));

    public static <T extends Entity> EntityType<T> registerEntity(String name, SpawnGroup spawnGroup, EntityType.EntityFactory<T> entity,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE, FadenIdentifier.create(name), FabricEntityTypeBuilder.create(spawnGroup, entity).dimensions(EntityDimensions.changing(width, height)).build());
    }

    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.Builder builder) {
        return Registry.register(Registries.ENTITY_TYPE, FadenIdentifier.create(name), builder.build());
    }

    public static void register() {}
}