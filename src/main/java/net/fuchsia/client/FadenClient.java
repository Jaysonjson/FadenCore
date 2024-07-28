package net.fuchsia.client;

import com.google.common.reflect.TypeToken;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.client.handler.FadenItemModelHandler;
import net.fuchsia.client.overlay.StatsOverlay;
import net.fuchsia.client.registry.FadenItemModelRegistry;
import net.fuchsia.client.render.entity.NPCEntityRenderer;
import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.init.FadenCloths;
import net.fuchsia.common.init.FadenEntities;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.FadenTooltipData;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.fuchsia.network.FadenNetwork;
import net.fuchsia.util.FadenCheckSum;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class FadenClient implements ClientModInitializer {

    private static FadenItemModelRegistry ITEM_MODELS = new FadenItemModelRegistry();

    @Override
    public void onInitializeClient() {
        FadenNetwork.registerS2C();
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            tryToLoadTextures(FadenCloths::load, "CLOTH");
            tryToLoadTextures(ClientRaceSkinCache::add, "RACE SKINS");
            tryToLoadTextures(() -> {
                for (FadenCape cape : FadenCapes.getCapes()) {
                    cape.load();
                }
            }, "CAPES");
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

        registerModels();

        EntityRendererRegistry.register(FadenEntities.NPC, (context) -> new NPCEntityRenderer(context, true));

    }

    private static void tryToLoadTextures(Runnable action, String textureType) {
        try {
            action.run();
        } catch (Exception e) {
            Faden.LOGGER.error("Error trying to load textures for: " + textureType, e);
        }
    }

    public void registerModels() {
        for (FadenItemModelHandler.Data model : FadenItemModelHandler.getModels()) {
            if(!model.model.isEmpty()) {
                ITEM_MODELS.addModel(model.item, model.model);
            }
        }
    }

    public void loadClientItemValues() {
        new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/" + Faden.MC_VERSION + "/").mkdirs();
        File itemValues = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/" + Faden.MC_VERSION + "/item_values.json");
        if(itemValues.exists()) {
            try {
                ItemValues.reload(Faden.GSON.fromJson(new FileReader(itemValues), new TypeToken<HashMap<String, Integer>>(){}.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getItemValuesChecksum() {
        if(!new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/" + Faden.MC_VERSION + "/item_values.json").exists()) return "";
        return FadenCheckSum.checkSum(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/" + Faden.MC_VERSION + "/item_values.json"));
    }


    public static FadenItemModelRegistry getItemModels() {
        return ITEM_MODELS;
    }

}
