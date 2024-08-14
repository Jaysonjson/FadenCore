package json.jayson.faden.core.common.slot;

import net.minecraft.util.StringIdentifiable;

public enum GearSlot implements StringIdentifiable {

    BRACELET(0, 1,"bracelet"),
    BELT(2, 0,"belt"),
    NECKLACE(3, 0, "necklace");

    public static final StringIdentifiable.EnumCodec<GearSlot> CODEC = StringIdentifiable.createCodec(GearSlot::values);
    private final int entityId;
    private final int entityIdEnd;
    private final String name;

    GearSlot(final int entityId, final int entityIdEnd, final String name) {
        this.entityId = entityId;
        this.entityIdEnd = entityIdEnd;
        this.name = name;
    }

    public int getEntitySlotId() {
        return this.entityId;
    }

    public int getEntityIdEnd() {
        return entityIdEnd;
    }

    public String getName() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }

    public static GearSlot byName(String name) {
        GearSlot equipmentSlot = CODEC.byId(name);
        if (equipmentSlot != null) {
            return equipmentSlot;
        } else {
            throw new IllegalArgumentException("Invalid slot '" + name + "'");
        }
    }
}
