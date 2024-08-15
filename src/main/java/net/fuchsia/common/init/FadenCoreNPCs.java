package net.fuchsia.common.init;

import net.fuchsia.common.npc.INPC;

import java.util.ArrayList;

public class FadenCoreNPCs {

    private static ArrayList<INPC> NPCS = new ArrayList<>();

    public static INPC register(INPC inpc) {
        NPCS.add(inpc);
        return inpc;
    }

    public static ArrayList<INPC> getNPCS() {
        return NPCS;
    }
}
