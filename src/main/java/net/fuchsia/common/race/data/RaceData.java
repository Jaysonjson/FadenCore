package net.fuchsia.common.race.data;

import net.fuchsia.common.race.IRace;
import net.fuchsia.common.race.Race;

import java.io.Serializable;

public class RaceData implements Serializable {

    private static final long serialVersionUID = 0L;

    private transient IRace race;
    private String sub_id = "";
    private String race_id = "";
    private String head_cosmetic_id = "";


    public RaceData(IRace race, String sub_id, String head_cosmetic) {
        this.race = race;
        this.sub_id = sub_id;
        if(race != null) {
            this.race_id = race.getId();
        }
        this.head_cosmetic_id = head_cosmetic;
    }

    public IRace getRace() {
        if(race == null) {
            race = Race.valueOf(race_id);
        } else {
            race_id = race.getId();
        }
        return race;
    }

    public String getSubId() {
        return sub_id;
    }

    public String getHeadCosmeticId() {
        return head_cosmetic_id;
    }
}
