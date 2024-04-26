package net.fuchsia.common.quest.data;

import java.util.ArrayList;


/*
* Only tracks ongoing and done quests - faster than NBT
* */
public class PlayerQuests {

    //WHOOPS NEED TO UPDATE THIS LATER; THIS ONLY WORKS FOR 1 PLAYERL MAO
    public ArrayList<String> done = new ArrayList<>();
    public ArrayList<String> onGoing = new ArrayList<>();
}
