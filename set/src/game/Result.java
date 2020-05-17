package src.game;

import src.player.Player;

import java.util.*;

public class Result {
    // descendingly sorted by value (i.e. nSets) each player found.
    // key: playerID, value: player
    List<Player> playersSortedByResults;

    public Result(Map<Integer, Player> activePlayers, Map<Integer, Player> inactivePlayers) {
        HashMap<Integer, Player> playersByID = new HashMap<>();
        playersByID.putAll(activePlayers);
        playersByID.putAll(inactivePlayers);

        playersSortedByResults = new ArrayList<>(playersByID.values());
        playersSortedByResults.sort(Comparator.comparing(Player::getNSetsCollected));
    }

    // sort results based on sets found
    public void showResult() {
        System.out.println("RESULTS:");
        for (Player player: playersSortedByResults)
            player.seeScore();
    }
}