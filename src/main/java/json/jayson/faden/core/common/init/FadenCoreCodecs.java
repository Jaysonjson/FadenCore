package json.jayson.faden.core.common.init;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import json.jayson.faden.core.common.objects.codecs.PlayerStatsCodec;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.Supplier;

public class FadenCoreCodecs {

    public static final Supplier<Codec<PlayerStatsCodec>> PLAYER_STATS = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> inst.group(
                    Codecs.POSITIVE_INT.optionalFieldOf("vitality", 1).forGetter(PlayerStatsCodec::getVitality))
            .and(Codecs.POSITIVE_INT.optionalFieldOf("strength", 1).forGetter(PlayerStatsCodec::getStrength))
            .apply(inst, PlayerStatsCodec::new)));

    public static final Codec<Boolean> BOOL = Codec.BOOL.validate(DataResult::success);
}
