package src.move.moves;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.cardCollection.set.Set;
import src.move.Move;
import src.move.PlayerMove;
import src.move.MoveType;
import src.player.Player;

import static src.game.Referee.getSet;

public class ShowSet extends PlayerMove implements Move {
    public ShowSet(Player player) {
        super(player);
    }

    @Override
    public void validateMove(Board board, Deck deck) {
        // pass. No restrictions as yet here. Maybe in the future, implement a limit on how many times you can request this!
    }

    @Override
    public void enactMove(Board board, Deck deck) {
        Set set = getSet(board);
        if (set == null)
            System.out.println("There are no sets on the board.");
        else
            set.display();
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.SHOW_SET;
    }
}
