package src.move.moves;

import src.card.Card;
import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.cardCollection.set.Set;
import src.move.MoveRunner;
import src.move.MoveType;
import src.move.Move;
import src.player.Players;

import static src.cardCollection.board.Board.BASE_BOARD_SIZE;
import static src.cardCollection.board.Dealer.addToBoardFromDeck;
import static src.cardCollection.set.Set.SET_SIZE;

public class SelectSet extends Move implements MoveRunner {
    private int[] cardPositions;

    public SelectSet(int playerID, int[] cardPositions) {
        super(playerID);
        this.cardPositions = cardPositions;
    }

    @Override
    public void enactMove(Board board, Deck deck, Players players) {
        var setCards = new Card[3];
        int index = 0;
        for (int cardPosition: cardPositions)
            setCards[index++] = board.removeCard(cardPosition);

        players.getPlayer(getPlayerID()).collectSet(new Set(setCards));

        if (board.size() < BASE_BOARD_SIZE && deck.size() >= SET_SIZE)
            addToBoardFromDeck(SET_SIZE, board, deck);
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.SELECT_SET;
    }

    public int[] getCardPositions() {
        return cardPositions;
    }
}
