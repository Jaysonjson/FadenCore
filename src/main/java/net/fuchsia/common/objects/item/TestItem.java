package net.fuchsia.common.objects.item;

import com.terraformersmc.modmenu.gui.ModsScreen;
import net.fuchsia.common.quest.TestQuest;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceUtil;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
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
            TestQuest test = new TestQuest();
            test.checkAndRewardStep(user, FadenIdentifier.create("use_test_item"));
        }
        return super.use(world, user, hand);
    }
}
