package src.game;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.player.Player;
import static src.cardCollection.board.Dealer.setupBoard;
import static src.cardCollection.deck.DeckBuilder.buildDeck;
import static src.game.Referee.setExists;
import static src.move.MoveCompleter.completeMove;
import static src.player.PlayersSetup.setupPlayers;

public class Game {
    private Player[] players;
    private Board board;
    private Deck deck;

    public void run() {
//        TODO: should all this stuff be done in a separate class GameRunner?
        this.players = setupPlayers();
        this.deck    = buildDeck();
        this.board   = setupBoard(this.deck);

        while (!gameIsOver()) {
            this.board.display();
            completeMove();
        }


    }

    private boolean gameIsOver() {
        return board.size() > 0 || setExists(board);
    }
}

// isSet, game runner, AI, move checker