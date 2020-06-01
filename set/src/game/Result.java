package src.game;

import src.player.Player;
import src.proto.AllProtos;

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
        playersSortedByResults.sort((left, right) -> right.getNSetsCollected() - left.getNSetsCollected());
    }

    public AllProtos.Result getProto() {
        return AllProtos.Result
                .newBuilder()
                .addAllRankedPlayers(buildRankedPlayersProto())
                .build();
    }

    private List<AllProtos.Player> buildRankedPlayersProto() {
        List<AllProtos.Player> rankedPlayersProto = new LinkedList<>();
        for (Player player: playersSortedByResults) rankedPlayersProto.add(player.proto);
        return rankedPlayersProto;
    }

    // sort results based on sets found
    public void showResult() {
        System.out.println("RESULTS:");
        for (Player player: playersSortedByResults)
            player.seeScore();
    }
}