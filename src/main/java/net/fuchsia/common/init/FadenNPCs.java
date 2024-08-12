package net.fuchsia.common.init;

import net.fuchsia.common.npc.INPC;
import net.fuchsia.common.npc.NPCTexture;
import net.fuchsia.common.quest.FadenQuests;
import net.fuchsia.common.race.Race;
import net.fuchsia.server.PlayerData;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collection;

public class FadenNPCs {

    private static ArrayList<INPC> NPCS = new ArrayList<>();

    public static INPC TEST = register(new INPC() {
        @Override
        public NPCTexture getTexture() {
            return new NPCTexture("black_harengon", true, Identifier.of("faden:skin/black/black_harengon_default"));
        }

        @Override
        public Identifier getId() {
            return FadenIdentifier.create("test");
        }

        @Override
        public ActionResult interaction(PlayerEntity player, Vec3d hitPos, Hand hand) {
            FadenQuests.TEST.startQuest(player.getUuid());
            player.sendMessage(Text.literal("test"), false);
            return ActionResult.CONSUME;
        }

        @Override
        public Race getRace() {
            return FadenRaces.HARENGON;
        }

        @Override
        public String getRaceSub() {
            return "black";
        }

        @Override
        public PlayerData.RaceDataCosmetics getRaceCosmetics() {
            PlayerData.RaceDataCosmetics cosmetics = new PlayerData.RaceDataCosmetics();
            cosmetics.getHead().add("ear_0");
            return cosmetics;
        }
    });

    private static INPC register(INPC inpc) {
        NPCS.add(inpc);
        return inpc;
    }

    public static ArrayList<INPC> getNPCS() {
        return NPCS;
    }
}
