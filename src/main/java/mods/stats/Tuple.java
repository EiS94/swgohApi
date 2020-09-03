package mods.stats;

import java.util.List;

public class Tuple {

    private Stat primStat;
    private List<Stat> secStats;

    public Tuple(Stat primStat, List<Stat> secStats) {
        this.primStat = primStat;
        this.secStats = secStats;
    }

    public Stat getPrimStat() {
        return primStat;
    }

    public void setPrimStat(Stat primStat) {
        this.primStat = primStat;
    }

    public List<Stat> getSecStats() {
        return secStats;
    }

    public void setSecStats(List<Stat> secStats) {
        this.secStats = secStats;
    }
}
