import com.google.gson.JsonObject;

public class Ship {

    private int stars, level, gp;
    private String name;

    public Ship(int stars, int level, int gp, String name) {
        this.stars = stars;
        this.level = level;
        this.gp = gp;
        this.name = name;
    }

    public static Ship getShipFromJsonObject(JsonObject json) {
        String name = json.get("defId").getAsString();
        int gp = json.get("gp").getAsInt();
        int level = json.get("level").getAsInt();
        int stars = json.get("rarity").getAsInt();
        return new Ship(stars, level, gp, name);
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return stars + "* " +  name + ", Level: " + level;
    }
}
