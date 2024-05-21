package net.fuchsia.common.objects.item;

import net.fuchsia.common.quest.FadenQuests;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TestItem extends Item {
    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            //QUESTS SHOULD BE IN A STATIC CONTEXT LATER, JUST FOR TESTING
            FadenQuests.TEST.checkAndRewardStep(user, FadenIdentifier.create("use_test_item"));
        } else {
            //MinecraftClient.getInstance().setScreen(new CapeSelectScreen());
        }
        return super.use(world, user, hand);
    }
}
