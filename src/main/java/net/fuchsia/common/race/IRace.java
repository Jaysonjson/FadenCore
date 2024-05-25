package net.fuchsia.common.race;

import java.util.HashMap;

import org.joml.Vector3f;

import com.google.common.collect.ImmutableMap;

import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import net.minecraft.entity.EntityDimensions;

public interface IRace {

	HashMap<String, byte[]> getSkinMap();
	String getId();
	RaceCosmeticPalette getCosmeticPalette();
	String[] subIds();
	Vector3f size();
	RaceModelType model();
	EntityDimensions dimensions();
	ImmutableMap<Object, Object> poseDimensions();
}
