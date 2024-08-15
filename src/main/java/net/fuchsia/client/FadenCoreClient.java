package net.fuchsia.client;

import com.google.common.reflect.TypeToken;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.FadenCore;
import net.fuchsia.client.handler.FadenItemModelHandler;
import net.fuchsia.client.overlay.InstrumentMusicOverlay;
import net.fuchsia.client.overlay.StatsOverlay;
import net.fuchsia.client.registry.FadenItemModelRegistry;
import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCoreCapes;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.init.FadenCoreClothes;
import net.fuchsia.common.objects.music_instance.ClientMusicInstance;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.FadenTooltipData;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.fuchsia.network.FadenNetwork;
import net.fuchsia.util.FadenCoreCheckSum;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FadenCoreClient implements ClientModInitializer {

    private static FadenItemModelRegistry ITEM_MODELS = new FadenItemModelRegistry();
    public static ArrayList<String> SPLASHES = new ArrayList<>();

    @Override
    public void onInitializeClient() {
        FadenNetwork.registerS2C();
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            tryToLoadTextures(FadenCoreClothes::load, "CLOTH");
            tryToLoadTextures(ClientRaceSkinCache::add, "RACE SKINS");
            tryToLoadTextures(() -> {
                for (FadenCape cape : FadenCoreCapes.getCapes()) {
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

        /* Other Devs should implement it themselves if they want it */
        HudRenderCallback.EVENT.register(new StatsOverlay());
        HudRenderCallback.EVENT.register(new InstrumentMusicOverlay());

        registerModels();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if(PlayerModelCache.slimModel == null) {
                PlayerModelCache.slimModel = PlayerModelCache.makeSlimModel();
            }
            if(PlayerModelCache.wideModel == null) {
                PlayerModelCache.wideModel = PlayerModelCache.makeWideModel();
            }
        });
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
