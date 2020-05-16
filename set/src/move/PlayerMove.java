package src.move;

import src.player.Player;

// TODO: maybe interface is unnecessary and these should just be abstract classes
public abstract class PlayerMove implements Move {
    private final Player player;

    public PlayerMove(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
