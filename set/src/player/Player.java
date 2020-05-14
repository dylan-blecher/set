package src.player;

public class Player {
    private int playerCount;
    private String name;
    int nSetsFound = 0;
    // TODO: store exact cards collected? should generalise deck to CardList and use inheritence :)

    public Player(int playerCount, String name) {
        this.playerCount = playerCount;
        this.name = name;
    }
}
