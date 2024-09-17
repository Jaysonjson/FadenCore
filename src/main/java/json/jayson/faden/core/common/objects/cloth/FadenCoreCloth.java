package json.jayson.faden.core.common.objects.cloth;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public abstract class FadenCoreCloth {

    /*
    * First is slim, second is wide
    * */
    public abstract Pair<Identifier, Identifier> getTexture();

}
