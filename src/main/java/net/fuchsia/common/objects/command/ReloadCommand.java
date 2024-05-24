package net.fuchsia.common.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.fuchsia.common.data.ItemValues;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ReloadCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalCommandNode = CommandManager.literal("faden")
                .then(CommandManager.literal("reload").requires((source) -> source.hasPermissionLevel(2))
                        //.then(CommandManager.literal("cape")
                                                //.executes(context -> reloadCapes(context)))

                        .then(CommandManager.literal("item_values")
                                                .executes(context -> reloadItemValues(context))));

        dispatcher.register(literalCommandNode);
    }

    public static int reloadCapes(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        for (ServerPlayerEntity serverPlayerEntity : source.getSource().getServer().getPlayerManager().getPlayerList()) {
            FadenNetwork.Server.reloadCapes(serverPlayerEntity);
        }
        source.getSource().sendMessage(Text.literal("Reloaded Capes!"));
        return 0;
    }

    public static int reloadItemValues(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        ItemValues.add();
        for (ServerPlayerEntity serverPlayerEntity : source.getSource().getServer().getPlayerManager().getPlayerList()) {
            FadenNetwork.Server.reloadItemValues(serverPlayerEntity);
        }
        source.getSource().sendMessage(Text.literal("Reloaded Item Values!"));
        return 0;
    }

}
