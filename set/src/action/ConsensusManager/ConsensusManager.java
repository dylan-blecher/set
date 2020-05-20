package src.action.ConsensusManager;

import src.action.Action;
import src.action.ActionType;
import src.action.PlayerAction;
import src.action.actionQueue.ActionQueue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static src.action.ActionType.*;

// static class for managing moves that require consensus to be enacted
public class ConsensusManager {
    // map from MoveType to a list of players in consensus for that move
    private static final Map<ActionType, Set<Integer>> playersAgreeingToMove = new HashMap<>();

    // class should not be instantiatable
    private ConsensusManager() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    static {
        for (var moveType: getMoveTypesThatRequireConsensus())
            playersAgreeingToMove.put(moveType, new HashSet<>());
    }

    public static void update(Action action) {
        ActionType actionType = action.getType();
        switch(action.getType()) {
            case REQUEST_DRAW_THREE:
            case REQUEST_SHOW_SET:
                addAgreement(actionType, ((PlayerAction)action).getPlayerID());
                break;
            case SELECT_SET:
            case DRAW_THREE:
                resetRequests();
                break;
            case LEAVE_GAME:
                removePlayer(((PlayerAction)action).getPlayerID());
                break;
            case SHOW_SET:
                resetRequest(REQUEST_SHOW_SET);
                break;
        }
    }

    // TODO: This can probably be better implemented with a listener on when these lists become full
    public static void updateMoveQueue(int nActivePlayers, ActionQueue actions) {
        if (isConsensus(REQUEST_DRAW_THREE, nActivePlayers))
            actions.addAction(new Action(DRAW_THREE));

        if (isConsensus(REQUEST_SHOW_SET, nActivePlayers))
            actions.addAction(new Action(SHOW_SET));
    }

    private static void addAgreement(ActionType actionType, int playerID) {
        playersAgreeingToMove.get(actionType).add(playerID);
    }

    private static void removePlayer(int playerID) {
        for (var moveType: playersAgreeingToMove.keySet())
            playersAgreeingToMove.get(moveType).remove(playerID);
    }

    private static void resetRequests() {
        resetRequest(REQUEST_DRAW_THREE);
        resetRequest(REQUEST_SHOW_SET);
    }

    private static void resetRequest(ActionType actionType) {
        playersAgreeingToMove.get(actionType).clear();
    }

    private static ActionType[] getMoveTypesThatRequireConsensus() {
        return new ActionType[]{REQUEST_DRAW_THREE, REQUEST_SHOW_SET};
    }

    private static boolean isConsensus(ActionType actionType, int nActivePlayers) {
        return playersAgreeingToMove.get(actionType).size() == nActivePlayers;
    }
}
