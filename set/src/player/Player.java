package src.player;

import src.cardCollection.set.Set;
import src.move.Move;
import src.move.movesToDo.MoveQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private int ID;
    private String name;
    private MoveQueue movesToDo = new MoveQueue();
    private List<Set> setsCollected = new LinkedList<>();
    private Scanner scanner = new Scanner(System.in);

    public Player(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public void collectSet(Set set) {
        setsCollected.add(set);
    }

    // TODO: Maybe this should be elsewhere... player shouldn't be responsible for reliably saying their score? idk...
    public void seeScore() {
        // TODO: lol what a gross temporary solution below for plurals...
        System.out.printf("%s (player %d) found %d set%s!\n", name, ID, setsCollected.size(), setsCollected.size() == 1 ? "" : "s");
    }

    public int giveMoveType() {
        return scanner.nextInt();
    }

    public int giveMoveRequestResponse() {
        return scanner.nextInt();
    }

    public boolean hasMove() {
        return !movesToDo.isEmpty();
    }

    public List<Set> getSetsCollected() {
        return setsCollected;
    }

    public int getNSetsCollected() {
        return setsCollected.size();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void addMoveToQueue(Move move) {
        movesToDo.addMove(move);
    }

    public Move getNextMoveFromQueue() {
        return movesToDo.getNext();
    }
}
