package json.jayson.skin.provider;

import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import json.jayson.Faden;
import json.jayson.network.FadenNetwork;
import json.jayson.skin.client.ClientSkinCache;
import json.jayson.skin.server.ServerSkinCache;
import json.jayson.util.FadenIdentifier;
import json.jayson.util.FadenNBT;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

public class SkinProvider {

    public static URL retrieveOnlineSkin(GameProfile gameProfile) throws MalformedURLException {
        for (Property textures : gameProfile.getProperties().get("textures")) {
            String value = textures.value();
            String skinJson = new String(Base64.getDecoder().decode(value));
            SkinData skinData = new Gson().fromJson(skinJson, SkinData.class);
            return new URL(skinData.textures.SKIN.url);
        }
        return null;
    }


    public static byte[] getOnlineSkinBytes(GameProfile gameProfile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(retrieveOnlineSkin(gameProfile));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return bytes;
    }

    /*
     * Use random skin
     * Needs to be called on SERVER and CLIENT
     * */
    public static void randomSkin(PlayerEntity player, String modid, ModContainer container) {
        if(player.getWorld().isClient()) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) player;
            byte[] skinData = ClientSkinCache.getLocalOrModSkin(clientPlayerEntity, modid, container);
            if(skinData != null) {
                FadenNetwork.Client.sendSingletonSkin(player.getUuid(), skinData);
                ClientSkinCache.setSavedSkin(skinData);
            }
        } else {
            ServerSkinCache.getServerSkinCache().removeIf(serverSkin -> serverSkin.uuid.equals(player.getUuid()));
        }
    }

    public static byte[] readSkin(File file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return bytes;
    }


    public static byte[] readSkin(InputStream inputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return bytes;
    }

    /*
     * Taken from my Medusa Mod, could still be used for Angels?
     * - Jayson
     * */
    public static byte[] stonifySkin(URL url) {
        try {
            BufferedImage bufferedImage = ImageIO.read(url);
            Path texturePath = Faden.CONTAINER.findPath("assets/dalekmod/textures/stone_overlay.png").get();
            InputStream inputStream = Files.newInputStream(texturePath);
            BufferedImage stoneOverlay = ImageIO.read(inputStream);
            for (int i = 0; i < bufferedImage.getWidth(); i++) {
                for (int j = 0; j < bufferedImage.getHeight(); j++) {
                    int currentRgb = bufferedImage.getRGB(i,j);
                    Color color = new Color(currentRgb);
                    int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    int finalized = (currentRgb & 0xff000000) | (gray << 16) | (gray << 8) | gray;
                    bufferedImage.setRGB(i, j, finalized);
                }
            }
            Graphics2D g = bufferedImage.createGraphics();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f));
            g.drawImage(stoneOverlay, (bufferedImage.getWidth() - stoneOverlay.getWidth()) / 2, (bufferedImage.getHeight() - stoneOverlay.getHeight()) / 2, null);
            g.dispose();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Identifier getSkinIdentifier(UUID uuid) {
        return FadenIdentifier.create("skin/" + uuid.toString().replaceAll("-",""));
    }

    public static class SkinData {

        public Texture textures;

        public class Texture {
            public Skin SKIN;
        }

        public class Skin {
            public String url;
        }

    }

}
