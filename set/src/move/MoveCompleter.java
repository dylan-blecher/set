package src.move;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.moves.DrawThree;
import src.move.moves.EndGame;
import src.move.moves.SelectSet;
import src.move.moves.ShowSet;
import src.player.Player;

import java.util.Scanner;

import static src.cardCollection.set.Set.SET_SIZE;
import static src.move.MoveType.*;

public class MoveCompleter {
    // class should not be instantiatable
    private MoveCompleter() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    public static Move move(Board board, Deck deck, Player[] players) {
        Move move;

        while (true) {
            try {
                move = readMove(players);
                move.validateMove(board, deck);
                break;
            } catch(UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        move.enactMove(board, deck);
        return move;
    }

    private static Move readMove(Player[] players) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 0 if you would like to select a set");
        System.out.println("Enter 1 to see a set.");
        System.out.println("Enter 2 to end game now.");
        System.out.println("Enter 3 for 3 more cards.");

        int moveType = scanner.nextInt();

//        TODO: TRY TURN THIS INTO A SWITCH STATEMENT
        if (moveType == DRAW_3.getValue()) {
            return new DrawThree();
        } else if (moveType == SELECT_SET.getValue()) {
            System.out.println("Enter your player number. ");
            int playerID = scanner.nextInt() - 1;
            if (playerID >= players.length || playerID < 0) {
                throw new UnsupportedOperationException("You gave an invalid player number.");
            }

            System.out.println("Enter the 3 cards in your set. ");

            int[] cardIDs = new int[SET_SIZE];
            for (int i = 0; i < SET_SIZE; i++)
                cardIDs[i] = scanner.nextInt();

            return new SelectSet(cardIDs, players[playerID]);
        } else if (moveType == SHOW_SET.getValue()) {
            return new ShowSet();
        } else if (moveType == END_GAME.getValue()) {
            return new EndGame();
        } else {
//            TODO: create real type for this error...?
            throw new UnsupportedOperationException("You failed to enter 0 or 3");
        }
    }

    // TODO: various types of moves... one would be to display 3 more cards... allow this indefinitely? yes. but also show if they ask if there is a possible set :) but wait... can i display a board that big?

}
