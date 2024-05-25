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
    public static final DataComponentType<Float> DAMAGE_INCREASE_VALUE = register("extra_damage", (builder) -> builder.codec(Codecs.POSITIVE_FLOAT).packetCodec(PacketCodecs.FLOAT));
    public static final DataComponentType<Float> DAMAGE_INCREASE_PERCENTAGE = register("extra_damage_percentage", (builder) -> builder.codec(Codecs.POSITIVE_FLOAT).packetCodec(PacketCodecs.FLOAT));
    public static final DataComponentType<String> ITEM_TIER = register("item_tier", (builder) -> builder.codec(Codecs.ESCAPED_STRING).packetCodec(PacketCodecs.STRING));
    public static final DataComponentType<Boolean> FREE_WATER_MOVEMENT = register("free_water_movement", (builder) -> builder.codec(FadenCodecs.BOOL).packetCodec(PacketCodecs.BOOL));
    public static final DataComponentType<Float> JUMP_INCREASE_VALUE = register("jump_increase", (builder) -> builder.codec(Codecs.POSITIVE_FLOAT).packetCodec(PacketCodecs.FLOAT));
    public static final DataComponentType<Float> JUMP_INCREASE_PERCENTAGE = register("jump_increase_percentage", (builder) -> builder.codec(Codecs.POSITIVE_FLOAT).packetCodec(PacketCodecs.FLOAT));
    public static final DataComponentType<Float> FALL_DAMAGE_DECREASE_PERCENTAGE = register("falldamage_decrease_percentage", (builder) -> builder.codec(Codecs.POSITIVE_FLOAT).packetCodec(PacketCodecs.FLOAT));

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return (DataComponentType<T>) Registry.register(Registries.DATA_COMPONENT_TYPE, FadenIdentifier.create(id), ((DataComponentType.Builder)builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void register() {

    }

}
