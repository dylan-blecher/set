package src.game;

import src.action.Action;
import src.action.PlayerActionSelectSet;
import src.card.Card;
import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;

import java.util.HashMap;
import java.util.Map;

import static src.cardCollection.board.Board.MAX_BOARD_SIZE;
import static src.cardCollection.set.Set.SET_SIZE;
import static src.cardCollection.set.Set.isSet;

/**
 * @author dylanblecher
 * Given an action, validate it or throw an exception if it's an invalid action.
 */

public class stateValidator {
    // class should not be instantiatable
    private stateValidator() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }
    public static boolean setExists(Board board) {
        return findSet(board) != null;
    }

    /**
     * @param board
     * @return a set found on the board (mapping from the position on the board to the card itself for all 3 cards)
     * @returns null if no sets are on the board
     *
     * Currently we are using brute force, which is pretty fast because the number of cards is small.
     * According to Wikipedia, this is an NP-hard problem.
     * https://en.wikipedia.org/wiki/Set_(card_game)#Complexity
     *
     * Ideas for optimisation:
        * Might choose one set over another based on how many more it leaves on the board.
        * Create groups of cards based on features (e.g. put all cards with 1 shape on them together...)
     */
    // returns a map (position --> card) containing 3 items
    public static Map<Integer, Card> findSet(Board board) {

        for (int i = 0; i < MAX_BOARD_SIZE; i++) {
            for (int j = i + 1; j < MAX_BOARD_SIZE; j++) {
                for (int k = j + 1; k < MAX_BOARD_SIZE; k++) {
                    Card cardI = board.getCard(i);
                    Card cardJ = board.getCard(j);
                    Card cardK = board.getCard(k);

                    Card[] potentialSet = {cardI, cardJ, cardK};

                    if (isSet(potentialSet)) {
                        Map<Integer, Card> set = new HashMap<>();
                        set.put(i, cardI);
                        set.put(j, cardJ);
                        set.put(k, cardK);

                        return set;
                    }
                }
            }
        }

        return null;
    }

    public static void validateAction(Action action, Board board, Deck deck) {
        if (action == null) throw new UnsupportedOperationException("null action type.");

        switch (action.getType()) {
            case SHOW_SET:
                validateSHOW_SET(board);
                break;
            case DRAW_THREE:
                validateDRAW_THREE(board, deck);
                break;
            case LEAVE_GAME:
                validateDROP_OUT();
                break;
            case REQUEST_SHOW_SET:
                validateREQUEST_SHOW_SET();
                break;
            case REQUEST_DRAW_THREE:
                validateREQUEST_DRAW_THREE(board, deck);
                break;
            case SELECT_SET:
                validateSELECT_SET((PlayerActionSelectSet) action, board);
                break;
            default:
                throw new UnsupportedOperationException("Invalid action type.");
        }
    }

    private static void validateSHOW_SET(Board board) {
        if (findSet(board) == null) {
            throw new UnsupportedOperationException("There are no sets on the board.");
        }
    }

    private static void validateDRAW_THREE(Board board, Deck deck) {
        if (deck.nCards() < SET_SIZE)
            throw new UnsupportedOperationException("Not enough cards left to draw SET_SIZE.");
        if (board.nEmptySpots() == 0)
            throw new UnsupportedOperationException("Board is full.");
    }

    private static void validateREQUEST_SHOW_SET() {
        // always valid :)

    }

    private static void validateREQUEST_DRAW_THREE(Board board, Deck deck) {
        validateDRAW_THREE(board, deck);
    }

    private static void validateDROP_OUT() {
        // always valid :)
    }

    private static void validateSELECT_SET(PlayerActionSelectSet action, Board board) {
        Card[] potentialSet = new Card[3];
        int index = 0;
        for (int cardPosition: action.getCardPositions()) {
            if (cardPosition >= MAX_BOARD_SIZE || cardPosition < 0)
                throw new UnsupportedOperationException("Card position ID entered is not on the board!");
            potentialSet[index++] = board.getCard(cardPosition);
        }

        if (!isSet(potentialSet))
            throw new UnsupportedOperationException("The cards you selected do not form a set.");
    }
}
