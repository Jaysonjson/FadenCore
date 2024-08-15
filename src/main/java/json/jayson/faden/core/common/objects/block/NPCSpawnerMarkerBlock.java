package json.jayson.faden.core.common.objects.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NPCSpawnerMarkerBlock extends Block {
    public NPCSpawnerMarkerBlock(Settings settings) {
        super(settings.noCollision());
    }


    /*
    * Will be called when placed in the world by the player but most importantly world gen, should be mainly used for world gen
    * */
    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        //TODO NPC Spawning Logic -> BlockEntity
    }
}
