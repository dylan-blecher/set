package src.move.movesToDo;

import src.move.Move;

public interface MovesToDo {
    Move getNext();
    void addMove(Move moveRunner);
    boolean isEmpty();
}
