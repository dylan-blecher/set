package src.player;

import src.cardCollection.set.Set;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private int ID;
    private String name;
    private List<Set> setsCollected = new LinkedList<>();
    private static Scanner scanner = new Scanner(System.in);

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
}
