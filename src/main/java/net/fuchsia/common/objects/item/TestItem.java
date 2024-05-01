package net.fuchsia.common.objects.item;

import com.mojang.authlib.GameProfile;
import com.terraformersmc.modmenu.gui.ModsScreen;
import net.fuchsia.common.quest.FadenQuests;
import net.fuchsia.common.quest.TestQuest;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceUtil;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.message.ChatVisibility;
import net.minecraft.network.packet.c2s.common.SyncedClientOptions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.UUID;

public class TestItem extends Item {
    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            //QUESTS SHOULD BE IN A STATIC CONTEXT LATER, JUST FOR TESTING
            FadenQuests.TEST.checkAndRewardStep(user, FadenIdentifier.create("use_test_item"));
        }
        return super.use(world, user, hand);
    }
}
