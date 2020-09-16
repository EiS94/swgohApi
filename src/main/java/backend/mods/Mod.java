package backend.mods;


import backend.Place;
import backend.Tuple;
import backend.Type;
import backend.mods.stats.Stat;
import backend.mods.stats.StatType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

public class Mod {

    /**
     * @param id = own id
     * @param set = Type of backend.mods, 1= Health, 4 = Speed, ... TODO
     * @param level = modLevel
     * @param pips = color: white = 1, green = 2, blue = 3, purple = 4, gold = 5
     * @param tier = "seltenheit" 1-6
     * @param stats:
     * [x,y,z] x,z = 0,1,2,...  y = 0.000,0.001,...
     * x = tpye of modifier
     * y = value of modifiing
     * z = level of modifier
     */

    private Type type;
    private Place place;
    private int level, color, tier;
    private Stat primStat;
    private List<Stat> secStats;

    public Mod(Type type, Place place, int level, int color, int tier, Stat primStat, List<Stat> secStats) {
        this.type = type;
        this.place = place;
        this.level = level;
        this.color = color;
        this.tier = tier;
        this.primStat = primStat;
        this.secStats = secStats;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(level).append(" - ").append(" ").append(color).append(" ").append(place.toString()).append("\n");
        sb.append("PRIMÄRWERT\n");
        sb.append(primStat.toString()).append("\n");
        sb.append("SEKUNDÄRWERTE\n");
        for (Stat stat : secStats) {
            sb.append(stat.toString()).append("\n");
        }
        return sb.toString();
    }

    public static List<Mod> createModsfromJsonArrayPlayer(JsonArray json) {
        List<Mod> mods = new LinkedList<>();
        int counter = 0;
        for (JsonElement jsonElement : json) {
            mods.add(createModfromJsonElementPlayer(jsonElement.getAsJsonObject(), counter++));
        }
        return mods;
    }

    private static Mod createModfromJsonElementPlayer(JsonObject json, int counter) {
        if (json.get("primaryStat") == null) return null;
        JsonObject jo = json.get("primaryStat").getAsJsonObject();
        Stat prime = new Stat(getStatType(jo.get("unitStat").getAsInt()), jo.get("value").getAsDouble(),
                0, true);
        Object o = json.get("secondaryStat");
        return new Mod(null, Place.values()[counter], json.get("level").getAsInt(), json.get("pips").getAsInt(),
                json.get("tier").getAsInt(), prime, createStatsfromJsonArrayPlayer(o));

    }

    private static List<Stat> createStatsfromJsonArrayPlayer(Object json) {
        JsonArray j = (JsonArray) json;
        List<Stat> secStats = new LinkedList<>();
        for (JsonElement e : j) {
            JsonObject o = (JsonObject) e;
            secStats.add(new Stat(getStatType(o.get("unitStat").getAsInt()), o.get("value").getAsDouble(),
                    o.get("roll").getAsInt(), false));
        }
        return secStats;
    }


    public static List<Mod> createModsfromJsonArray(JsonArray json) {
        List<Mod> mods = new LinkedList<>();
        int counter = 0;
        for (JsonElement jsonElement : json) {
            mods.add(createModfromJsonElement(jsonElement.getAsJsonObject(), counter++));
        }
        return mods;
    }

    private static Mod createModfromJsonElement(JsonObject json, int counter) {
        if (json.get("stat") != null) {
            Tuple<Stat, List<Stat>> t = createStatsfromJsonArray(json.get("stat").getAsJsonArray());
            return new Mod(null, Place.values()[counter], json.get("level").getAsInt(), json.get("pips").getAsInt(),
                    json.get("tier").getAsInt(), t.getFirstValue(), t.getSecondValue());
        } else return null;
    }

    private static Tuple<Stat, List<Stat>> createStatsfromJsonArray(JsonArray json) {
        JsonElement e = json.get(0);
        Stat primtStat = new Stat(getStatType(e.getAsJsonArray().get(0).getAsInt()),
                e.getAsJsonArray().get(1).getAsDouble(), e.getAsJsonArray().get(2).getAsInt(), true);
        List<Stat> secStats = new LinkedList<>();
        for (int i = 1; i < json.size(); i++) {
            e = json.get(i);
            secStats.add(new Stat(getStatType(e.getAsJsonArray().get(0).getAsInt()),
                    e.getAsJsonArray().get(1).getAsDouble(), e.getAsJsonArray().get(2).getAsInt(), false));
        }
        return new Tuple<>(primtStat, secStats);
    }

    private static StatType getStatType(int value) {
        /**
         * @param 48 = % Angriff
         * @param 55 = % Gesundheit
         * @param 56 = % Schutz
         * @param 28 = Schutz
         * @param  1 = Gesundheit
         * @param  5 = Tempo
         * @param 41 = Angriff
         * @param 49 = % Abwehr
         * @param 18 = % Zaehigkeit
         * @param 17 = Effektifitaet
         * @param 53 = % krit-Treffer-Chance
         * @param 42 = Abwehr
         * @param 48 = Angriff
         *
         */
        switch (value) {
            case 48:
                return StatType.OFFENSEPROD;
            case 55:
                return StatType.HEALTHPROD;
            case 56:
                return StatType.PROTECTIONPROD;
            case 28:
                return StatType.PROTECTION;
            case 1:
                return StatType.HEALTH;
            case 5:
                return StatType.SPEED;
            case 41:
                return StatType.OFFENSE;
            case 49:
                return StatType.DEFENSEPROD;
            case 18:
                return StatType.TENACITY;
            case 17:
                return StatType.EFFECT;
            case 53:
                return StatType.CRIDHITCHAN;
            case 42:
                return StatType.DEFENSE;
            default:
                return StatType.CRITDAM;
        }
    }

}
