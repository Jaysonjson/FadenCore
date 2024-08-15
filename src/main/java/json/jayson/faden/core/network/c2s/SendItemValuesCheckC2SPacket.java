package json.jayson.faden.core.network.c2s;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.network.FadenCoreNetwork;
import json.jayson.faden.core.util.FadenCoreCheckSum;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import json.jayson.faden.core.util.NetworkUtils;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SendItemValuesCheckC2SPacket(String checksum) implements CustomPayload {

    public static final CustomPayload.Id<SendItemValuesCheckC2SPacket> ID = new CustomPayload.Id<>(FadenCoreIdentifier.create("send_item_values"));
    public static final PacketCodec<RegistryByteBuf, SendItemValuesCheckC2SPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendItemValuesCheckC2SPacket decode(RegistryByteBuf buf) {
            return new SendItemValuesCheckC2SPacket(buf.readString());
        }

        @Override
        public void encode(RegistryByteBuf buf, SendItemValuesCheckC2SPacket value) {
           buf.writeString(value.checksum);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ServerPlayNetworking.Context context) {
        String serverChecksum = FadenCoreCheckSum.checkSum(FadenCore.GSON.toJson(NetworkUtils.trimItemValueMap()));
        if(!checksum.equalsIgnoreCase(serverChecksum)) {
            FadenCore.LOGGER.debug("Updated Item Values for {} due to mismatched Checksum", context.player().getName());
            FadenCoreNetwork.Server.sendItemValues(context.player());
        }
    }
}
