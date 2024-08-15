package json.jayson.faden.core.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import json.jayson.faden.core.FadenCore;

public class FadenCoreModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> FadenCore.openConfig(parent);
    }
}
