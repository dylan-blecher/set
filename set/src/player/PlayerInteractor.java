package src.player;
// this class serves to interact with the player
// for example, PlayerInteractor is responsible for finding out the player's actions

import src.action.ActionType;
import src.action.PlayerAction;
import src.action.PlayerActionSelectSet;

import java.util.Scanner;

import static src.cardCollection.set.Set.SET_SIZE;

public class PlayerInteractor {
    private static Scanner scanner = new Scanner(System.in);

    // class should not be instantiatable
    private PlayerInteractor() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    public static void promptForAction() {
        System.out.println("Enter 0 if you would like to select a set.");
        System.out.println("Enter 1 to request to see a set.");
        System.out.println("Enter 2 to leave game now.");
        System.out.println("Enter 3 to request 3 more cards.");
    }

    // returns an action or null if the action was invalid
    public static PlayerAction getAction(int playerID) {
        promptForAction();
        ActionType actionType = getActionType(Player.giveActionType());

        PlayerAction action;
        switch (actionType) {
            case SELECT_SET:
                action = getSelectSet(playerID, actionType);
                break;
            case REQUEST_SHOW_SET:
            case REQUEST_DRAW_THREE:
            case LEAVE_GAME:
                action = new PlayerAction(actionType, playerID);
                break;
            default:
                action = null;
        }

        return action;
    }

    // TODO: various types of actions... one would be to display 3 more cards... allow this indefinitely? yes. but also show if they ask if there is a possible set :) but wait... can i display a board that big?
    private static PlayerAction getSelectSet(int playerID, ActionType actionType) {
        System.out.println("Enter the 3 cards in your set. ");

        int[] cardPositions = new int[SET_SIZE];
        for (int i = 0; i < SET_SIZE; i++)
            cardPositions[i] = scanner.nextInt();

        return new PlayerActionSelectSet(actionType, playerID, cardPositions);
    }

    private static ActionType getActionType(int actionInt) {
        // TODO: Validate input
        // turn input int into an enum
        return ActionType.valueOf(actionInt);
    }

    public static String readPlayerName() {
        System.out.println("Name of player?");
        return scanner.nextLine();
    }

    public static void farewellPlayer(int playerID) {
        System.out.println("Adios muchacho :)");
    }
}
