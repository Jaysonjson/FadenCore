package json.jayson.faden.core.common.objects.blockentity;

import json.jayson.faden.core.common.init.FadenCoreBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class NPCSpawnerMarkerBlockEntity extends BlockEntity {
    public String npc = "";

    public NPCSpawnerMarkerBlockEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        npc = nbt.getString("npc");
        super.readNbt(nbt, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putString("npc", npc);
        super.writeNbt(nbt, registryLookup);
    }
}
