package net.fuchsia.util;

import net.fuchsia.FadenCore;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class FadenCoreIdentifier {

    public static Identifier create(String name) {
        return Identifier.of(FadenCore.MOD_ID, name);
    }


    public static ModelIdentifier modelId(String name) {
        return new ModelIdentifier(FadenCoreIdentifier.create(name), "inventory");
    }


    public static Identifier data(String name) {
        return create("data/" + name);
    }

    public static Identifier minecraft(String name) {
        return Identifier.of(name);
    }
}
