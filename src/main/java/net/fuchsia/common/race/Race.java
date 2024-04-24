package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import org.joml.Vector3f;

import java.util.HashMap;

public enum Race implements IRace {
	
	
	HUMAN(new RaceCosmeticPalette(), new String[]{"default"}),
	HARENGON(RaceCosmetics.HARENGON, new String[]{"brown", "black", "gold", "salt", "toast", "white", "white_splotched"}, new Vector3f(0.85f, 0.85f, 0.85f), true),
	TABAXI,
	ELF;

	private HashMap<String, byte[]> skinMap;
	private RaceCosmeticPalette palette;
	private String[] subIds;
	private Vector3f size = new Vector3f(1, 1, 1);
	private boolean slim = false;
	Race(RaceCosmeticPalette palette, String[] subIds) {
		skinMap = new HashMap<>();
		this.palette = palette;
		this.subIds = subIds;
	}

	Race(RaceCosmeticPalette palette, String[] subIds, Vector3f size, boolean slim) {
		skinMap = new HashMap<>();
		this.palette = palette;
		this.subIds = subIds;
		this.size = size;
		this.slim = slim;
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

	@Override
	public boolean slim() {
		return slim;
	}
}
