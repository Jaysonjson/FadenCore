package net.fuchsia.common.race.types;

import com.google.common.collect.Multimap;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceCosmetics;
import net.fuchsia.common.race.RaceModelType;
import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import org.joml.Vector3f;

public class HarengonRace extends Race {

    public HarengonRace() {
        super(FadenIdentifier.create("harengon"), new String[]{"brown", "black", "gold", "salt", "toast", "white", "white_splotched"}, new Vector3f(0.77f, 0.72f, 0.77f));
    }

    @Override
    public RaceCosmeticPalette getCosmeticPalette() {
        return RaceCosmetics.HARENGON;
    }

    @Override
    public RaceModelType model() {
        return RaceModelType.SLIM;
    }

    @Override
    protected void addEntityAttributes(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers) {
        modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(FadenIdentifier.create("speed"), 0.03, EntityAttributeModifier.Operation.ADD_VALUE));
        modifiers.put(EntityAttributes.GENERIC_JUMP_STRENGTH, new EntityAttributeModifier(FadenIdentifier.create("jump_strength"), 0.25, EntityAttributeModifier.Operation.ADD_VALUE));
        modifiers.put(EntityAttributes.GENERIC_LUCK, new EntityAttributeModifier(FadenIdentifier.create("luck"), 0.2, EntityAttributeModifier.Operation.ADD_VALUE));
        modifiers.put(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, new EntityAttributeModifier(FadenIdentifier.create("fall_distance"), 3.6, EntityAttributeModifier.Operation.ADD_VALUE));
        modifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(FadenIdentifier.create("max_health"), -8, EntityAttributeModifier.Operation.ADD_VALUE));
    }
}
