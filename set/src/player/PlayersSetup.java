package src.player;

import java.util.Scanner;

public class PlayersSetup {
    private PlayersSetup() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    public static Player[] setupPlayers() {
//        TODO: move scanner into it's own class?
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many players?");
        int nPlayers = scanner.nextInt();
        if (nPlayers < 1) {
            // TODO: customise this exception? re-ask for nPlayers
            throw new UnsupportedOperationException("Must have 1 or more player.");
        }

        Player[] players = new Player[nPlayers];
        for (int playerCount = 1; playerCount <= nPlayers; playerCount++) {
            // TODO: maybe create a version where it's a continuously running website and you safe everyone's alltime scores between sessions... using a database... in which case, each user needs to be able to login with a unique ID...
            System.out.printf("Name of player %d?\n", playerCount);
            String name = scanner.nextLine(); // TODO not waiting for both names of input! whaaat? maybe because i'm using same string 'name' for both names
            players[playerCount - 1] = new Player(playerCount, name);
        }

        return players;
    }
}
