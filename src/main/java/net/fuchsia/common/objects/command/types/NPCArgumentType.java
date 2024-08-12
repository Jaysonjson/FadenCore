package net.fuchsia.common.objects.command.types;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fuchsia.common.init.FadenCoreNPCs;
import net.fuchsia.common.npc.INPC;
import net.minecraft.command.argument.IdentifierArgumentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class NPCArgumentType extends IdentifierArgumentType {

    public NPCArgumentType() {
    }

    public static NPCArgumentType empty() {
        return new NPCArgumentType();
    }

    @Override
    public Collection<String> getExamples() {
        Collection<String> ex = new ArrayList<>();
        ex.add("test");
        return ex;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (INPC npc : FadenCoreNPCs.getNPCS()) {
            builder.suggest(npc.getId().toString());
        }
        return builder.buildFuture();
    }
}
