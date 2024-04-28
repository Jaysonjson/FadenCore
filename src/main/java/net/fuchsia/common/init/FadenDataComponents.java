package net.fuchsia.common.init;

import net.fuchsia.util.FadenIdentifier;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class FadenDataComponents {

    public static final DataComponentType<Integer> EXTRA_VALUE = register("extra_value", (builder) -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.VAR_INT));

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return (DataComponentType) Registry.register(Registries.DATA_COMPONENT_TYPE, FadenIdentifier.create(id), ((DataComponentType.Builder)builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void init() {

    }

}
