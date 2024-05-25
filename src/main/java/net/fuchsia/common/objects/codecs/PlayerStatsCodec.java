package net.fuchsia.common.objects.codecs;

public class PlayerStatsCodec {

    private final int vitality;
    private final int strength;
    public PlayerStatsCodec(int vitality, int strength) {
        this.vitality = vitality;
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public int getVitality() {
        return vitality;
    }

}
