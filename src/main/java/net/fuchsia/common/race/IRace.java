package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;

import java.util.HashMap;

public interface IRace {

	HashMap<String, byte[]> getSkinMap();
	String getId();
	RaceCosmeticPalette getCosmeticPalette();
	String[] subIds();
}
