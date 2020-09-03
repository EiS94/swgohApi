import com.google.gson.JsonObject;
import mods.Mod;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Char {

    private String name;
    private int galacticPower, level, stars, gear;
    private List<Mod> mods = new LinkedList<>();
    private Map<String, Integer> abilityTiers;
    private List<String> placedGear = new LinkedList<>();

    public static Char getCharFromJsonObject(JsonObject json) {
        String name = json.get("nameKey").getAsString();
        int gp = json.get("gp").getAsInt();
        int level = json.get("level").getAsInt();
        int stars = json.get("rarity").getAsInt();
        int gear = json.get("gear").getAsInt();
        List<Mod> mods = Mod.createModsfromJsonArrayPlayer(json.get("mods").getAsJsonArray());
        return new Char(name, gp, level, stars, gear, mods);
    }

    public Char(String name, int galacticPower, int level, int stars, int gear, List<Mod> mods, Map<String, Integer> abilityTiers, List<String> placedGear) {
        this.name = name;
        this.galacticPower = galacticPower;
        this.level = level;
        this.stars = stars;
        this.gear = gear;
        this.mods = mods;
        this.abilityTiers = abilityTiers;
        this.placedGear = placedGear;
    }

    public Char(String name, int galacticPower, int level, int stars, int gear, Map<String, Integer> abilityTiers) {
        this.name = name;
        this.galacticPower = galacticPower;
        this.level = level;
        this.stars = stars;
        this.gear = gear;
        this.abilityTiers = abilityTiers;
    }

    public Char(String name, int galacticPower, int level, int stars, int gear) {
        this.name = name;
        this.galacticPower = galacticPower;
        this.level = level;
        this.stars = stars;
        this.gear = gear;
    }

    public Char(String name, int galacticPower, int level, int stars, int gear, List<Mod> mods) {
        this.name = name;
        this.galacticPower = galacticPower;
        this.level = level;
        this.stars = stars;
        this.gear = gear;
        this.mods = mods;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGalacticPower(int galacticPower) {
        this.galacticPower = galacticPower;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public void setMods(List<Mod> mods) {
        this.mods = mods;
    }

    public void setAbilityTiers(Map<String, Integer> abilityTiers) {
        this.abilityTiers = abilityTiers;
    }

    public void setPlacedGear(List<String> placedGear) {
        this.placedGear = placedGear;
    }

    public String getName() {
        return name;
    }

    public int getGalacticPower() {
        return galacticPower;
    }

    public int getLevel() {
        return level;
    }

    public int getStars() {
        return stars;
    }

    public int getGear() {
        return gear;
    }

    public List<Mod> getMods() {
        return mods;
    }

    public Map<String, Integer> getAbilityTiers() {
        return abilityTiers;
    }

    public List<String> getPlacedGear() {
        return placedGear;
    }

    @Override
    public String toString() {
        return stars + "* " +  name + ", Level: " + level + ", Gear: " + gear + "\n" + mods;
    }
}
