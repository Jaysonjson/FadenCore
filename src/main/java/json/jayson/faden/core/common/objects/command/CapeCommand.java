package json.jayson.faden.core.common.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import json.jayson.faden.core.common.objects.command.types.CapeArgumentType;
import json.jayson.faden.core.network.FadenCoreNetwork;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.ServerPlayerDatas;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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
        Identifier cape = IdentifierArgumentType.getIdentifier(source, "cape");
        if(FadenCoreRegistry.CAPE.containsId(cape)) {
            source.getSource().sendFeedback(() -> Text.literal("Gave Cape: " + cape), false);
            PlayerData playerData = ServerPlayerDatas.getOrLoadPlayerData(player.getUuid());
            playerData.addCape(cape);
            for (ServerPlayerEntity serverPlayerEntity : source.getSource().getServer().getPlayerManager().getPlayerList()) {
                FadenCoreNetwork.Server.sendCapeUpdate(serverPlayerEntity, player.getUuid(), cape.toString(), false);
            }
        } else {
            source.getSource().sendFeedback(() -> Text.literal("Cape not found: " + cape), false);
        }
        return 0;
    }

    public static int removeCape(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
        Identifier cape = IdentifierArgumentType.getIdentifier(source, "cape");
        source.getSource().sendFeedback(() -> Text.literal("Removed Cape: " + cape), false);
        PlayerData playerData = ServerPlayerDatas.getOrLoadPlayerData(player.getUuid());
        playerData.removeCape(cape);
        for (ServerPlayerEntity serverPlayerEntity : source.getSource().getServer().getPlayerManager().getPlayerList()) {
            FadenCoreNetwork.Server.sendCapeUpdate(serverPlayerEntity, player.getUuid(), cape.toString(), true);
        }
        return 0;
    }

}
