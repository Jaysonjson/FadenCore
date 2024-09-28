package json.jayson.faden.core.common.objects.race;

import json.jayson.faden.core.common.race.FadenCoreRace;
import json.jayson.faden.core.common.race.RaceModelType;
import json.jayson.faden.core.common.race.cosmetic.RaceCosmeticPalette;
import org.joml.Vector3f;

public class FadenCoreTestRace extends FadenCoreRace {

    public FadenCoreTestRace() {
        super(new String[]{"default"}, new Vector3f(1, 1, 1));
    }

    @Override
    public RaceCosmeticPalette getCosmeticPalette() {
        return new RaceCosmeticPalette();
    }

    @Override
    public RaceModelType getModelType() {
        return RaceModelType.BOTH;
    }
}
