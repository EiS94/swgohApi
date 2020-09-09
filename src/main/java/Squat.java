import org.graalvm.compiler.api.replacements.Snippet;

import java.util.LinkedList;
import java.util.List;

public class Squat {

    private Char leader;
    private List<Char> chars;
    private int gp;
    //TODO arena rank

    public Squat(Char leader, List<Char> chars, int gp) {
        this.leader = leader;
        this.chars = chars;
        this.gp = gp;
    }

    public Squat(Char leader, List<Char> chars) {
        this.leader = leader;
        this.chars = chars;
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
