package net.fuchsia;

import net.fuchsia.util.FadenIdentifier;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;

@Deprecated
public class ReMapper {

    public static HashMap<String, String> REMAPPINGS = new HashMap<>();

    public static void register() {
        add("granite_tiles_slab", "granite_tile_slab");
        add("granite_tiles_wall", "granite_tile_wall");
        add("granite_tiles_stairs", "granite_tile_stairs");
        add("granite_tiles_button", "granite_tile_button");
        add("granite_tiles_pressure_plate", "granite_tile_pressure_plate");

        add("granud_brick_slab", "granud_tile_slab");
        add("granud_brick_wall", "granud_tile_wall");
        add("granud_brick_stairs", "granud_tile_stairs");
        add("granud_brick_button", "granud_tile_button");
        add("granud_brick_pressure_plate", "granud_tile_pressure_plate");
    }

    protected static void add(String old, String ne) {
        REMAPPINGS.put("faden:" + old, "faden:" + ne);
    }

}
