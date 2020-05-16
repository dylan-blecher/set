package src.move.moves;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.Move;
import src.move.PlayerMove;
import src.move.MoveType;
import src.player.Player;

public class EndGame extends PlayerMove implements Move {
    public EndGame(Player player) {
        super(player);
    }

    @Override
    public void validateMove(Board board, Deck deck) {
        // pass. For now can always end game.
    }

    @Override
    public void enactMove(Board board, Deck deck) {
        // pass. Doesn't have to do anything.
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.END_GAME;
    }
}
