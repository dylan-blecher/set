package src.action.ConsensusManager;

import src.action.Action;
import src.action.ActionType;
import src.action.PlayerAction;
import src.action.actionQueue.SynchronisedActionQueue;
import src.proto.AllProtos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static src.action.ActionType.*;

/**
 * @author dylanblecher
 * ConsensusManager is used to manage the agreements for requestType actions.
 * For example, we will only draw three cards when all players have requested draw three.
 * The same applies for showing a set.
 * For each requestType action (requestShowSet and requestDrawThree), we store
 * each player who has requested that action.
 */
// static class for managing moves that require consensus to be enacted
public class ConsensusManager {
    // map from MoveType to a list of players in consensus for that move
    private static final Map<ActionType, Set<Integer>> playersAgreeingToMove = new HashMap<>();

    // class should not be instantiatable
    private ConsensusManager() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private");
    }

    /**
     * Setup ConsensusManager on game startup for every action that requires consensuse to be enacted.
     * The set of players will initially be empty, of course.
     */
    static {
        for (ActionType actionType: getMoveTypesThatRequireConsensus())
            playersAgreeingToMove.put(actionType, new HashSet<>());
    }

    /**
     * @param action takes in an action and updates the state of the ConsensusManager based on it.
     */
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

    /**
     * Adds actions to enact the request, if all players have agreed to the request.
     */
    public static void updateMoveQueue(int nActivePlayers, SynchronisedActionQueue actions) {
        if (isConsensus(REQUEST_DRAW_THREE, nActivePlayers)) actions.addAction(new Action(DRAW_THREE));
        if (isConsensus(REQUEST_SHOW_SET, nActivePlayers)) actions.addAction(new Action(SHOW_SET));
    }

    private static void addAgreement(ActionType actionType, int playerID) {
        playersAgreeingToMove.get(actionType).add(playerID);
    }

    /**
     * @param playerID the ID of the player who no longer agrees to a request.
     *                 Useful if the player changes their mind or leaves the game.
     */
    private static void removePlayer(int playerID) {
        for (ActionType moveType: playersAgreeingToMove.keySet())
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

    private static int getNumberInAgreement(ActionType actionType) {
        return playersAgreeingToMove.get(actionType).size();
    }

    /**
     * Convert the ConsensusManager into a seralisable proto using Google's Protobuf
     */
    public static AllProtos.Consensuses getConsensusesProto(int nActivePlayers) {
        return AllProtos.Consensuses
                .newBuilder()
                .setNActivePlayers(nActivePlayers)
                .setNPlayersRequestingDrawThree(getNumberInAgreement(REQUEST_DRAW_THREE))
                .setNPlayersRequestingShowSet(getNumberInAgreement(REQUEST_SHOW_SET))
                .build();
    }
}
