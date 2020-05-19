package src.action.ConsensusManager;

import src.action.Action;
import src.action.ActionType;
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

    static {
        for (var moveType: getMoveTypesThatRequireConsensus())
            playersAgreeingToMove.put(moveType, new HashSet<>());
    }

    public static void update(ActionType actionType, int playerID) {
        switch(actionType) {
            case REQUEST_DRAW_THREE:
            case REQUEST_SHOW_SET:
                addAgreement(actionType, playerID);
                break;
            case SELECT_SET:
            case DRAW_THREE:
                resetRequests();
                break;
            case LEAVE_GAME:
                removePlayer(playerID);
                break;
            case SHOW_SET:
                resetRequest(REQUEST_SHOW_SET);
                break;
        }
    }

    // TODO: This can probably be better implemented with a listener on when these lists become full
    public static void updateMoveQueue(int nActivePlayers) {
        if (isConsensus(REQUEST_DRAW_THREE, nActivePlayers))
            ActionQueue.addAction(new Action(DRAW_THREE, playersAgreeingToMove.get(REQUEST_DRAW_THREE)));

        if (isConsensus(REQUEST_SHOW_SET, nActivePlayers))
            ActionQueue.addAction(new Action(SHOW_SET, playersAgreeingToMove.get(REQUEST_SHOW_SET)));
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
