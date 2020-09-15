import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import help.swgoh.api.SwgohAPI;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Guild {

    List<Player> players;

    public static Guild getGuild(int allyCode, SwgohAPI api) throws ExecutionException, InterruptedException {
        JsonParser parser = new JsonParser();
        JsonElement e = parser.parse(api.getGuild(allyCode).get());
        JsonObject json = (JsonObject) e.getAsJsonArray().get(0);
        JsonArray roster = json.get("roster").getAsJsonArray();
        List<Integer> allyCodes = new LinkedList<>();
        for (JsonElement el : roster) {
            JsonObject o = (JsonObject) el;
            allyCodes.add(o.get("allyCode").getAsInt());
        }
        List<Player> players = new LinkedList<>();
        JsonArray array = parser.parse(api.getPlayers(allyCodes).get()).getAsJsonArray();
        while (array.size() < allyCodes.size()) {
            array = parser.parse(api.getPlayers(allyCodes).get()).getAsJsonArray();
        }
        for (JsonElement el : array) {
            JsonObject o = (JsonObject) el;
            players.add(Player.getPlayerFromJsonObject(o));
        }
        players.sort(Comparator.comparing(Player::getGrandArenaLifeTimePoints).reversed());
        return new Guild(players);
    }

    public Guild(List<Player> players) {
        this.players = players;
    }

    public int countChar(String name) {
        int counter = 0;
        for (Player p : players) {
            for (Char c : p.getCharacters()) {
                if (c.getName().toLowerCase().contains(name)) counter++;
            }
        }
        return counter;
    }

    public int countChar(Char c) {
        int counter = 0;
        for (Player p : players) {
            for (Char c2 : p.getCharacters()) {
                if (c.getName().toLowerCase().contains(c.getName().toLowerCase())) counter++;
            }
        }
        return counter;
    }

    public int countCharAbouveGear(String name, int minGear) {
        int counter = 0;
        for (Player p : players) {
            for (Char c : p.getCharacters()) {
                if (c.getName().toLowerCase().contains(name) && c.getGear() >= minGear) counter++;
            }
        }
        return counter;
    }

    public Map<Player, Char> getPlayersWithCharAbouveGear(String name, int minGear) {
        Map<Player, Char> map = new HashMap<>();
        for (Player p : players) {
            for (Char c : p.getCharacters()) {
                if (c.getName().toLowerCase().contains(name) && c.getGear() >= minGear) {
                    map.put(p, c);
                }
            }
        }
        return map;
    }

    public Map<Player, Char> getPlayersWithCharAbouveStar(String name, int minStars) {
        Map<Player, Char> map = new HashMap<>();
        for (Player p : players) {
            for (Char c : p.getCharacters()) {
                if (c.getName().toLowerCase().contains(name) && c.getStars() >= minStars) {
                    map.put(p, c);
                }
            }
        }
        return map;
    }

    public Map<Player, Char> getPlayersWithCharAbouveStarAndGear(String name, int minStars, int minGear) {
        Map<Player, Char> map = new HashMap<>();
        for (Player p : players) {
            for (Char c : p.getCharacters()) {
                if (c.getName().toLowerCase().contains(name) && c.getStars() >= minStars && c.getGear() >= minGear) {
                    map.put(p, c);
                }
            }
        }
        return map;
    }

    public Map<String, Integer> countCharsAbouveStarAndGear(SwgohAPI api, int minStars, int minGear) throws ExecutionException, InterruptedException {
        Map<String, Integer> map = new HashMap<>();
        List<String> names = Player.getAllCharNames(api);
        for (String name : names) {
            int counter = 0;
            for (Player p : players) {
                for (Char c : p.getCharacters()) {
                    if (c.getName().equals(name) && c.getGear() >= minGear && c.getStars() >= minStars) {
                        counter++;
                        break;
                    }
                }
            }
            map.put(name.substring(5, name.length()-5), counter);
        }
        return map;
    }

}
