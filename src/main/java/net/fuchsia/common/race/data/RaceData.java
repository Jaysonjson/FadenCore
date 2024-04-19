package net.fuchsia.common.race.data;

import net.fuchsia.common.race.IRace;

public class RaceData {

    private IRace race;
    private String sub_id = "";

    public RaceData(IRace race, String sub_id) {
        this.race = race;
        this.sub_id = sub_id;
    }

    public IRace getRace() {
        return race;
    }

    public String getSubId() {
        return sub_id;
    }
}
