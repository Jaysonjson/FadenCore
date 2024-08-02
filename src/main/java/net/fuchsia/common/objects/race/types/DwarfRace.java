package net.fuchsia.common.objects.race.types;

import com.google.common.collect.Multimap;
import net.fuchsia.common.objects.race.Race;
import net.fuchsia.common.objects.race.RaceModelType;
import net.fuchsia.common.objects.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import org.joml.Vector3f;

public class DwarfRace extends Race {

    public DwarfRace() {
        super(FadenIdentifier.create("dwarf"), new String[]{"default"}, new Vector3f(1, 0.65f, 1));
    }

    @Override
    public RaceCosmeticPalette getCosmeticPalette() {
        return new RaceCosmeticPalette();
    }

    @Override
    public RaceModelType model() {
        return RaceModelType.WIDE;
    }

    @Override
    protected void addEntityAttributes(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers) {
        modifiers.put(EntityAttributes.PLAYER_MINING_EFFICIENCY, new EntityAttributeModifier(FadenIdentifier.create("health"), 4, EntityAttributeModifier.Operation.ADD_VALUE));
    }

}
