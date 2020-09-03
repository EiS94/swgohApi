import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import help.swgoh.api.SwgohAPI;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
}
