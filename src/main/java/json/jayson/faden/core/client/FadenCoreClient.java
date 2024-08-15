package json.jayson.faden.core.client;

import com.google.common.reflect.TypeToken;
import json.jayson.faden.core.client.overlay.InstrumentMusicOverlay;
import json.jayson.faden.core.client.overlay.StatsOverlay;
import json.jayson.faden.core.client.render.entity.NPCEntityRenderer;
import json.jayson.faden.core.common.init.FadenCoreBlocks;
import json.jayson.faden.core.common.init.FadenCoreEntities;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.loader.api.FabricLoader;
import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.client.handler.FadenItemModelHandler;
import json.jayson.faden.core.client.registry.FadenItemModelRegistry;
import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.common.data.ItemValues;
import json.jayson.faden.core.common.init.FadenCoreClothes;
import json.jayson.faden.core.common.objects.music_instance.ClientMusicInstance;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipComponent;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipData;
import json.jayson.faden.core.common.race.skin.client.ClientRaceSkinCache;
import json.jayson.faden.core.network.FadenCoreNetwork;
import json.jayson.faden.core.util.FadenCoreCheckSum;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.PositionedSoundInstance;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

public class FadenCoreClient implements ClientModInitializer {

    private static FadenItemModelRegistry ITEM_MODELS = new FadenItemModelRegistry();

    @Override
    public void onInitializeClient() {
        FadenCoreNetwork.registerS2C();
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            tryToLoadTextures(FadenCoreClothes::load, "CLOTH");
            tryToLoadTextures(ClientRaceSkinCache::add, "RACE SKINS");
            tryToLoadTextures(() -> {
                for (FadenCoreCape cape : FadenCoreRegistry.CAPE) {
                    cape.load();
                }
            }, "CAPES");
        });

        TooltipComponentCallback.EVENT.register((component) -> {
            if(component instanceof FadenTooltipData data) {
                return new FadenTooltipComponent(data);
            }
            return null;
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            Iterator<ClientMusicInstance> iterator = ClientMusicInstances.getInstances().values().iterator();
            while (iterator.hasNext()) {
                ClientMusicInstance instance = iterator.next();
                instance.tick();
                if(instance.getInstance().getInstruments().isEmpty()) {
                    for (PositionedSoundInstance value : instance.getSoundInstances().values()) {
                        MinecraftClient.getInstance().getSoundManager().stop(value);
                    }
                    iterator.remove();
                }
            }
        });

        HudRenderCallback.EVENT.register(new StatsOverlay());
        HudRenderCallback.EVENT.register(new InstrumentMusicOverlay());
        EntityRendererRegistry.register(FadenCoreEntities.NPC, (context) -> new NPCEntityRenderer(context));
        registerModels();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if(PlayerModelCache.slimModel == null) {
                PlayerModelCache.slimModel = PlayerModelCache.makeSlimModel();
            }
            if(PlayerModelCache.wideModel == null) {
                PlayerModelCache.wideModel = PlayerModelCache.makeWideModel();
            }
        });
        setBlockRenderMaps();
        loadClientItemValues();
    }

    public void setBlockRenderMaps() {
        BlockRenderLayerMap.INSTANCE.putBlock(FadenCoreBlocks.NPC_SPAWNER_MARKER, RenderLayer.getCutout());
    }

    private static void tryToLoadTextures(Runnable action, String textureType) {
        try {
            action.run();
        } catch (Exception e) {
            FadenCore.LOGGER.error("Error trying to load textures for: " + textureType, e);
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
        new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/" + FadenCore.MC_VERSION + "/").mkdirs();
        File itemValues = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/" + FadenCore.MC_VERSION + "/item_values.json");
        if(itemValues.exists()) {
            try {
                ItemValues.reload(FadenCore.GSON.fromJson(new FileReader(itemValues), new TypeToken<HashMap<String, Integer>>(){}.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getItemValuesChecksum() {
        if(!new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/" + FadenCore.MC_VERSION + "/item_values.json").exists()) return "";
        return FadenCoreCheckSum.checkSum(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/" + FadenCore.MC_VERSION + "/item_values.json"));
    }


    public static FadenItemModelRegistry getItemModels() {
        return ITEM_MODELS;
    }
}
