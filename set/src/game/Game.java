package src.game;

import src.action.Action;
import src.action.ActionEnactor;
import src.action.PlayerAction;
import src.action.actionQueue.SynchronisedActionQueue;
import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.player.Player;
import src.player.Players;

import java.util.Map;

import static src.action.ActionType.SELECT_SET;
import static src.cardCollection.board.Dealer.setupBoard;
import static src.cardCollection.deck.DeckBuilder.buildDeck;
import static src.game.stateValidator.setExists;
import static src.game.stateValidator.validateAction;
import static src.server.Server.tellPlayer;

public class Game {
    // TODO: change this players array to be a map from ID to player structure
//  TODO:  maybe players should be refactored to be a static class
    private Players players;
    private Board board;
    private Deck deck;
    private SynchronisedActionQueue actions;

    public Game(Map<Integer, Player> activePlayers, SynchronisedActionQueue actions) {
         setupGame(activePlayers, actions);
    }

    // the reason this is not immediately initialised with the attributes above is
    // in case I want to add replay() functionality, where it sets up a new game :)
    private void setupGame(Map<Integer, Player> activePlayers, SynchronisedActionQueue actions) {
        this.players          = new Players(activePlayers);
        this.deck             = buildDeck();
        this.board            = setupBoard(this.deck);
        this.actions          = actions;
    }

    public void run() {
        while (gameIsNotOver()) {
//            TODO: might not want to display board every time - only if the board changed since previous move...
            this.board.display();

            Action action = actions.getNext();

            try {
                validateAction(action, board, deck);
            } catch(UnsupportedOperationException e) {
                warnInvalidity(action, e.getMessage());
                continue;
            }

            ActionEnactor.enact(action, board, deck, players, actions);
        }

        Result result = new Result(players.getActivePlayers(), players.getInactivePlayers());
        result.showResult();
    }

    private void warnInvalidity(Action action, String errorMessage) {
        if (action.getType() == SELECT_SET) {
            tellPlayer((PlayerAction)action, errorMessage);
        } else {
            System.out.println(errorMessage);
        }
    }

    private boolean gameIsNotOver() {
        return (deck.nCards() > 0 || setExists(board)) && players.getNActivePlayers() > 0;
    }
}

//    TODO:
// look through all code and check if there are places where i'm passing the oG reference but i should be passing a copy, if i were to have responsibiities correct
// actionQueue class - has a queue. pass to queue which is private. part of game runner, not player. it's own package
// serialised = packing and unpacking
//    write AI's that are correct a percentage of the time and has delays
// action queue, players making their own moves, then threads
// change END_GAME to be DROP_OUT
// players communicate with eachother through the game (send request for 3 more)
// similar can't find set for SHOW_SET, no one gets it - discard pile
// all other players agree
// byzantine generals problem - timeouts - accept request if not in 10 seconds
// mutual exclusion for move Making
// how to add fixes without fucking the system'
// 2 threads, 1 queue per player - one player thread adds job, one player thread sends job to server
// queue is size 1 for now because we don't have blocking problem (waiting for server)
// protobuf (google product to send information to a server - serialisation library)
// graphics
// move should have a playerID field.