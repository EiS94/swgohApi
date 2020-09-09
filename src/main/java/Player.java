import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import help.swgoh.api.SwgohAPI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Player {

    private int allyCode, level, grandArenaLifeTimePoints;
    private String name, guildName;
    private List<Char> characters;
    private List<Ship> ships;
    private Squat arenaSquat;

    public static Player getPlayer(SwgohAPI api, int allyCode) throws ExecutionException, InterruptedException {
        JsonParser parser = new JsonParser();
        JsonElement e = parser.parse(api.getPlayer(allyCode).get());
        JsonObject json = (JsonObject) e.getAsJsonArray().get(0);
        int ally = json.get("allyCode").getAsInt();
        String name = json.get("name").getAsString();
        String guild = json.get("guildName").getAsString();
        int level = json.get("level").getAsInt();
        int grandArenaLifeTimePoints = json.get("grandArenaLifeTime").getAsInt();

        List<Char> roster = new ArrayList<>();
        List<Ship> ships = new ArrayList<>();
        JsonArray jsonRoster = json.get("roster").getAsJsonArray();
        for (JsonElement jsonChar : jsonRoster) {
            JsonObject jChar = (JsonObject) jsonChar;
            int a = jChar.get("combatType").getAsInt();
            if (a > 2) System.out.println(a + jChar.toString());
            if (jChar.get("combatType").getAsString().equals("1")) {
                roster.add(Char.getCharFromJsonObject(jChar));
            } else {
                ships.add(Ship.getShipFromJsonObject(jChar));
            }
        }
        return sortCharsandShips(ally, name, guild, level, grandArenaLifeTimePoints, roster, ships);
    }

    public static Player getPlayerFromJsonObject(JsonObject json) {
        int ally = json.get("allyCode").getAsInt();
        String name = json.get("name").getAsString();
        String guild = json.get("guildName").getAsString();
        int level = json.get("level").getAsInt();
        int grandArenaLifeTimePoints = json.get("grandArenaLifeTime").getAsInt();

        List<Char> roster = new ArrayList<>();
        List<Ship> ships = new ArrayList<>();
        JsonArray jsonRoster = json.get("roster").getAsJsonArray();
        for (JsonElement jsonChar : jsonRoster) {
            JsonObject jChar = (JsonObject) jsonChar;
            if (jChar.get("combatType").getAsString().equals("1")) {
                roster.add(Char.getCharFromJsonObject(jChar));
            } else {
                ships.add(Ship.getShipFromJsonObject(jChar));
            }
        }

        List<Char> arenaSquatChars = new LinkedList<>();
        JsonObject squat = ((JsonObject) ((JsonObject) json.get("arena")).get("char"));
        //TODO get arena squat and arena fleet
        return sortCharsandShips(ally, name, guild, level, grandArenaLifeTimePoints, roster, ships);
    }

    private static Player sortCharsandShips(int ally, String name, String guild, int level, int grandArenaLifeTimePoints, List<Char> roster, List<Ship> ships) {
        roster.sort(Comparator.comparing(Char::getGalacticPower).reversed());
        List<Char> galacticLegends = new LinkedList<>();
        for (Char c : roster) {
            if (c.getName().toLowerCase().contains("glrey") || c.getName().toLowerCase().contains("supremeleader")) {
                galacticLegends.add(c);
            }
        }
        for (Char c : galacticLegends) {
            roster.remove(c);
        }
        for (Char c : galacticLegends) {
            c.setGalacticPower(c.getGalacticPower() + 20000);
            roster.add(0, c);
        }
        ships.sort(Comparator.comparing(Ship::getGp).reversed());
        return new Player(ally, level, grandArenaLifeTimePoints, name, guild, roster, ships);
    }

    public Player(int allyCode, int level, int grandArenaLifeTimePoints, String name, String guildName, List<Char> characters, List<Ship> ships) {
        this.allyCode = allyCode;
        this.level = level;
        this.grandArenaLifeTimePoints = grandArenaLifeTimePoints;
        this.name = name;
        this.guildName = guildName;
        this.characters = characters;
        this.ships = ships;
    }

    public int getAllyCode() {
        return allyCode;
    }

    public void setAllyCode(int allyCode) {
        this.allyCode = allyCode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGrandArenaLifeTimePoints() {
        return grandArenaLifeTimePoints;
    }

    public void setGrandArenaLifeTimePoints(int grandArenaLifeTimePoints) {
        this.grandArenaLifeTimePoints = grandArenaLifeTimePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public List<Char> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Char> characters) {
        this.characters = characters;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public String toString() {
        return name + ", Level: " + level + ", Charaktere: " + characters.size() + ", Schiffe: " + ships.size();
    }
}
