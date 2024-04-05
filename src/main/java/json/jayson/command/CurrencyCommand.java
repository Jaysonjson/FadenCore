package json.jayson.command;

import com.mojang.brigadier.CommandDispatcher;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import json.jayson.common.objects.CoinMap;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.DataCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.text.Text;

public class CurrencyCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalCommandNode = CommandManager.literal("currency").requires((source) -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("give").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("amount", IntegerArgumentType.integer())).executes(context -> giveCurrency(context))))
                .then(CommandManager.literal("remove").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("amount", IntegerArgumentType.integer())).executes(context -> removeCurrency(context))))
                .then(CommandManager.literal("count").then(CommandManager.argument("player", EntityArgumentType.player()).executes(context -> countCurrency(context))));
        dispatcher.register(literalCommandNode);
    }

    public static int countCurrency(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
        source.getSource().sendFeedback(() -> Text.literal("Amount: " + CoinMap.countCurrency(player.getInventory())), false);
        return 0;
    }

    public static int removeCurrency(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
        int amount = IntegerArgumentType.getInteger(source, "amount");
        CoinMap.removeCurrency(player.getWorld(), player.getBlockPos(), player.getInventory(), amount, false);
        source.getSource().sendFeedback(() -> Text.literal("Removed: " + amount), false);
        return 0;
    }

    public static int giveCurrency(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = EntityArgumentType.getPlayer(source, "player");
        int amount = IntegerArgumentType.getInteger(source, "amount");
        CoinMap.addCurrency(player.getWorld(), player.getBlockPos(), player.getInventory(), amount);
        source.getSource().sendFeedback(() -> Text.literal("Gave: " + amount), false);
        return 0;
    }
}
