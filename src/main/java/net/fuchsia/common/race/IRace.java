package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import org.joml.Vector3f;

import java.util.HashMap;

public interface IRace {

	HashMap<String, byte[]> getSkinMap();
	String getId();
	RaceCosmeticPalette getCosmeticPalette();
	String[] subIds();
	Vector3f size();
	RaceModelType model();
}
