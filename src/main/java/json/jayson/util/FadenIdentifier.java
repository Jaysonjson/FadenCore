package json.jayson.util;

import json.jayson.Faden;
import net.minecraft.util.Identifier;

public class FadenIdentifier {

    public static Identifier create(String name) {
        return new Identifier(Faden.MOD_ID, name);
    }

    public static Identifier data(String name) {
        return create("data/" + name);
    }

}
