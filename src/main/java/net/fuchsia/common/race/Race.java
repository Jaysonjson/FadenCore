package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;

import java.util.HashMap;

public enum Race implements IRace {
	
	
	HUMAN, 
	HARENGON,
	TABAXI,
	ELF;

	private HashMap<String, byte[]> skinMap;
	
	Race() {
		skinMap = new HashMap<>();
	}
	
	@Override
	public HashMap<String, byte[]> getSkinMap() {
		return skinMap;
	}

	@Override
	public String getId() {
		return name();
	}

	@Override
	public RaceCosmeticPalette getCosmeticPalette() {
		return new RaceCosmeticPalette();
	}
}
