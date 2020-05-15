package src.move;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;

public interface Move {
    void validateMove(Board board, Deck deck);
    // Always call validateMove before enactMove to check validity :)
    void enactMove(Board board, Deck deck);
}
