package json.jayson.faden.core.common.objects.command.types;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import json.jayson.faden.core.common.npc.NPC;
import json.jayson.faden.core.registry.FadenCoreRegistry;
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
        for (NPC npc : FadenCoreRegistry.NPC) {
            builder.suggest(npc.getIdentifier().toString());
        }
        return builder.buildFuture();
    }
}
