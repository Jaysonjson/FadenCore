package net.fuchsia.common.objects.command.types;

import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.fuchsia.common.init.FadenRaces;
import net.fuchsia.race.Race;
import net.fuchsia.race.RaceEnum;

public class RaceSubIdArgumentType implements ArgumentType<String> {

    public RaceSubIdArgumentType() {
    }

    public static RaceSubIdArgumentType empty() {
        return new RaceSubIdArgumentType();
    }


    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        return reader.readUnquotedString();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String id = StringArgumentType.getString(context, "race");
        for (Race value : FadenRaces.getRegistry().values()) {
            if(value.getId().equalsIgnoreCase(id)) {
                for (String s : value.subIds()) {
                    builder.suggest(s);
                }
            }
        }
        builder.suggest("RANDOM");
        return builder.buildFuture();
    }
}
