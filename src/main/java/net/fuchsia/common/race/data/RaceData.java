package net.fuchsia.common.race.data;

import net.fuchsia.common.race.IRace;
import net.fuchsia.common.race.Race;

import java.io.Serial;
import java.io.Serializable;

public class RaceData implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    private transient IRace race;
    private String sub_id = "";
    private String race_id = "";
    private RaceDataCosmetics cosmetics = new RaceDataCosmetics();

    public RaceData() {
        this.race = null;
        this.sub_id = "";
        this.race_id = "";
    }

    public RaceData(IRace race, String sub_id, RaceDataCosmetics cosmetics) {
        this.race = race;
        this.sub_id = sub_id;
        if(race != null) {
            this.race_id = race.getId();
        }
        this.cosmetics = cosmetics;

    }


    public RaceData(String race, String sub_id, RaceDataCosmetics cosmetics) {
        this.sub_id = sub_id;
        this.race_id = race;
        this.race = Race.valueOf(race);
        this.cosmetics = cosmetics;
    }

    public IRace getRace() {
        if(race == null && !race_id.isEmpty()) {
            race = Race.valueOf(race_id);
        } else {
            if(race != null) {
                race_id = race.getId();
            }
        }
        return race;
    }

    public RaceDataCosmetics getCosmetics() {
        return cosmetics;
    }

    public String getSubId() {
        return sub_id;
    }

    public static class RaceDataCosmetics implements Serializable {
        private static final long serialVersionUID = 0L;
        private String head_cosmetic_id = "";
        private String chest_cosmetic_id = "";
        private String leg_cosmetic_id = "";
        private String boots_cosmetic_id = "";

        public RaceDataCosmetics() {

        }

        public RaceDataCosmetics(String head_cosmetic, String chest_cosmetic_id, String leg_cosmetic_id, String boots_cosmetic_id) {
            this.head_cosmetic_id = head_cosmetic;
            this.chest_cosmetic_id = chest_cosmetic_id;
            this.leg_cosmetic_id = leg_cosmetic_id;
            this.boots_cosmetic_id = boots_cosmetic_id;
        }

        public String getHeadCosmeticId() {
            return head_cosmetic_id;
        }

        public String getChestCosmeticId() {
            return chest_cosmetic_id;
        }

        public String getLegCosmeticId() {
            return leg_cosmetic_id;
        }

        public String getBootsCosmeticId() {
            return boots_cosmetic_id;
        }

        public void setBootsCosmeticId(String boots_cosmetic_id) {
            this.boots_cosmetic_id = boots_cosmetic_id;
        }

        public void setChestCosmeticId(String chest_cosmetic_id) {
            this.chest_cosmetic_id = chest_cosmetic_id;
        }

        public void setHeadCosmeticId(String head_cosmetic_id) {
            this.head_cosmetic_id = head_cosmetic_id;
        }

        public void setLegCosmeticId(String leg_cosmetic_id) {
            this.leg_cosmetic_id = leg_cosmetic_id;
        }
    }
}

