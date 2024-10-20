package json.jayson.faden.core.common.cloth;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public abstract class FadenCoreCloth {

    /*
    * First is slim, second is wide
    * */
    public abstract Pair<Identifier, Identifier> getTexture();

    /*
    * if the players default second layer should be rendered
    * */
    public boolean renderDefaultSecondLayer() {
        return true;
    }
}
