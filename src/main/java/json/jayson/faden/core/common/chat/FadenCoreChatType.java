package json.jayson.faden.core.common.chat;

import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public abstract class FadenCoreChatType {

    public abstract Optional<Text> getPrefix();
    /*
    * If set to 0, the chat is global
    * */
    public abstract int getProximity();

    public Identifier getIdentifier() {
        return FadenCoreRegistry.CHAT.getId(this);
    }
}
