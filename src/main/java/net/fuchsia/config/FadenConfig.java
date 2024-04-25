package net.fuchsia.config;

public class FadenConfig {

	public boolean ENABLE_PLAYER_RACE_SKINS = true;
	public boolean VANILLA_BLUR = true;
	public boolean CUSTOM_CAPES = true;

	public FadenConfig() {
	}

	public FadenConfig(boolean playerRace, boolean vanillaBlur, boolean customCapes) {
		this.ENABLE_PLAYER_RACE_SKINS = playerRace;
		this.VANILLA_BLUR = vanillaBlur;
		this.CUSTOM_CAPES = customCapes;
	}

}
