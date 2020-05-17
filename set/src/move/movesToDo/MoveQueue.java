package src.move.movesToDo;

import src.move.Move;

import java.util.LinkedList;
import java.util.Queue;

// To avoid the producer-consumer problem, we can only allow one move to take place at a time.
// So, let's keep a to do list of moves.
// Note: this is looking forward... Not necessary immediately, but it will be when working with threads.
public class MoveQueue implements MovesToDo {
    Queue<Move> q = new LinkedList<>();

    @Override
    public Move getNext() {
        return q.remove();
    }

    @Override
    public void addMove(Move move) {
        q.add(move);
    }

    @Override
    public boolean isEmpty() {
        return q.size() == 0;
    }
}


