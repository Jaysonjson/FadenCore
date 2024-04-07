package net.fuchsia.race;

import java.util.HashMap;

public enum Race implements IRace {
	
	
	HUMAN, 
	RABBIT, 
	ELF;

	private HashMap<String, byte[]> skinMap;
	
	Race() {
		skinMap = new HashMap<>();
	}
	
	@Override
	public HashMap<String, byte[]> getSkinMap() {
		return skinMap;
	}
}
