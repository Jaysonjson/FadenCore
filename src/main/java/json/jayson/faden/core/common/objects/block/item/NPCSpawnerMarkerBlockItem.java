package json.jayson.faden.core.common.objects.block.item;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.init.FadenCoreDataComponents;
import json.jayson.faden.core.common.objects.blockentity.NPCSpawnerMarkerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class NPCSpawnerMarkerBlockItem extends BlockItem {
    public NPCSpawnerMarkerBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        ItemStack stack = context.getStack();
        ActionResult result = super.place(context);
        if(context.getPlayer() != null && stack.contains(FadenCoreDataComponents.NPC_ID)) {
            PlayerEntity player = context.getPlayer();
            World world = player.getWorld();
            BlockPos pos = context.getBlockPos();
            if(world.getBlockEntity(pos) instanceof NPCSpawnerMarkerBlockEntity blockEntity) {
                blockEntity.npc = stack.get(FadenCoreDataComponents.NPC_ID);
            } else {
                FadenCore.LOGGER.warn("NPCSpawnerMarkerBlockEntity is null at: {}", pos);
            }
        }
        return result;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(stack.contains(FadenCoreDataComponents.NPC_ID)) {
            tooltip.add(Text.of("NPC: " + stack.get(FadenCoreDataComponents.NPC_ID)));
        }
    }
}
