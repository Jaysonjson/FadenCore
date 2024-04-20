package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import org.joml.Vector3f;

import java.util.HashMap;

public enum Race implements IRace {
	
	
	HUMAN, 
	HARENGON(RaceCosmetics.HARENGON, new String[]{"brown", "black", "gold", "salt", "toast", "white", "white_splotched"}, new Vector3f(0.85f, 0.85f, 0.85f)),
	TABAXI,
	ELF;

	private HashMap<String, byte[]> skinMap;
	private RaceCosmeticPalette palette;
	private String[] subIds;
	private Vector3f size = new Vector3f(1, 1, 1);
	Race(RaceCosmeticPalette palette, String[] subIds) {
		skinMap = new HashMap<>();
		this.palette = palette;
		this.subIds = subIds;
	}

	Race(RaceCosmeticPalette palette, String[] subIds, Vector3f size) {
		skinMap = new HashMap<>();
		this.palette = palette;
		this.subIds = subIds;
		this.size = size;
	}

	Race() {
		skinMap = new HashMap<>();
		this.palette = new RaceCosmeticPalette();
		this.subIds = new String[]{};
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
		return palette;
	}

	@Override
	public String[] subIds() {
		return subIds;
	}

	@Override
	public Vector3f size() {
		return size;
	}
}
