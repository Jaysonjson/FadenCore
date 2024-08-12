package net.fuchsia.common.objects.command.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.fuchsia.common.init.FadenRaces;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceEnum;
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
        for (Race value : FadenRaces.getRegistry().values()) {
            ex.add(value.getIdentifier().toString());
        }
        return ex;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (Race value : FadenRaces.getRegistry().values()) {
            builder.suggest(value.getIdentifier().toString());
        }
        return builder.buildFuture();
    }
}
