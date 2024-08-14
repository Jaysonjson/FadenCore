package json.jayson.faden.core.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import json.jayson.faden.core.client.ClientMusicInstances;
import json.jayson.faden.core.client.overlay.InstrumentMusicOverlay;
import json.jayson.faden.core.common.objects.music_instance.ClientMusicInstance;
import json.jayson.faden.core.common.objects.music_instance.MusicInstance;
import json.jayson.faden.core.network.PacketUtils;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;

public record SendMusicInstanceS2CPacket(MusicInstance musicInstance) implements CustomPayload {

    public static final CustomPayload.Id<SendMusicInstanceS2CPacket> ID = new CustomPayload.Id<>(FadenCoreIdentifier.create("music_instance"));
    public static final PacketCodec<RegistryByteBuf, SendMusicInstanceS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendMusicInstanceS2CPacket decode(RegistryByteBuf buf) {
            return new SendMusicInstanceS2CPacket(PacketUtils.readByteData(buf));
        }

        @Override
        public void encode(RegistryByteBuf buf, SendMusicInstanceS2CPacket value) {
            PacketUtils.writeByteData(value.musicInstance, buf);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        if(musicInstance != null) {
            ClientMusicInstance clientMusicInstance = ClientMusicInstances.getInstances().getOrDefault(musicInstance.getUuid(), new ClientMusicInstance());
            clientMusicInstance.setInstance(musicInstance);
            ClientMusicInstances.getInstances().put(musicInstance.getUuid(), clientMusicInstance);
            if(!clientMusicInstance.playing) {
                clientMusicInstance.startPlaying();
            }
            if(50 >= context.player().getPos().distanceTo(new Vec3d(clientMusicInstance.getInstance().getPosition().x, clientMusicInstance.getInstance().getPosition().y, clientMusicInstance.getInstance().getPosition().z))) {
                InstrumentMusicOverlay.CLIENT_MUSIC_INSTANCE = clientMusicInstance;
                InstrumentMusicOverlay.SHOW_TICKS = 0;
            }
        }
    }
}