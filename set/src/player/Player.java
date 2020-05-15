package src.player;

import src.cardCollection.set.Set;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private int playerCount;
    private String name;
    int nSetsFound = 0;
    private List<Set> setsCollected = new LinkedList<>();

    // TODO: store exact cards collected? should generalise deck to CardList and use inheritence :)

    public Player(int playerCount, String name) {
        this.playerCount = playerCount;
        this.name = name;
    }

    public void collectSet(Set set) {
        setsCollected.add(set);
        nSetsFound++;
    }
}
