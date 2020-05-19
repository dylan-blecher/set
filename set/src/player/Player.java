package src.player;

import src.action.Action;
import src.action.actionQueue.ActionQueue;
import src.cardCollection.set.Set;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private int ID;
    private String name;
    private ActionQueue movesToDo = new ActionQueue();
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
        System.out.printf("%s (player %d) found %d set%s!\n", name, ID + 1, setsCollected.size(), setsCollected.size() == 1 ? "" : "s");
    }

    public int giveActionType() {
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

    public void addActionToQueue(Action playerAction) {
        movesToDo.addAction(playerAction);
    }

    public Action getNextActionFromQueue() {
        return movesToDo.getNext();
    }
}
