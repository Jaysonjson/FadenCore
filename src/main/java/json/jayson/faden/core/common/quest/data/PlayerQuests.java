package json.jayson.faden.core.common.quest.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


/*
* Only tracks ongoing and done quests - faster than NBT
* */
public class PlayerQuests {

    public HashMap<UUID, ArrayList<String>> done = new HashMap<>();
    public HashMap<UUID, ArrayList<String>> onGoing = new HashMap<>();

    public boolean newQuestForPlayer(UUID uuid, FadenCoreQuest quest) {
        ArrayList<String> dof = done.getOrDefault(uuid, new ArrayList<>());
        ArrayList<String> ong = onGoing.getOrDefault(uuid, new ArrayList<>());
        return !dof.contains(quest.getIdentifier().toString()) && !ong.contains(quest.getIdentifier().toString());
    }
}
