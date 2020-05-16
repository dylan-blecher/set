package src.player;

import src.cardCollection.set.Set;
import src.move.Move;
import src.move.MoveType;
import src.move.moves.DrawThree;
import src.move.moves.EndGame;
import src.move.moves.SelectSet;
import src.move.moves.ShowSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static src.cardCollection.set.Set.SET_SIZE;

public class Player {
    private int playerID;
    private String name;
    private List<Set> setsCollected = new LinkedList<>();
    private Scanner scanner = new Scanner(System.in);

    public Player(int playerID, String name) {
        this.playerID = playerID;
        this.name = name;
    }

    public void collectSet(Set set) {
        setsCollected.add(set);
    }

    public void seeScore() {
        // TODO: lol what a gross temporary solution below for plurals...
        System.out.printf("%s (player %d) found %d set%s!\n", name, playerID, setsCollected.size(), setsCollected.size() == 1 ? "" : "s");
    }

    public Move getMove(Player player, Player[] players) {
        promptForMove();
        // TODO: Validate input
        int moveType_in = scanner.nextInt();
        // turn input int into an enum
        MoveType moveType = MoveType.valueOf(moveType_in);

        switch (moveType) {
            case SELECT_SET:
                return getSelectSet(player, players);
            case SHOW_SET:
                return new ShowSet(player);
            case END_GAME:
                return new EndGame(player);
            case DRAW_3:
                return new DrawThree(player);
            default:
                throw new UnsupportedOperationException("You failed to enter a valid move number.");
        }
    }

    private void promptForMove() {
        System.out.println("Enter 0 if you would like to select a set.");
        System.out.println("Enter 1 to see a set.");
        System.out.println("Enter 2 to end game now.");
        System.out.println("Enter 3 for 3 more cards.");
    }

    // TODO: various types of moves... one would be to display 3 more cards... allow this indefinitely? yes. but also show if they ask if there is a possible set :) but wait... can i display a board that big?
    private Move getSelectSet(Player player, Player[] players) {
        System.out.println("Enter your player number. ");
        // TODO: this player resetting is just temporary until threading works.
        player = players[scanner.nextInt() - 1];
        System.out.println("Enter the 3 cards in your set. ");

        int[] cardIDs = new int[SET_SIZE];
        for (int i = 0; i < SET_SIZE; i++)
            cardIDs[i] = scanner.nextInt();

        return new SelectSet(player, cardIDs);
    }

    public List<Set> getSetsCollected() {
        return setsCollected;
    }

    public int getNSetsCollected() {
        return setsCollected.size();
    }
}
