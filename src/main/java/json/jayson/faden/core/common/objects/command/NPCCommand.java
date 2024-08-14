package json.jayson.faden.core.common.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import json.jayson.faden.core.common.npc.NPC;
import json.jayson.faden.core.common.npc.NPCUtil;
import json.jayson.faden.core.common.objects.command.types.NPCArgumentType;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class NPCCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalCommandNode = CommandManager.literal("faden")
                .then(CommandManager.literal("npc").requires((source) -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("summon").then((CommandManager.argument("npc", NPCArgumentType.empty()))
                                .executes(NPCCommand::summonNPC))));

        dispatcher.register(literalCommandNode);
    }

    public static int summonNPC(CommandContext<ServerCommandSource> source) {
        Identifier id = IdentifierArgumentType.getIdentifier(source, "npc");
        FadenCoreRegistry.NPC.stream()
                .filter(npc -> npc.getIdentifier().toString().equalsIgnoreCase(id.toString()))
                .findFirst()
                .ifPresent(npc -> NPCUtil.summon(npc, source.getSource().getWorld(), source.getSource().getPosition()));
        return 0;
    }

}
