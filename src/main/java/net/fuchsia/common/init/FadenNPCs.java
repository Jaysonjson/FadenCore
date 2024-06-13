package net.fuchsia.common.init;

import net.fuchsia.common.npc.INPC;
import net.fuchsia.common.npc.NPCTexture;
import net.fuchsia.common.quest.FadenQuests;
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
            return null;
        }

        @Override
        public Identifier getId() {
            return Identifier.of("test");
        }

        @Override
        public ActionResult interaction(PlayerEntity player, Vec3d hitPos, Hand hand) {
            FadenQuests.TEST.startQuest(player.getUuid());
            player.sendMessage(Text.literal("test"), false);
            return ActionResult.CONSUME;
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
