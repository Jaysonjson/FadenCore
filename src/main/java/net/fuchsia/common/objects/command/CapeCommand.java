package net.fuchsia.common.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.common.objects.CoinMap;
import net.fuchsia.common.objects.command.types.CapeArgumentType;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class CapeCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalCommandNode = CommandManager.literal("faden")
                .then(CommandManager.literal("cape").requires((source) -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .then(CommandManager.argument("cape", CapeArgumentType.empty())
                                                .executes(context -> addCape(context)))))

                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .then(CommandManager.argument("cape",  CapeArgumentType.empty())
                                                .executes(context -> removeCape(context)))))
                );

        dispatcher.register(literalCommandNode);
    }

    public static int addCape(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
        String cape = StringArgumentType.getString(source, "cape");
        source.getSource().sendFeedback(() -> Text.literal("Gave Cape: " + cape), false);
        ArrayList<String> capes = FadenCapes.getPlayerCapes().getOrDefault(player.getUuid(), new ArrayList<>());
        if(!capes.contains(cape)) {
            capes.add(cape);
        }
        FadenCapes.getPlayerCapes().put(player.getUuid(), capes);
        for (ServerPlayerEntity serverPlayerEntity : source.getSource().getServer().getPlayerManager().getPlayerList()) {
            FadenNetwork.Server.sendCapeUpdate(serverPlayerEntity, player.getUuid(), cape, false);
        }
        return 0;
    }

    public static int removeCape(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
        String cape = StringArgumentType.getString(source, "cape");
        source.getSource().sendFeedback(() -> Text.literal("Removed Cape: " + cape), false);
        FadenCapes.getPlayerCapes().getOrDefault(player.getUuid(), new ArrayList<>()).remove(cape);
        for (ServerPlayerEntity serverPlayerEntity : source.getSource().getServer().getPlayerManager().getPlayerList()) {
            FadenNetwork.Server.sendCapeUpdate(serverPlayerEntity, player.getUuid(), cape, true);
        }
        return 0;
    }

}
