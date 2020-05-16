package src.move.moves;

import src.card.Card;
import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.cardCollection.set.Set;
import src.move.Move;
import src.move.PlayerMove;
import src.move.MoveType;
import src.player.Player;

import java.util.LinkedList;
import java.util.List;

import static src.cardCollection.board.Board.BASE_BOARD_SIZE;
import static src.cardCollection.board.Dealer.addToBoardFromDeck;
import static src.cardCollection.set.Set.SET_SIZE;
import static src.game.Referee.isSet;

public class SelectSet extends PlayerMove implements Move {
    private int[] cardIDs;

    public SelectSet(Player player, int[] cardIDs) {
        super(player);
        this.cardIDs = cardIDs;
    }

    @Override
    public void validateMove(Board board, Deck deck) {
        List<Card> potentialSet = new LinkedList<>();
        for (int cardID: cardIDs) {
            if (cardID >= board.size() || cardID < 0) {
                throw new UnsupportedOperationException("Card position ID entered is not on the board!");
            }
            potentialSet.add(board.getCard(cardID));
        }

        if (!isSet(potentialSet)) {
            throw new UnsupportedOperationException("The cards you selected do not form a set.");
        }
    }

    @Override
    public void enactMove(Board board, Deck deck) {
        List<Card> setCards = new LinkedList<>();
        // move each card from board to a list of cards which we will then turn into a set :)
        for (int cardID: cardIDs)
            setCards.add(board.getCard(cardID));

        for (int cardID: cardIDs)
            board.removeCard(cardID);

        getPlayer().collectSet(new Set(setCards));

        if (board.size() < BASE_BOARD_SIZE && deck.size() >= SET_SIZE)
            addToBoardFromDeck(SET_SIZE, board, deck);
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.SELECT_SET;
    }
}