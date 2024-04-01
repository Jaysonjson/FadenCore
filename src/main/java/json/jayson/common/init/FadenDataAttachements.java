package json.jayson.common.init;

import com.mojang.serialization.Codec;
import json.jayson.util.FadenIdentifier;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class FadenDataAttachements {

    public static final AttachmentType<Float> MANA = AttachmentRegistry.<Float>builder()
            .persistent(Codec.FLOAT)
            .copyOnDeath()
            .initializer(() -> 10.0f)
            .buildAndRegister(FadenIdentifier.data("mana"));

    public static final AttachmentType<Integer> SOULS = AttachmentRegistry.<Integer>builder()
            .persistent(Codec.INT)
            .initializer(() -> 0)
            .buildAndRegister(FadenIdentifier.data("souls"));

}
