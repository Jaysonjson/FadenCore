package net.fuchsia.common.cape;

import java.util.ArrayList;
import java.util.UUID;

import net.fuchsia.config.FadenCoreOptions;
import net.fuchsia.server.PlayerData;
import net.fuchsia.util.PlayerDataUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class FadenCoreCapes {

    private static ArrayList<FadenCape> CAPES = new ArrayList<>();

    public static FadenCape register(Identifier id) {
        FadenCape cape = new FadenCape(id, Text.translatable("cape." + id.getNamespace() + "." + id.getPath()), Text.translatable("cape." + id.getNamespace() + "." + id.getPath() + ".description"));
        CAPES.add(cape);
        return cape;
    }

    public static FadenCape registerOnlineCape(Identifier id) {
        FadenCape cape = new FadenCape(id, Text.literal(id.getPath()), Text.literal(id.getPath()));
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
            if(cape.getId().toString().equalsIgnoreCase(name)) {
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
