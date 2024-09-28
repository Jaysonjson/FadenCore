package json.jayson.faden.core.common.objects.block.item;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.init.FadenCoreBlocks;
import json.jayson.faden.core.common.init.FadenCoreDataComponents;
import json.jayson.faden.core.common.objects.blockentity.NPCSpawnerMarkerBlockEntity;
import json.jayson.faden.core.common.objects.tooltip.ItemToolTipEntryRenderer;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipComponent;
import json.jayson.faden.core.common.objects.tooltip.ToolTipEntry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;

public class NPCSpawnerMarkerBlockItem extends BlockItem implements ItemToolTipEntryRenderer {
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
    public Collection<ToolTipEntry> getToolTipEntries(FadenTooltipComponent component) {
        ArrayList<ToolTipEntry> entries = new ArrayList<>();
        ItemStack stack = component.data.itemStack;
        if(stack.contains(FadenCoreDataComponents.NPC_ID)) {
            //entries.add(ToolTipEntry.of(Text.of("NPC ID: " + stack.get(FadenCoreDataComponents.NPC_ID)), FadenCoreBlocks.NPC_SPAWNER_MARKER_ITEM));
        }
        return entries;
    }
}
