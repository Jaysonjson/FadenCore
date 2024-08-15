package json.jayson.faden.core.common.objects.command.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.command.argument.IdentifierArgumentType;

public class CapeArgumentType extends IdentifierArgumentType {

    public CapeArgumentType() {
    }

    public static CapeArgumentType empty() {
        return new CapeArgumentType();
    }

    @Override
    public Collection<String> getExamples() {
        Collection<String> ex = new ArrayList<>();
        for (FadenCoreCape cape : FadenCoreRegistry.CAPE) {
        	ex.add(cape.getIdentifier().toString());
        }
        return ex;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (FadenCoreCape cape : FadenCoreRegistry.CAPE) {
        	builder.suggest(cape.getIdentifier().toString());
        }
        return builder.buildFuture();
    }

}
