package json.jayson.faden.core.common.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import json.jayson.faden.core.common.data.ItemValues;
import json.jayson.faden.core.network.FadenCoreNetwork;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ItemValueCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        LiteralArgumentBuilder<ServerCommandSource> literalCommandNode = CommandManager.literal("faden")
                .then(CommandManager.literal("item_value").requires((source) -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("set").then((CommandManager.argument("item", ItemStackArgumentType.itemStack(registryAccess))).then(CommandManager.argument("value", IntegerArgumentType.integer(0, 999999999))
                                .executes(ItemValueCommand::setValue)))));

        dispatcher.register(literalCommandNode);
    }

    public static int setValue(CommandContext<ServerCommandSource> source) {
        Item item = ItemStackArgumentType.getItemStackArgument(source, "item").getItem();
        int value = IntegerArgumentType.getInteger(source, "value");
        ItemValues.VALUES.put(item, value);
        for (ServerPlayerEntity serverPlayerEntity : source.getSource().getServer().getPlayerManager().getPlayerList()) {
            FadenCoreNetwork.Server.sendItemValueUpdate(serverPlayerEntity, item, value);
        }
        source.getSource().sendMessage(Text.literal("Set " + item.toString() + " value to " + value));
        return 0;
    }
}
