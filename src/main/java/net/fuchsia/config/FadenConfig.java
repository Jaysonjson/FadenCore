package net.fuchsia.config;

public class FadenConfig {

	public boolean ENABLE_PLAYER_RACE_SKINS = true;
	public boolean VANILLA_BLUR = true;

	public FadenConfig() {
	}

	public FadenConfig(boolean playerRace, boolean vanillaBlur) {
		this.ENABLE_PLAYER_RACE_SKINS = playerRace;
		this.VANILLA_BLUR = vanillaBlur;
	}

}
