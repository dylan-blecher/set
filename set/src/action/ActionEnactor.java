package src.action;

import src.action.ConsensusManager.ConsensusManager;
import src.action.actionQueue.SynchronisedActionQueue;
import src.card.Card;
import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.cardCollection.set.Set;
import src.player.Players;

import static java.lang.Integer.min;
import static src.cardCollection.board.Board.BASE_BOARD_SIZE;
import static src.cardCollection.board.Dealer.addToBoardFromDeck;
import static src.cardCollection.set.Set.SET_SIZE;
import static src.game.stateValidator.findSet;
import static src.server.Server.sendRevealedSet;

/**
 * @author dylanblecher
 * Responsible for enacting a given action
 * We can assume the action is valid, since the validator is called on the action first.
 */
public class ActionEnactor {
    // make ActionEnactor uninstantiable
    private ActionEnactor() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private");
    }

    /**
     *
     * @param action    the action to enact
     * @param board     the board to make changes on based on the action
     * @param deck      the remaining deck of cards to make changes on based on the action
     * @param players   the players in the game (active and inactive)
     * @param actions   the queue of actions to add the action to
     */
    public static void enact(Action action, Board board, Deck deck, Players players, SynchronisedActionQueue actions) {
        ActionType actionType = action.getType();
        switch (actionType) {
            case LEAVE_GAME:
                enactLeaveGame((PlayerAction) action, players);
                break;
            case SELECT_SET:
                enactSelectSet((PlayerActionSelectSet) action, board, deck, players);
                break;
            case DRAW_THREE:
                enactDrawThree(board, deck);
                break;
            case SHOW_SET:
                enactShowSet(board, players, deck);
                break;
        }

        ConsensusManager.update(action);
        ConsensusManager.updateMoveQueue(players.getNActivePlayers(), actions);
    }

    private static void enactLeaveGame(PlayerAction playerAction, Players players) {
        int playerID = playerAction.getPlayerID();
        players.dropPlayerFromGame(playerID);
    }

    private static void enactShowSet(Board board, Players players, Deck deck) {
        java.util.Set<Integer> setCardPositions = findSet(board).keySet();
        sendRevealedSet(setCardPositions, board, players);
        removeSetFromBoard(setCardPositions, board);
        replenishBoard(board, deck);
    }

    private static void enactSelectSet(PlayerActionSelectSet action, Board board, Deck deck, Players players) {
        Set set = removeSetFromBoard(action.getCardPositions(), board);
        players.getPlayer(action.getPlayerID()).collectSet(set);
        replenishBoard(board, deck);
    }

    private static Set removeSetFromBoard(Iterable<Integer> setCardPositions, Board board) {
        Card[] setCards = new Card[3];
        int index = 0;
        for (int cardPosition: setCardPositions)
            setCards[index++] = board.removeCard(cardPosition);
        return new Set(setCards);
    }

    /**
     * Tries to refill the board to the base size after cards have been removedd.
     * If the deck is empty or the board is not below baseSize, there is nothing to do.
     */
    private static void replenishBoard(Board board, Deck deck) {
        if (board.nCards() < BASE_BOARD_SIZE && deck.nCards() >= 0) {
            int nEmptyBaseSlots = BASE_BOARD_SIZE - board.nCards();
            int nToAdd = min(nEmptyBaseSlots, deck.nCards());
            addToBoardFromDeck(nToAdd, board, deck);
        }
    }

    /**
     * Adds 3 cards (or fills up the board if less than 3 slots are available)
     */
    private static void enactDrawThree(Board board, Deck deck) {
        int nCardsToAdd = min(board.nEmptySpots(), SET_SIZE);
        addToBoardFromDeck(nCardsToAdd, board, deck);
    }
}