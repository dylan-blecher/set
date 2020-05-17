package src.game;

import src.card.Card;
import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.cardCollection.set.Set;
import src.move.MoveRunner;
import src.move.moves.RequestDrawThree;
import src.move.moves.LeaveGame;
import src.move.moves.SelectSet;
import src.move.moves.RequestShowSet;

import static src.cardCollection.board.Board.MAX_BOARD_SIZE;
import static src.cardCollection.set.Set.SET_SIZE;
import static src.cardCollection.set.Set.isSet;

// TODO: This is a bit gross because Set is a thing in Java.util so I'm overriding :/

public class Referee {
    // class should not be instantiatable
    private Referee() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    // TODO: not sure if this belongs in the referee.. will be reused by move...
    public static boolean setExists(Board board) {
        return getSet(board) != null;
    }

    // TODO: not sure if this belongs in the referee.. will be reused by AI...
    public static Set getSet(Board board) {
        // TODO: brute force, optimise later! Might choose one set over another based on how many more it leaves on the board... or that's up to AI
        //       there's got to be a smart way by considering which 3 cards are new... DOn't need to compare them with all surely?
        for (Card card1: board.getCards()) {
            for (Card card2 : board.getCards()) {
                // don't compare a card with itself (i.e. skip if card has matching ID)
                if (card1 == card2) continue;
                for (Card card3 : board.getCards()) {
                    if (card3 == card2 || card3 == card1) continue;
                    Card[] potentialSet = {card1, card2, card3};
                    if (isSet(potentialSet)) return new Set(potentialSet);
                }
            }
        }

        return null;
    }

    public static void validateMove(MoveRunner moveRunner, Board board, Deck deck) {
        if (moveRunner == null) throw new UnsupportedOperationException("Invalid move type.");

        switch (moveRunner.getMoveType()) {
            case LEAVE_GAME:
                validateDROP_OUT((LeaveGame) moveRunner, board, deck);
                break;
            case REQUEST_SHOW_SET:
                validateSHOW_SET((RequestShowSet) moveRunner, board, deck);
                break;
            case REQUEST_DRAW_THREE:
                validateREQUEST_DRAW_THREE((RequestDrawThree) moveRunner, board, deck);
                break;
            case SELECT_SET:
                validateSELECT_SET((SelectSet) moveRunner, board, deck);
                break;
        }
    }

    private static void validateREQUEST_DRAW_THREE(RequestDrawThree move, Board board, Deck deck) {
        if (deck.nCards() < SET_SIZE)
            throw new UnsupportedOperationException("Not enough cards left to draw SET_SIZE.");
        if (board.nEmptySpots() == 0)
            throw new UnsupportedOperationException("Board is full.");
    }

    private static void validateDROP_OUT(LeaveGame move, Board board, Deck deck) {
        // always valid :)
    }

    private static void validateSHOW_SET(RequestShowSet move, Board board, Deck deck) {
        // always valid :)
    }

    private static void validateSELECT_SET(SelectSet move, Board board, Deck deck) {
        Card[] potentialSet = new Card[3];
        int index = 0;
        for (int cardPosition: move.getCardPositions()) {
            if (cardPosition >= MAX_BOARD_SIZE || cardPosition < 0)
                throw new UnsupportedOperationException("Card position ID entered is not on the board!");
            potentialSet[index++] = board.getCard(cardPosition);
        }

        if (!isSet(potentialSet))
            throw new UnsupportedOperationException("The cards you selected do not form a set.");
    }
}
