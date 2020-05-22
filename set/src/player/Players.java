package src.player;

import java.util.HashMap;
import java.util.Map;

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
}
