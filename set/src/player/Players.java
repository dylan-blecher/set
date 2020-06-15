package src.player;

import src.proto.AllProtos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author dylanblecher
 * Manage active and inactive players in the game.
 * We generally get a player by mapping from their ID, which is just an integer.
 */
public class Players {
    private Map<Integer, Player> activePlayers;
    private Map<Integer, Player> inactivePlayers = new HashMap<>();

    public Players(Map<Integer, Player> activePlayers) {
        this.activePlayers = activePlayers;
    }

    public void dropPlayerFromGame(int playerID) {
        Player player = activePlayers.remove(playerID);
        if (player != null) inactivePlayers.put(playerID, player);
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
        if (activePlayers.get(playerID)   != null) return activePlayers.get(playerID);
        if (inactivePlayers.get(playerID) != null) return inactivePlayers.get(playerID);
        return null;
    }

    public List<AllProtos.Player> getActivePlayersProto() {
        List<AllProtos.Player> activePlayersProto = new LinkedList<>();
        for (Player player: activePlayers.values()) activePlayersProto.add(player.proto);
        return activePlayersProto;
    }
}
