package net.fuchsia.common.cape.online;

import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.util.FadenCheckSum;
import net.fuchsia.util.FadenOnlineUtil;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class OnlineCapes {

	private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/online_capes.json").toPath();
	public static OnlineCapeCache ONLINE_CAPES = new OnlineCapeCache();

	public static void retrieve()  {
		if(FadenOptions.getConfig().CUSTOM_CAPES) {
			try {
				File cache = CACHE_PATH.toFile();
				if(!cache.exists()) {
					ONLINE_CAPES = Faden.GSON.fromJson(FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/online_capes.json"), OnlineCapeCache.class);
					FileWriter writer = new FileWriter(cache);
					writer.write(Faden.GSON.toJson(ONLINE_CAPES));
					writer.close();
				} else {
					InputStream inputStream = new FileInputStream(cache);
					ONLINE_CAPES = Faden.GSON.fromJson(new FileReader(cache), OnlineCapeCache.class);
					if(!FadenCheckSum.checkSum(inputStream).equals(Faden.CHECKSUMS.online_capes)) {
						Faden.LOGGER.warn("Mismatched Checksum for " + cache + " - retrieving data again");
						ONLINE_CAPES = Faden.GSON.fromJson(FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/online_capes.json"), OnlineCapeCache.class);
					}
					FileWriter writer = new FileWriter(cache);
					writer.write(Faden.GSON.toJson(ONLINE_CAPES));
					writer.close();
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
