package json.jayson.faden.core.common.init;

import json.jayson.faden.core.common.objects.blockentity.NPCSpawnerMarkerBlockEntity;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FadenCoreBlockEntities {

    public static final BlockEntityType<NPCSpawnerMarkerBlockEntity> NPC_SPAWNER_MARKER = register("npc_spawner_marker", BlockEntityType.Builder.create(NPCSpawnerMarkerBlockEntity::new, FadenCoreBlocks.NPC_SPAWNER_MARKER).build());

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> type) {
       return Registry.register(Registries.BLOCK_ENTITY_TYPE, FadenCoreIdentifier.create(id), type);
    }

    public static void init() {}

}
