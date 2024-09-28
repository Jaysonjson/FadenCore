package json.jayson.faden.core.common.objects.command.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import json.jayson.faden.core.common.race.FadenCoreRace;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.command.argument.IdentifierArgumentType;

public class RaceArgumentType extends IdentifierArgumentType {

    public RaceArgumentType() {
    }

    public static RaceArgumentType empty() {
        return new RaceArgumentType();
    }

    @Override
    public Collection<String> getExamples() {
        Collection<String> ex = new ArrayList<>();
        if(FadenCoreRegistry.RACE.size() > 0) {
            ex.add(FadenCoreRegistry.RACE.get(0).getIdentifier().toString());
        }
        return ex;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (FadenCoreRace value : FadenCoreRegistry.RACE) {
            builder.suggest(value.getIdentifier().toString());
        }
        return builder.buildFuture();
    }
}
