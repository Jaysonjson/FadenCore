package net.fuchsia.common.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fuchsia.common.init.FadenCoreNPCs;
import net.fuchsia.common.npc.INPC;
import net.fuchsia.common.npc.NPCUtil;
import net.fuchsia.common.objects.command.types.NPCArgumentType;
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
        for (INPC npc : FadenCoreNPCs.getNPCS()) {
            if(npc.getId().toString().equalsIgnoreCase(id.toString())) {
                NPCUtil.summon(npc, source.getSource().getWorld(), source.getSource().getPosition());
                break;
            }
        }
        return 0;
    }

}
