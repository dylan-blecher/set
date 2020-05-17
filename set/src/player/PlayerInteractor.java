package src.player;
// this class serves to interact with the player
// for example, PlayerInteractor is responsible for finding out the player's moves

import src.move.Move;
import src.move.MoveType;
import src.move.moves.*;

import java.util.Scanner;

import static src.cardCollection.set.Set.SET_SIZE;

public class PlayerInteractor {
    private static Scanner scanner = new Scanner(System.in);

    // class should not be instantiatable
    private PlayerInteractor() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    public static void promptForMove() {
        System.out.println("Enter 0 if you would like to select a set.");
        System.out.println("Enter 1 to request to see a set.");
        System.out.println("Enter 2 to leave game now.");
        System.out.println("Enter 3 to request 3 more cards.");
    }

    // returns a Move or null if the move was invalid
    public static Move getMove(Player player) {
        promptForMove();
        MoveType moveType = getMoveType(player.giveMoveType());

        Move move;
        switch (moveType) {
            case SELECT_SET:
                move = getSelectSet(player.getID());
                break;
            case REQUEST_SHOW_SET:
                move = new RequestShowSet(player.getID());
                break;
            case LEAVE_GAME:
                move = new LeaveGame(player.getID());
                break;
            case REQUEST_DRAW_THREE:
                move = new RequestDrawThree(player.getID());
                break;
            default:
                move = null;
        }

        player.addMoveToQueue(move);
        return player.getNextMoveFromQueue();
    }

    // TODO: various types of moves... one would be to display 3 more cards... allow this indefinitely? yes. but also show if they ask if there is a possible set :) but wait... can i display a board that big?
    private static Move getSelectSet(int playerID) {
        System.out.println("Enter your player number. ");
        // TODO: this player resetting is just temporary until threading works.
        playerID = scanner.nextInt() - 1;
        System.out.println("Enter the 3 cards in your set. ");

        int[] cardIDs = new int[SET_SIZE];
        for (int i = 0; i < SET_SIZE; i++)
            cardIDs[i] = scanner.nextInt();

        return new SelectSet(playerID, cardIDs);
    }

    private static MoveType getMoveType(int moveInt) {
        // TODO: Validate input
        // turn input int into an enum
        return MoveType.valueOf(moveInt);
    }

    private static void promptForMoveRequestResponse(Player actingPlayer, MoveType request) {
        String message = String.format("%s (player %d) has requested to ", actingPlayer.getName(), actingPlayer.getID() + 1);

        switch (request) {
            case REQUEST_SHOW_SET:
                message += "have a set on the board revealed.";
                break;
            case REQUEST_DRAW_THREE:
                message += "have three more cards drawn onto the board.";
        }

        System.out.println(message);
        System.out.println("Enter 4 to agree to request.");
        System.out.println("Enter 5 to disagree to request.");
    }

    public static Move getMoveRequestResponse(Player player, RequestMove request) {
        promptForMoveRequestResponse(player, request.getMoveType());
        MoveType response = getMoveType(player.giveMoveRequestResponse());
        return new RespondToRequest(player.getID(), response, request);
    }
}
