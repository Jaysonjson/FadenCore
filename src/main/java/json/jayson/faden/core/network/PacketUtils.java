package json.jayson.faden.core.network;

import json.jayson.faden.core.FadenCore;
import net.minecraft.network.RegistryByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class PacketUtils {

    public static <T> void writeByteData(T data, RegistryByteBuf buf) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(data);
            byte[] bytes = byteOut.toByteArray();
            byteOut.close();
            out.close();
            buf.writeString(new String(Base64.getEncoder().encode(bytes)));
        } catch (Exception e) {
            FadenCore.LOGGER.error("Could not encode Packet - {}", e.getMessage());
            e.printStackTrace();
        }
    }


    public static <T> T readByteData(RegistryByteBuf buf) {
        T t = null;
        try {
            ByteArrayInputStream byteOut = new ByteArrayInputStream(Base64.getDecoder().decode(buf.readString()));
            ObjectInputStream out = new ObjectInputStream(byteOut);
            t = (T) out.readObject();
            byteOut.close();
            out.close();
        } catch (Exception e) {
            FadenCore.LOGGER.error("Could not decode Packet - {}", e.getMessage());
            e.printStackTrace();
        }
        return t;
    }


}
