package net.fuchsia.common.cape.online;

import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.util.FadenOnlineUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class OnlineCapes {

	private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/online_capes.json").toPath();
	public static OnlineCapeCache ONLINE_CAPES = new OnlineCapeCache();

	public static void retrieve()  {
		if(FadenOptions.getConfig().CUSTOM_CAPES) {
			try {
				File cache = CACHE_PATH.toFile();
				if(!cache.exists()) {
					ONLINE_CAPES = Faden.GSON.fromJson(FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/online_capes.json"), OnlineCapeCache.class);
				} else {
					InputStream inputStream = new FileInputStream(cache);
					ONLINE_CAPES = Faden.GSON.fromJson(new FileReader(cache), OnlineCapeCache.class);
					if(!ONLINE_CAPES.checksum.equals(Faden.CHECKSUMS.ONLINE_CAPES)) {
						ONLINE_CAPES = Faden.GSON.fromJson(FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/online_capes.json"), OnlineCapeCache.class);
					}
				}

				for (OnlineCape onlineCape : ONLINE_CAPES.CAPES) {
					FadenCapes.registerOnlineCape(onlineCape.id).setTextureData(onlineCape.textureData);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



}
