import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.graalvm.compiler.api.replacements.Snippet;

import java.util.LinkedList;
import java.util.List;

public class Squat {

    private Char leader;
    private List<Char> chars;
    private int gp, rank;
    //TODO arena rank


    public Squat() {
    }

    public Squat(Char leader, List<Char> chars) {
        this.leader = leader;
        this.chars = chars;
    }

    public Squat(Char leader, List<Char> chars, int rank) {
        this.leader = leader;
        this.chars = chars;
        this.rank = rank;
    }

    public static Squat getSquatFromJsonObject(JsonObject json, List<Char> roster) {
        List<Char> arenaSquatChars = new LinkedList<>();
        JsonArray a = json.get("squad").getAsJsonArray();
        Squat squat = new Squat();
        for (JsonElement element : a) {
            JsonObject object = (JsonObject) element;
            if (object.get("squadUnitType").getAsString().equals("2")) {
                String arenaName = object.get("defId").getAsString().toLowerCase();
                for (Char c : roster) {
                    if (c.getName().toLowerCase().contains(arenaName)) {
                        squat.setLeader(c);
                        break;
                    }
                }
            } else {
                String arenaName = object.get("defId").getAsString().toLowerCase();
                for (Char c : roster) {
                    if (c.getName().toLowerCase().contains(arenaName)) {
                        arenaSquatChars.add(c);
                        break;
                    }
                }
            }
            squat.setChars(arenaSquatChars);
        }
        return squat;
    }

    public List<Char> getChars() {
        return chars;
    }

    public void setChars(List<Char> chars) {
        this.chars = chars;
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }

    public Char getLeader() {
        return leader;
    }

    public void setLeader(Char leader) {
        this.leader = leader;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getFullInfos() {
        StringBuilder result = new StringBuilder("GP : " + gp + "\n" + "Leader: " + leader + "\n");
        for (Char c : chars) {
            result.append(c.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return leader + ", GP: " + gp;
    }
}
