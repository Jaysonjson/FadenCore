package net.fuchsia.common.cape;

import java.util.ArrayList;
import java.util.UUID;

import net.fuchsia.config.FadenCoreOptions;
import net.fuchsia.server.PlayerData;
import net.fuchsia.util.PlayerDataUtil;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class FadenCoreCapes {

    private static ArrayList<FadenCape> CAPES = new ArrayList<>();

    public static FadenCape register(String id) {
        FadenCape cape = new FadenCape(id, Text.translatable("cape.faden." + id), Text.translatable("cape.faden." + id + ".description"));
        CAPES.add(cape);
        return cape;
    }

    public static FadenCape registerOnlineCape(String id) {
        FadenCape cape = new FadenCape(id, Text.literal(id), Text.literal(id));
        CAPES.add(cape);
        return cape;
    }

    public static ArrayList<FadenCape> getCapes() {
        return CAPES;
    }


    @Nullable
    public static FadenCape getCapeForPlayer(UUID uuid) {
        PlayerData data = PlayerDataUtil.getClientOrServer(uuid);
        if(data != null) {
            return data.getSelectedCape();
        }
        return null;
    }

    public static FadenCape getCapeById(String name) {
        for (FadenCape cape : getCapes()) {
            if(cape.getId().equalsIgnoreCase(name)) {
                return cape;
            }
        }
        return null;
    }

    public static boolean playerHasCape(UUID uuid) {
        PlayerData data = PlayerDataUtil.getClientOrServer(uuid);
        return data != null && !data.getSelectedCapeId().isEmpty();
    }

}
