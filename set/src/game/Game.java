package src.game;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.Move;
import src.player.Player;

import static src.cardCollection.board.Dealer.setupBoard;
import static src.cardCollection.deck.DeckBuilder.buildDeck;
import static src.game.Referee.setExists;
import static src.move.MoveCompleter.move;
import static src.move.MoveType.END_GAME;
import static src.player.PlayersSetup.setupPlayers;

public class Game {
    // TODO: change this players array to be a map from ID to player structure
    private Player[] players;
    private Board board;
    private Deck deck;

    public void run() {
//        TODO: should all this stuff be done in a separate class GameRunner?
        this.players = setupPlayers();
        this.deck    = buildDeck();
        this.board   = setupBoard(this.deck);
        while (gameIsNotOver()) {
//            TODO: might not want to display board every time - only if the board changed since previous move...
            this.board.display();
            Move move = move(board, deck, players);
            if (move.getMoveType() == END_GAME) break;
        }

        Results results = new Results(players);
        results.showResults();
    }

    private boolean gameIsNotOver() {
        return deck.size() > 0 || setExists(board);
    }
}

// isSet, game runner, AI, move checker