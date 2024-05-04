package net.fuchsia.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fuchsia.client.overlay.StatsOverlay;
import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.client.handler.FadenItemModelHandler;
import net.fuchsia.client.registry.FadenItemModelRegistry;
import net.fuchsia.common.init.FadenCloths;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.FadenTooltipData;
import net.fuchsia.network.FadenNetwork;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;

public class FadenClient implements ClientModInitializer {

    private static FadenItemModelRegistry ITEM_MODELS = new FadenItemModelRegistry();

    @Override
    public void onInitializeClient() {
        FadenNetwork.registerS2C();
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ClientRaceSkinCache.add();
            for (FadenCape cape : FadenCapes.getCapes()) {
                cape.load();
            }
            FadenCloths.load();
        });


        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
        });

        TooltipComponentCallback.EVENT.register((component) -> {
            if(component instanceof FadenTooltipData data) {
                return new FadenTooltipComponent(data);
            }
            return null;
        });

        HudRenderCallback.EVENT.register(new StatsOverlay());
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
