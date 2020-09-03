import help.swgoh.api.SwgohAPI;
import help.swgoh.api.SwgohAPIBuilder;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        SwgohAPI api = new SwgohAPIBuilder().withUsername("eiS").withPassword("swgohAPI").build();

        Guild g = Guild.getGuild(498229368, api);

        //Player p = Player.getPlayer(api, 589477137);

        //Player p2 = Player.getPlayer(api, 692526929);

        System.out.println(g.players.get(0));

    }

}