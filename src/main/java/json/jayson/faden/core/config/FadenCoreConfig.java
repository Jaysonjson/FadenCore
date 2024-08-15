package json.jayson.faden.core.config;

public class FadenCoreConfig {

	public boolean ENABLE_PLAYER_RACE_SKINS = true;
	public boolean VANILLA_BLUR = true;
	public boolean CUSTOM_CAPES = true;
	public boolean FADEN_HEALTH = true;
	public boolean SHOW_PLAYING_MUSIC = true;

	public FadenCoreConfig() {
	}

	public FadenCoreConfig(boolean playerRace, boolean vanillaBlur, boolean customCapes, boolean fadenHealth, boolean showPlayingMusic) {
		this.ENABLE_PLAYER_RACE_SKINS = playerRace;
		this.VANILLA_BLUR = vanillaBlur;
		this.CUSTOM_CAPES = customCapes;
		this.FADEN_HEALTH = fadenHealth;
		this.SHOW_PLAYING_MUSIC = showPlayingMusic;
	}

}
