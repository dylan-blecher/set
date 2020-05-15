package src.move;

// TODO: create end game option where it just prints out the current winner summary
public enum MoveType {
    DRAW_3(3),
    CHOOSE_SET(0),
    SHOW_SET(1);

    private int value;

    MoveType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}