package src.game;

import src.player.Player;
import src.player.PlayerComparator;

import java.util.Arrays;

public class Result {
    // ascendingly sorted by nSets each player found.
    // key: playerID, value: player
    private Player[] playerResults;

    public Result(Player[] players) {
        this.playerResults = players.clone();
        Arrays.sort(this.playerResults, new PlayerComparator());
    }

    // sort results based on sets found
    public void showResult() {
        System.out.println("RESULTS:");
        for (int i = playerResults.length - 1; i >= 0; i--)
            playerResults[i].seeScore();
    }
}
