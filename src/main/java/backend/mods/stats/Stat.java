package backend.mods.stats;

import java.text.DecimalFormat;

public class Stat {

    private StatType statType;
    private double value;
    private int level;
    private boolean primary;

    public Stat(StatType statType, double value, int level, boolean primary) {
        this.statType = statType;
        this.value = value;
        this.level = level;
        this.primary = primary;
    }

    public StatType getStatType() {
        return statType;
    }

    public void setStatType(StatType statType) {
        this.statType = statType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("####.##");
        if (!primary) return "(" + level + ") +" + df.format(value) + " " + statType.toString();
        else return "+" + df.format(value) + " " + statType.toString();
    }
}
