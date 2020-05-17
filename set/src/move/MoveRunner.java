package src.move;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.player.Players;

public interface MoveRunner {
    void enactMove(Board board, Deck deck, Players players);
    MoveType getMoveType();
    int getPlayerID();
}
