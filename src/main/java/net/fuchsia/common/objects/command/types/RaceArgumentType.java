package net.fuchsia.common.objects.command.types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fuchsia.common.race.Race;
import net.minecraft.command.CommandSource;
import net.minecraft.util.StringIdentifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RaceArgumentType implements ArgumentType<String> {

    boolean sub_id = false;
    public RaceArgumentType(boolean sub_id) {
       this.sub_id = sub_id;
    }

    public static RaceArgumentType of(boolean sub_id) {
        return new RaceArgumentType(sub_id);
    }


    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        return reader.readUnquotedString();
    }

    @Override
    public Collection<String> getExamples() {
        Collection<String> ex = new ArrayList<>();
        if(!sub_id) {
            for (Race value : Race.values()) {
                ex.add(value.getId());
            }
        }
        return ex;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if(!sub_id) {
            for (Race value : Race.values()) {
                builder.suggest(value.getId());
            }
        } else {
            String id = StringArgumentType.getString(context, "race");
            for (Race value : Race.values()) {
                if(value.getId().equalsIgnoreCase(id)) {
                    for (String s : value.subIds()) {
                        builder.suggest(s);
                    }
                }
            }
        }
        return builder.buildFuture();
    }
}
