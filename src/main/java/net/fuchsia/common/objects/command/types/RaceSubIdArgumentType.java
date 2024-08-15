package net.fuchsia.common.objects.command.types;

import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.fuchsia.common.init.FadenCoreRaces;
import net.fuchsia.common.race.Race;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

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
        Identifier id = IdentifierArgumentType.getIdentifier((CommandContext<ServerCommandSource>) context, "race");
        for (Race value : FadenCoreRaces.getRegistry().values()) {
            if(value.getIdentifier().toString().equalsIgnoreCase(id.toString())) {
                for (String s : value.subIds()) {
                    builder.suggest(s);
                }
            }
        }
        builder.suggest("RANDOM");
        return builder.buildFuture();
    }
}
