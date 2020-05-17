package src.game;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.move.MoveRunner;
import src.move.movesToDo.MoveQueue;
import src.move.movesToDo.MovesToDo;
import src.player.Players;

import static src.cardCollection.board.Dealer.setupBoard;
import static src.cardCollection.deck.DeckBuilder.buildDeck;
import static src.game.Referee.setExists;
import static src.game.Referee.validateMove;
import static src.move.MoveType.LEAVE_GAME;
import static src.player.PlayerInteractor.getMove;

public class Game {
    // TODO: change this players array to be a map from ID to player structure
    private Players players;
    private Board board;
    private Deck deck;
    private MovesToDo movesToDo;

    public Game() {
        setupGame();
    }

    // the reason this is not immediately initialised with the attributes above is
    // in case I want to add replay() functionality, where it sets up a new game :)
    private void setupGame() {
        this.players   = new Players();
        this.deck      = buildDeck();
        this.board     = setupBoard(this.deck);
        this.movesToDo = new MoveQueue();
    }

    public void run() {
        while (gameIsNotOver()) {
//            TODO: might not want to display board every time - only if the board changed since previous move...
            this.board.display();

            while (movesToDo.isEmpty()) {
                for (var player : players.getActivePlayers().values()) {
                    // can't model it perfectly yet... players will run int heir own thread so they can have a move
                    // without calling getMove(). But for now, they can't because it's not multi-threaded...
                    //if (player.hasMove()) {
                    movesToDo.addMove(getMove(player));
                }
            }

            MoveRunner moveRunner = movesToDo.getNext();
            // ignore invalid moveTypes
            if (moveRunner == null) continue;

            try {
                validateMove(moveRunner, board, deck);
            } catch(UnsupportedOperationException e) {
                System.out.println(e.getMessage());
                continue;
            }

            moveRunner.enactMove(board, deck, players);
            if (moveRunner.getMoveType() == LEAVE_GAME) break;
        }

        Result result = new Result(players.getActivePlayers(), players.getInactivePlayers());
        result.showResult();
    }

    private boolean gameIsNotOver() {
        return (deck.size() > 0 || setExists(board)) && players.getNActivePlayers() > 0;
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