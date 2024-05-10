package net.fuchsia.network.s2c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.reflect.TypeToken;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.Faden;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record ReloadServerJSONS2CPacket(String type, String json) implements CustomPayload {

    public static final CustomPayload.Id<ReloadServerJSONS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("reload"));
    public static final PacketCodec<RegistryByteBuf, ReloadServerJSONS2CPacket> CODEC =new PacketCodec<RegistryByteBuf, ReloadServerJSONS2CPacket>() {
        @Override
        public ReloadServerJSONS2CPacket decode(RegistryByteBuf buf) {
            return new ReloadServerJSONS2CPacket(buf.readString(), buf.readString());
        }

        @Override
        public void encode(RegistryByteBuf buf, ReloadServerJSONS2CPacket value) {
            buf.writeString(value.type);
            buf.writeString(value.json);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        switch (type) {
            case "cape": {
                FadenCapes.setPlayerCapes(Faden.GSON.fromJson(json, new TypeToken<HashMap<UUID, ArrayList<String>>>(){}.getType()));
                break;
            }

            case "item_values": {
                ItemValues.reload(Faden.GSON.fromJson(json, new TypeToken<HashMap<String, Integer>>(){}.getType()), json);
                break;
            }
        }
    }
}
