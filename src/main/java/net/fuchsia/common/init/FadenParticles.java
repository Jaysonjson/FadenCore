package net.fuchsia.common.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FadenParticles {

    public static final SimpleParticleType BLOSSOM = registerSimple("blossom");


    public static SimpleParticleType registerSimple(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, FadenIdentifier.create(name), FabricParticleTypes.simple());
    }

}
