package json.jayson.faden.core.server;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import json.jayson.faden.core.common.race.Race;
import json.jayson.faden.core.network.FadenCoreNetwork;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import json.jayson.faden.core.common.cape.FadenCoreCape;

public class PlayerData implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;


    private String selectedCape = "";
    private ArrayList<String> capes = new ArrayList<>();
    private RaceSaveData race = new RaceSaveData();
    private UUID uuid;

    public RaceSaveData getRaceSaveData() {
        if(race == null) race = new RaceSaveData();
        return race;
    }

    public void resetRaceData() {
        race = new RaceSaveData();
    }

    public String getSelectedCapeId() {
        return selectedCape;
    }

    public void addCape(Identifier cape) {
        if(!getCapes().contains(cape.toString())) {
            getCapes().add(cape.toString());
        }
    }

    public void removeCape(Identifier cape) {
        getCapes().remove(cape.toString());
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ArrayList<String> getCapes() {
        return capes;
    }

    @Nullable
    public FadenCoreCape getSelectedCape() {
        for (FadenCoreCape cape : FadenCoreRegistry.CAPE) {
            if(cape.getIdentifier().toString().equalsIgnoreCase(selectedCape)) {
                return cape;
            }
        }
        return null;
    }

    public void setSelectedCape(FadenCoreCape selectedCape) {
        this.selectedCape = selectedCape.getIdentifier().toString();
    }

    public void setSelectedCape(String selectedCape) {
        this.selectedCape = selectedCape;
    }

    public void sync() {
        if(ServerPlayerDatas.SERVER != null) {
            for (ServerPlayerEntity serverPlayerEntity : ServerPlayerDatas.SERVER.getPlayerManager().getPlayerList()) {
                FadenCoreNetwork.Server.syncPlayerData(serverPlayerEntity, getUuid(), this);
            }
        }
    }

    public static class RaceSaveData implements Serializable {
        @Serial
        private static final long serialVersionUID = 0L;

        private transient Race raceObj;
        private String skin = "";
        private String race = "";
        private String raceSub = "";
        private RaceDataCosmetics cosmetics = new RaceDataCosmetics();

        public boolean hasRace() {
            return getRace() != null;
        }

        public RaceDataCosmetics getCosmetics() {
            return cosmetics;
        }

        public String getRaceSub() {
            return raceSub;
        }

        public void setRaceSub(String raceSub) {
            this.raceSub = raceSub;
        }

        public void setCosmetics(RaceDataCosmetics cosmetics) {
            this.cosmetics = cosmetics;
        }

        @Nullable
        public Race getRace() {
            if(raceObj == null) {
                raceObj = FadenCoreRegistry.getRace(Identifier.of(race));
            }
            return raceObj;
        }

        public String getRaceId() {
            return race;
        }

        public void setRace(String race) {
            this.race = race;
        }

        public String getSkin() {
            return skin;
        }

        public void setSkin(String skin) {
            this.skin = skin;
        }
    }

    public static class RaceDataCosmetics implements Serializable {
        private static final long serialVersionUID = 0L;
        private ArrayList<String> head = new ArrayList<>();
        private ArrayList<String> chest = new ArrayList<>();
        private ArrayList<String> leg = new ArrayList<>();
        private ArrayList<String> boots = new ArrayList<>();

        public RaceDataCosmetics() {

        }

        public ArrayList<String> getHead() {
            return head;
        }

        public ArrayList<String> getBoots() {
            return boots;
        }

        public ArrayList<String> getChest() {
            return chest;
        }

        public ArrayList<String> getLeg() {
            return leg;
        }

        public void setBoots(ArrayList<String> boots) {
            this.boots = boots;
        }

        public void setChest(ArrayList<String> chest) {
            this.chest = chest;
        }

        public void setHead(ArrayList<String> head) {
            this.head = head;
        }

        public void setLeg(ArrayList<String> leg) {
            this.leg = leg;
        }
    }
}
