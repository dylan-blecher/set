package src.move;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.player.Players;

// TODO: maybe interface is unnecessary and these should just be abstract classes
public abstract class Move implements MoveRunner {
    private final int playerID;

    public Move(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }


    public abstract void enactMove(Board board, Deck deck, Players players);
    public abstract MoveType getMoveType();
}
