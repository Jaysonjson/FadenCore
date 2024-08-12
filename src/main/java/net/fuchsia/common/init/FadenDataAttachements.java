package net.fuchsia.common.init;

import com.mojang.serialization.Codec;
import net.fuchsia.util.FadenIdentifier;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class FadenDataAttachements {

    public static final AttachmentType<Float> MANA = AttachmentRegistry.<Float>builder()
            .persistent(Codec.FLOAT)
            .copyOnDeath()
            .initializer(() -> 10.0f)
            .buildAndRegister(FadenIdentifier.data("mana"));
}
