package json.jayson.faden.core.client.data.listeners;

import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class CapeResourceListener implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return FadenCoreIdentifier.create("cape");
    }

    @Override
    public void reload(ResourceManager manager) {
        for (FadenCoreCape cape : FadenCoreRegistry.CAPE) {
            cape.load();
        }
    }
}
