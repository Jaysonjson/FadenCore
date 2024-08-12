package net.fuchsia.common.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.fuchsia.common.init.FadenCoreRaces;
import net.fuchsia.common.objects.command.types.RaceArgumentType;
import net.fuchsia.common.objects.command.types.RaceSubIdArgumentType;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceUtil;
import net.fuchsia.server.PlayerData;
import net.fuchsia.util.PlayerDataUtil;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RaceCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalCommandNode = CommandManager.literal("faden")
                .then(CommandManager.literal("race").requires((source) -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .then(CommandManager.argument("race", RaceArgumentType.empty()).then(CommandManager.argument("sub_id", RaceSubIdArgumentType.empty())
                                                .executes(context -> setRace(context))))))

                        .then(CommandManager.literal("remove").then(CommandManager.argument("player", EntityArgumentType.player())
                                        .executes(context -> removeRace(context))))
                        .then(CommandManager.literal("get")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .executes(context -> getRace(context)))));
        dispatcher.register(literalCommandNode);
    }

    public static int getRace(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
        PlayerData data = PlayerDataUtil.getClientOrServer(player.getUuid());
        if(data.getRaceSaveData().getRace() != null) {
            Race race = data.getRaceSaveData().getRace();
            source.getSource().sendFeedback(() -> Text.literal("Race: " + race.getIdentifier().toString() + " with SubId:" + data.getRaceSaveData().getRaceSub()), false);
        } else {
            source.getSource().sendFeedback(() -> Text.literal("Player does not have a race!"), false);
        }
        return 0;
    }

    public static int setRace(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        try {
            PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
            Identifier race = IdentifierArgumentType.getIdentifier(source, "race");
            String sub_id = StringArgumentType.getString(source, "sub_id");
            if(sub_id.equalsIgnoreCase("RANDOM")) {
                RaceUtil.setPlayerRace((ServerPlayerEntity) player, FadenCoreRaces.getRace(race));
            } else {
                RaceUtil.setPlayerRace((ServerPlayerEntity) player, FadenCoreRaces.getRace(race), sub_id);
            }
            source.getSource().sendFeedback(() -> Text.literal("Set Race to : " + race + " with SubId: " + sub_id), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int removeRace(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        try {
            PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
            PlayerData data = PlayerDataUtil.getClientOrServer(player.getUuid());
            data.resetRaceData();
            data.sync();
            source.getSource().sendFeedback(() -> Text.literal("Removed race data from " + player.getName().getString()), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
