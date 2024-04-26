package net.fuchsia.common.quest.data;

import java.util.ArrayList;


/*
* Only tracks ongoing and done quests - faster than NBT
* */
public class PlayerQuests {

    public ArrayList<String> done = new ArrayList<>();
    public ArrayList<String> onGoing = new ArrayList<>();
}
