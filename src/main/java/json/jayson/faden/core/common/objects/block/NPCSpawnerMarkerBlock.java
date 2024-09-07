package json.jayson.faden.core.common.objects.block;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.npc.NPCUtil;
import json.jayson.faden.core.common.objects.blockentity.NPCSpawnerMarkerBlockEntity;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NPCSpawnerMarkerBlock extends Block implements BlockEntityProvider {
    public NPCSpawnerMarkerBlock(Settings settings) {
        super(settings.noCollision());
    }


    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.getBlock() == Blocks.BARRIER || oldState.getBlock() != Blocks.AIR) {
            if (world.getBlockEntity(pos) instanceof NPCSpawnerMarkerBlockEntity blockEntity) {
                world.setBlockState(pos, Blocks.BARRIER.getDefaultState());
            }
        }
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(newState.getBlock() == Blocks.BARRIER) {
            if (world.getBlockEntity(pos) instanceof NPCSpawnerMarkerBlockEntity blockEntity) {
                String npc = blockEntity.npc;
                if (FadenCoreRegistry.NPC.containsId(Identifier.of(npc))) {
                    NPCUtil.summon(FadenCoreRegistry.NPC.get(Identifier.of(npc)), world, pos.toCenterPos());
                } else {
                    FadenCore.LOGGER.warn("NPCSpawnerMarkerBlockEntity has invalid NPC: {}", npc);
                }
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            } else {
                FadenCore.LOGGER.warn("NPCSpawnerMarkerBlockEntity is null at: {}", pos);
            }
        }
    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NPCSpawnerMarkerBlockEntity(pos, state);
    }
}
