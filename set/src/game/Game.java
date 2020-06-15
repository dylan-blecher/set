package src.game;

import src.action.Action;
import src.action.ActionEnactor;
import src.action.PlayerAction;
import src.action.actionQueue.SynchronisedActionQueue;
import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.player.Player;
import src.player.Players;

import java.io.IOException;
import java.util.Map;

import static src.action.ActionType.SELECT_SET;
import static src.action.ActionType.SHOW_SET;
import static src.cardCollection.board.Dealer.setupBoard;
import static src.cardCollection.deck.DeckBuilder.buildDeck;
import static src.game.stateValidator.setExists;
import static src.game.stateValidator.validateAction;
import static src.server.Server.*;

/**
 * @author dylanblecher
 * blecher.dylan@gmail.com
 * April-June 2020
 * Set is a multi-player real-time card game designed by Marsha Falco in 1974.
 * Checkout the rules here:
 * https://en.wikipedia.org/wiki/Set_(card_game)
 */
public class Game {
    private Players players;
    private Board board;
    private Deck deck;
    private SynchronisedActionQueue actions;

    public Game(Map<Integer, Player> activePlayers, SynchronisedActionQueue actions) {
         setupGame(activePlayers, actions);
    }

    /**
     * @param activePlayers players connected to the server
     * @param actions       queue of actions to read action from
     * The reason this is not immediately initialised with the attributes above is
     * in case I want to add replay() functionality, where it sets up a new game :)
     */
    private void setupGame(Map<Integer, Player> activePlayers, SynchronisedActionQueue actions) {
        this.players          = new Players(activePlayers);
        this.deck             = buildDeck();
        this.board            = setupBoard(this.deck);
        this.actions          = actions;
    }

    /**
     * run the game
     */
    public void run() {
        while (gameIsNotOver()) {
            sendStateForDisplay(board, players);

            Action action = actions.getNext();

            try {
                validateAction(action, board, deck);
            } catch(UnsupportedOperationException e) {
                warnInvalidity(action, e.getMessage(), players);
                continue;
            }

            ActionEnactor.enact(action, board, deck, players, actions);
        }

        Result result = new Result(players.getActivePlayers(), players.getInactivePlayers());
        sendResultToPlayers(result, players);
    }

    /**
     * Warn the player(s) that the action was invalid.
     * @param action the action that was invalid
     * @param errorMessage  the reason the action was invalid
     * @param players the players to send the message to
     */
    private void warnInvalidity(Action action, String errorMessage, Players players) {
        try {
            if (action.getType() == SELECT_SET) {
                tellPlayer((PlayerAction) action, errorMessage);
            } else if (action.getType() == SHOW_SET){
                sendErrorMessageToPlayers(errorMessage, players);
            } else {
                System.out.println(errorMessage);
            }
        } catch (IOException e) {
            // TODO: If the player doesn't receive the errorMessage, handle it -
            //  implement re-join game if connection cuts - deadletter queue.
            return;
        }
    }

    /**
     * @return if the game can continue or not
     */
    private boolean gameIsNotOver() {
        return (deck.nCards() > 0 || setExists(board)) && players.getNActivePlayers() > 0;
    }
}