import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import utilitys.Tuple;

public class Utils {

    public static Map<String, List<Tuple<Player, Char>>> sortByListSize(Map<String, List<Tuple<Player, Char>>> unsorted) {
        Map<String, List<Tuple<Player, Char>>> sorted = new LinkedHashMap<>();
        int max = -1;
        String best = "";
        while (!unsorted.isEmpty()) {
            for (String s : unsorted.keySet()) {
                if (unsorted.get(s).size() > max) {
                    max = unsorted.get(s).size();
                    best = s;
                }
            }
            sorted.put(best, unsorted.remove(best));
            max = -1;
        }
        return sorted;
    }

}
