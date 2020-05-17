package src.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Players {
    private Map<Integer, Player> activePlayers   = new HashMap<>();
    private Map<Integer, Player> inactivePlayers = new HashMap<>();

    Scanner scanner = new Scanner(System.in);

    public Players() {
        while (true) {
            try {
                setupPlayers();
                break;
            }
            catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setupPlayers() {
        int nPlayers = readNPlayers();
        if (nPlayers < 1) {
            // TODO: customise this exception? re-ask for nPlayers
            throw new UnsupportedOperationException("Must have 1 or more player.");
        }

        for (int playerCount = 0; playerCount < nPlayers; playerCount++) {
            // TODO: maybe create a version where it's a continuously running website and you safe everyone's alltime scores between sessions... using a database... in which case, each user needs to be able to login with a unique ID...
            String name = readPlayerName();
            activePlayers.put(playerCount, new Player(playerCount, name));
        }
    }

    private String readPlayerName() {
        System.out.println("Name of player?");
        return scanner.nextLine();
    }

    private int readNPlayers() {
        System.out.println("How many players?");
        int nPlayers = scanner.nextInt();
        scanner.nextLine();
        return nPlayers;
    }

    public void dropPlayerFromGame(int playerID) {
        Player player = activePlayers.remove(playerID);
        inactivePlayers.put(playerID, player);
    }

    public int getNActivePlayers() {
        return activePlayers.size();
    }

    public Map<Integer, Player> getActivePlayers() {
        return activePlayers;
    }

    public Map<Integer, Player> getInactivePlayers() {
        return inactivePlayers;
    }

    // looks for the player by ID in active and inactive players
    // returns the player if found or null if not found
    public Player getPlayer(int playerID) {
        // search active players
        for (int searchedPlayerID: activePlayers.keySet())
            if (playerID == searchedPlayerID)
                return activePlayers.get(playerID);

        // search inactive players
        for (int searchedPlayerID: inactivePlayers.keySet())
            if (playerID == searchedPlayerID)
                return inactivePlayers.get(playerID);

        return null;
    }
}
