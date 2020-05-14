package src.game;

import src.board.Board;
import src.deck.Deck;
import src.player.Player;
import static src.board.Dealer.setupBoard;
import static src.deck.DeckBuilder.buildDeck;

import java.util.Collections;
import java.util.Scanner;


public class Game {
    private int nPlayers;
    private Player[] players;
    private Board board;
    private Deck deck;

    public void run() {
//        TODO: should all this stuff be done in a separate class GameRunner?
        this.players = setupPlayers();
        this.deck    = buildDeck();
        this.board   = setupBoard(this.deck);
        this.board.display();
    }

    private Player[] setupPlayers() {
//        TODO: move scanner into it's own class?
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many players?");
        int nPlayers = scanner.nextInt();
        setNPlayers(nPlayers); // TODO: check this doesn't throw an error before continuing...

        Player[] players = new Player[nPlayers];
        for (int playerCount = 1; playerCount <= nPlayers; playerCount++) {
            // TODO: maybe create a version where it's a continuously running website and you safe everyone's alltime scores between sessions... using a database... in which case, each user needs to be able to login with a unique ID...
            System.out.printf("Name of player %d?\n", playerCount);
            String name = scanner.nextLine(); // TODO not waiting for both names of input! whaaat?
            players[playerCount - 1] = new Player(playerCount, name);
        }

        return players;
    }

    private void setNPlayers(int nPlayers) {
        if (nPlayers < 1) {
            // TODO: customise this exception?
            throw new UnsupportedOperationException("Must have 1 or more player.");
        }

        this.nPlayers = nPlayers;
    }
}