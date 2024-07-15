package net.fuchsia.network;

import net.fuchsia.Faden;
import net.fuchsia.network.s2c.RacePacket;
import net.fuchsia.network.s2c.SendRaceUpdateS2CPacket;
import net.minecraft.network.RegistryByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
            Faden.LOGGER.error("Could not encode Packet - {}", e.getMessage());
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
            Faden.LOGGER.error("Could not decode Packet - {}", e.getMessage());
        }
        return t;
    }


}
