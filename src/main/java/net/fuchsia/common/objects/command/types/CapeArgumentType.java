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

import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCapes;

public class CapeArgumentType implements ArgumentType<String> {

    public CapeArgumentType() {
    }

    public static CapeArgumentType empty() {
        return new CapeArgumentType();
    }

    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        return reader.readUnquotedString();
    }

    @Override
    public Collection<String> getExamples() {
        Collection<String> ex = new ArrayList<>();
        for (FadenCape cape : FadenCapes.getCapes()) {
        	ex.add(cape.getId());
        }
        return ex;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (FadenCape cape : FadenCapes.getCapes()) {
        	builder.suggest(cape.getId());
        }
        return builder.buildFuture();
    }

}
