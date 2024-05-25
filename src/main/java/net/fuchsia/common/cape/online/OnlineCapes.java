package net.fuchsia.common.cape.online;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.util.FadenOnlineUtil;

import java.io.*;
import java.nio.file.Path;

public class OnlineCapes {

	private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/online_capes.json").toPath();
	public static OnlineCapeCache ONLINE_CAPES = new OnlineCapeCache();

	public static void retrieve()  {
		if(FadenOptions.getConfig().CUSTOM_CAPES) {
			ONLINE_CAPES = Faden.GSON.fromJson(FadenOnlineUtil.getJSONDataOrCache("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/online_capes.json", CACHE_PATH.toFile(), Faden.CHECKSUMS.online_capes), OnlineCapeCache.class);
			for (OnlineCape onlineCape : ONLINE_CAPES.CAPES) {
				FadenCapes.registerOnlineCape(onlineCape.id).setTextureData(onlineCape.textureData);
			}
		}
	}



}
