package net.fuchsia.common.race;

import java.util.HashMap;

public interface IRace {

	HashMap<String, byte[]> getSkinMap();
	String getId();
}
