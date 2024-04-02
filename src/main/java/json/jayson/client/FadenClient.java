package json.jayson.client;

import json.jayson.client.handler.FadenItemModelHandler;
import json.jayson.client.registry.FadenItemModelRegistry;
import json.jayson.common.objects.tooltip.ItemValueTooltipComponent;
import json.jayson.common.objects.tooltip.ItemValueTooltipData;
import json.jayson.network.FadenNetwork;
import json.jayson.skin.client.ClientSkinCache;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;

public class FadenClient implements ClientModInitializer {

    private static FadenItemModelRegistry ITEM_MODELS = new FadenItemModelRegistry();

    @Override
    public void onInitializeClient() {
        FadenNetwork.registerS2C();

        ClientSkinCache.retrieveLocalSkins();
        ClientSkinCache.loadSavedSkins();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            byte[] cachedSkin = ClientSkinCache.getSavedSkin();
            if(cachedSkin != null) {
                FadenNetwork.Client.sendSingletonSkin(client.player.getUuid(), cachedSkin);
            }
        });


        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ClientSkinCache.removeClientSkin(client.player.getUuid());
        });

        TooltipComponentCallback.EVENT.register((component) -> {
            if(component instanceof ItemValueTooltipData data) {
                return new ItemValueTooltipComponent(data);
            }
            return null;
        });
    }

    public void registerModels() {
        for (FadenItemModelHandler.Data model : FadenItemModelHandler.getModels()) {
            if(!model.model.isEmpty()) {
                ITEM_MODELS.addModel(model.item, model.model);
            }
        }
    }


    public static FadenItemModelRegistry getItemModels() {
        return ITEM_MODELS;
    }

}
