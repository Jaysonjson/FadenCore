package json.jayson.faden.core.common.init;

import com.mojang.serialization.Codec;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class FadenCoreDataAttachements {

    public static final AttachmentType<Float> MANA = AttachmentRegistry.<Float>builder()
            .persistent(Codec.FLOAT)
            .copyOnDeath()
            .initializer(() -> 10.0f)
            .buildAndRegister(FadenCoreIdentifier.data("mana"));
}
