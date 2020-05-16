package src.player;

import src.cardCollection.set.Set;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private int playerID;
    private String name;
    private List<Set> setsCollected = new LinkedList<>();

    public Player(int playerID, String name) {
        this.playerID = playerID;
        this.name = name;
    }

    public void collectSet(Set set) {
        setsCollected.add(set);
    }

    public List<Set> getSetsCollected() {
        return setsCollected;
    }

    public int getNSetsCollected() {
        return setsCollected.size();
    }

    public void seeScore() {
        // TODO: lol what a gross temporary solution below for plurals...
        System.out.printf("%s (player %d) found %d set%s!\n", name, playerID, setsCollected.size(), setsCollected.size() == 1 ? "" : "s");
    }
}
