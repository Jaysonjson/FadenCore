package net.fuchsia.common.slot;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.StringIdentifiable;

public enum ClothSlot implements StringIdentifiable {

    FEET(3, "feet"),
    LEGS(2, "legs"),
    CHEST(1, "chest"),
    HEAD(0, "head");

    public static final StringIdentifiable.EnumCodec<ClothSlot> CODEC = StringIdentifiable.createCodec(ClothSlot::values);
    private final int entityId;
    private final String name;

    ClothSlot(final int entityId, final String name) {
        this.entityId = entityId;
        this.name = name;
    }

    public int getEntitySlotId() {
        return this.entityId;
    }

    public String getName() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }

    public static ClothSlot byName(String name) {
        ClothSlot equipmentSlot = CODEC.byId(name);
        if (equipmentSlot != null) {
            return equipmentSlot;
        } else {
            throw new IllegalArgumentException("Invalid slot '" + name + "'");
        }
    }

    public static ClothSlot fromEquipment(EquipmentSlot slot) {
        return byName(slot.getName());
    }
}
