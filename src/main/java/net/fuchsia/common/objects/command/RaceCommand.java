package net.fuchsia.common.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fuchsia.common.objects.CoinMap;
import net.fuchsia.common.objects.command.types.RaceArgumentType;
import net.fuchsia.common.objects.command.types.RaceSubIdArgumentType;
import net.fuchsia.common.race.IRace;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceSkinUtil;
import net.fuchsia.common.race.RaceUtil;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class RaceCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalCommandNode = CommandManager.literal("faden")
                .then(CommandManager.literal("race").requires((source) -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .then(CommandManager.argument("race", RaceArgumentType.empty()).then(CommandManager.argument("sub_id", RaceSubIdArgumentType.empty())
                                                .executes(context -> setRace(context))))))

                        .then(CommandManager.literal("get")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .executes(context -> getRace(context)))));
        dispatcher.register(literalCommandNode);
    }

    public static int getRace(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
        RaceData data = ClientRaceCache.getCache().getOrDefault(player.getUuid(), new RaceData(null, "", ""));
        if(data.getRace() != null) {
            source.getSource().sendFeedback(() -> Text.literal("Race: " + data.getRace().getId() + " with SubId:" + data.getSubId()), false);
        } else {
            source.getSource().sendFeedback(() -> Text.literal("Player does not have a race!"), false);
        }
        return 0;
    }

    public static int setRace(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
        String race = StringArgumentType.getString(source, "race");
        String sub_id = StringArgumentType.getString(source, "sub_id");
        RaceUtil.setPlayerRace((ServerPlayerEntity) player, Race.valueOf(race.toUpperCase()), sub_id);
        source.getSource().sendFeedback(() -> Text.literal("Set Race to : " + race + " with SubId: " + sub_id), false);
        return 0;
    }

}
