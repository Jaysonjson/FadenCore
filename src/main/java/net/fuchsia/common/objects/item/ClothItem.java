package net.fuchsia.common.objects.item;

import net.fuchsia.util.FadenIdentifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ClothItem extends Item implements Equipment {
    private String file;
    private Identifier texture;
    public ClothItem(Settings settings, String file) {
        super(settings);
        this.file = file;
        this.texture = FadenIdentifier.create("cloth/" + file);
    }

    public String getFile() {
        return file;
    }

    public Identifier getTexture() {
        return texture;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }
}
