package net.fuchsia.common.objects.block;

import net.fuchsia.client.screen.CapeSelectScreen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CapeStandBlock extends Block {
    public CapeStandBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(world.isClient) {
            MinecraftClient.getInstance().setScreen(new CapeSelectScreen());
        }
        return ActionResult.CONSUME;
    }
}
