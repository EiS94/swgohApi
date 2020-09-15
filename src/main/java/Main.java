import help.swgoh.api.SwgohAPI;
import help.swgoh.api.SwgohAPIBuilder;
import utilitys.ApiAccess;
import utilitys.Tuple;

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
            Map<String, List<Tuple<Player, Char>>> map = g.getPlayersAbouveStarAndGear(api, stars, gear);
            map = Utils.sortByListSize(map);
            System.out.println("bla");
        }

    }


}
