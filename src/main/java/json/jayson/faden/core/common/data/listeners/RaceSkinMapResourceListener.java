package json.jayson.faden.core.common.data.listeners;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.race.RaceSkinMap;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class RaceSkinMapResourceListener implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return FadenCoreIdentifier.create("race_skin_map");
    }

    @Override
    public void reload(ResourceManager manager) {
        for (String s : FadenCore.ADDONS.keySet()) {
            RaceSkinMap.addSkins(s, FabricLoader.getInstance().getModContainer(s).get());
            System.out.println("Loaded Skin Map for " + s);
        }
    }
}
