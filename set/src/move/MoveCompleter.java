package src.move;

import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.player.Player;

public class MoveCompleter {
    // class should not be instantiatable
    private MoveCompleter() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    public static Move move(Board board, Deck deck, Player player, Player[] players) {
        Move move;

        while (true) {
            try {
                move = player.getMove(player, players);
                assert(move != null);
                move.validateMove(board, deck);
                break;
            } catch(UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        move.enactMove(board, deck);
        return move;
    }

//    TODO:
    // move readMove into player class or atleast nextMove should be in player
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
}

