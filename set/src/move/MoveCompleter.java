package src.move;

public class MoveCompleter {
    // class should not be instantiatable
    private MoveCompleter() throws UnsupportedOperationException {
        // creates runtime error if reflection is used to bypass private
        throw new UnsupportedOperationException("Kindly stop using reflection to get around this being private xo");
    }

    public static Move completeMove() {
        Move move = readMove();
        validateMove(move);
        playMove(move);
        return move;
    }

    private static Move readMove() {
        return new Move(MoveType.CHOOSE_SET);
    }

    private static void validateMove(Move move) {
    }

    private static void playMove(Move move) {
    }
    // various types of moves... one would be to display 3 more cards... allow this indefinitely? yes. but also show if they ask if there is a possible set :)

}
