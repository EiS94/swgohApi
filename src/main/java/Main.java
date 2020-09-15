import help.swgoh.api.SwgohAPI;
import help.swgoh.api.SwgohAPIBuilder;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        SwgohAPI api = new SwgohAPIBuilder().withUsername(ApiAccess.getApiUsername())
                .withPassword(ApiAccess.getApiPassword()).build();

        List<String> names = Player.getAllCharNames(api);

        Guild g = Guild.getGuild(589477137, api);

        //Player p = Player.getPlayer(api, 589477137);

        //Player p2 = Player.getPlayer(api, 979382945);

        Scanner sc = new Scanner(System.in);

        /*while (true) {
            System.out.println("Which Char?");
            String name = sc.nextLine();
            System.out.println("Which min gear level?");
            int gear = Integer.parseInt(sc.nextLine());
            System.out.println("Which min stars?");
            int stars = Integer.parseInt(sc.nextLine());
            Map<Player, Char> map = g.getPlayersWithCharAbouveStarAndGear(name, stars, gear);
            for (Player p : map.keySet()) {
                String out = p.getName();
                Char c = map.get(p);
                out += " -> " + c.toString();
                System.out.println(out);
            }
        }
         */

        while (true) {
            System.out.println("Which min gear level?");
            int gear = Integer.parseInt(sc.nextLine());
            System.out.println("Which min stars?");
            int stars = Integer.parseInt(sc.nextLine());
            Map<String, Integer> map = g.countCharsAbouveStarAndGear(api, stars, gear);
            Object[] a = map.entrySet().toArray();
            Arrays.sort(a, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Map.Entry<String, Integer>) o2).getValue()
                            .compareTo(((Map.Entry<String, Integer>) o1).getValue());
                }
            });
            for (Object e : a) {
                System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : "
                        + ((Map.Entry<String, Integer>) e).getValue());
            }
        }

    }



}
