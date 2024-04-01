package json.jayson.skin.client;

import json.jayson.Faden;
import json.jayson.skin.provider.SkinProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ClientSkinCache {
    private static HashMap<UUID, Identifier> SKINS = new HashMap<>();

    /*
     * Skins from the Folder
     * */
    private static List<byte[]> LOCAL_SKINS = new ArrayList<>();

    public static HashMap<UUID, Identifier> getClientSkins() {
        return SKINS;
    }

    /*
     * I think theres a major flaw with this one, but need to do checks
     * I think I keep binding a new texture, overfloating memory
     * */
    public static void addClientSkin(UUID uuid, SkinTexture skinTexture) {
        // if(!getClientSkins().containsKey(uuid)) {
        Identifier id = SkinProvider.getSkinIdentifier(uuid);
        //MinecraftClient.getInstance().getTextureManager().destroyTexture(id);
        removeClientSkin(uuid);
        MinecraftClient.getInstance().getTextureManager().registerTexture(id, skinTexture);
        MinecraftClient.getInstance().getTextureManager().bindTexture(id);
        getClientSkins().put(uuid, id);
        //  }
    }

    public static void removeClientSkin(UUID uuid) {
        MinecraftClient.getInstance().getTextureManager().destroyTexture(SkinProvider.getSkinIdentifier(uuid));
        getClientSkins().remove(uuid);
    }

    public static List<byte[]> getLocalSkins() {
        return LOCAL_SKINS;
    }

    public static void addLocalSkin(byte[] bytes) {
        getLocalSkins().add(bytes);
    }


    /*
     * in kilobyte
     * */
    public static final float MAX_SKIN_FILE_SIZE = 4.1f;
    public static void retrieveLocalSkins() {
        new Thread(new ClientSkinRetrieveThread()).start();
    }

    public static Path getSkinFolder() {
        return new File(FabricLoader.getInstance().getGameDir().toString() + "/dalekmod/skins/").toPath();
    }




    /*
     * Let the client save their own skins
     * */



    /*
     * Skin that gets saved when you close the game
     * */
    private static NbtCompound SAVED_SKINS = new NbtCompound();
    private static final Path SAVED_SKINS_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/dalekmod/cache/" + Faden.MC_VERSION + "/skin.nbt").toPath();
    /*
     * Can be used if we should ever change the data
     * */
    private static final String SAVED_SKINS_VERSION = "0";
    public static byte[] getSavedSkin(String id) {
        if(SAVED_SKINS.contains(id)) {
            return SAVED_SKINS.getCompound(id).getByteArray("skinData");
        }
        return null;
    }

    public static byte[] getSavedSkin() {
        return getSavedSkin(MinecraftClient.getInstance().getCurrentServerEntry() != null ? MinecraftClient.getInstance().getCurrentServerEntry().address : MinecraftClient.getInstance().getServer().getSavePath(WorldSavePath.ROOT).getParent().getFileName().toString());
    }

    public static void setSavedSkin(byte[] cachedSkin) {
        setSavedSkin(MinecraftClient.getInstance().getCurrentServerEntry() != null ? MinecraftClient.getInstance().getCurrentServerEntry().address : MinecraftClient.getInstance().getServer().getSavePath(WorldSavePath.ROOT).getParent().getFileName().toString(), cachedSkin);
    }

    /**
     * use null to remove
     * */
    public static void setSavedSkin(String id, byte[] cachedSkin) {
        new Thread(() -> {
            if(SAVED_SKINS.contains(id)) SAVED_SKINS.remove(id);
            if(cachedSkin != null) {
                NbtCompound tag = new NbtCompound();
                tag.putByteArray("skinData", cachedSkin);
                SAVED_SKINS.put(id, tag);
                SAVED_SKINS.putString("prot", SAVED_SKINS_VERSION);
            }
            new File(FabricLoader.getInstance().getGameDir().toString() + "/dalekmod/cache/" + Faden.MC_VERSION + "/").mkdirs();
            try {
                NbtIo.writeCompressed(SAVED_SKINS,  SAVED_SKINS_PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void loadSavedSkins() {
        try {
            if(SAVED_SKINS_PATH.toFile().exists()) {
                SAVED_SKINS = NbtIo.readCompressed(SAVED_SKINS_PATH, NbtSizeTracker.ofUnlimitedBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getLocalOrModSkin(ClientPlayerEntity clientPlayerEntity, String modid, ModContainer modContainer) {
        if(modContainer == null) modContainer = Faden.CONTAINER;
        byte[] skinData = null;
        Random random = new Random();
        if(!ClientSkinCache.getLocalSkins().isEmpty()) {
            int index = random.nextInt(ClientSkinCache.getLocalSkins().size());
            skinData = ClientSkinCache.getLocalSkins().get(index);
        } else {
            String skinPath = getSkinPath(clientPlayerEntity, modid);
            Path skins = modContainer.findPath(skinPath).get();
            try {
                Path[] ar = Files.list(skins).toArray(Path[]::new);
                int index = random.nextInt(ar.length);
                InputStream inputStream = Files.newInputStream(ar[index]);
                skinData = SkinProvider.readSkin(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return skinData;
    }

    public static String getSkinPath(ClientPlayerEntity player, String modid) {
        return player.getSkinTextures().model() == SkinTextures.Model.WIDE ? "assets/" + modid + "/textures/regeneration/skins/minecraft/wide/" : "assets/" + modid + "/textures/regeneration/skins/minecraft/slim/";
    }
}

