package src.action;


import java.util.HashSet;
import java.util.Set;

public class Action {
    private final ActionType actionType;
    // actions are usually carried out by one people, but some actions must be agreed upon by all players
    // for example, DRAW_THREE can only occur if all active players request this move, so they're all responsible.
    private final Set<Integer> playerIDs;

    public Action(ActionType actionType, Set<Integer> playerIDs) {
        this.actionType = actionType;
        this.playerIDs = playerIDs;
    }

    public Action(ActionType actionType, int playerID) {
        this.actionType = actionType;
        this.playerIDs = new HashSet<>();
        playerIDs.add(playerID);
    }

    public ActionType getType() { return actionType; }
    public Set<Integer> getPlayerIDs() { return playerIDs; }
    public int getPlayerID() {
        return playerIDs.iterator().next();
    }
}


