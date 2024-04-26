package net.fuchsia.common.quest.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


/*
* Only tracks ongoing and done quests - faster than NBT
* */
public class PlayerQuests {

    //WHOOPS NEED TO UPDATE THIS LATER; THIS ONLY WORKS FOR 1 PLAYERL MAO
    public HashMap<UUID, ArrayList<String>> done = new HashMap<>();
    public HashMap<UUID, ArrayList<String>> onGoing = new HashMap<>();
}
