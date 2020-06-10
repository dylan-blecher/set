package src.action;

import src.action.ConsensusManager.ConsensusManager;
import src.action.actionQueue.SynchronisedActionQueue;
import src.card.Card;
import src.cardCollection.board.Board;
import src.cardCollection.deck.Deck;
import src.cardCollection.set.Set;
import src.player.Players;

import static src.cardCollection.board.Board.BASE_BOARD_SIZE;
import static src.cardCollection.board.Dealer.addToBoardFromDeck;
import static src.cardCollection.set.Set.SET_SIZE;
import static src.game.stateValidator.findSet;
import static src.server.Server.sendRevealedSet;

public class ActionEnactor {
    // TODO: ENFORCE THAT THIS AND CONSENSUS MANAGER CANNOT BE INSTANTIATED WITH PRIVATE CONSTRUCTOR
    // TODO: not sure if i like the fact that I'm passing players around here!
    public static void enact(Action action, Board board, Deck deck, Players players, SynchronisedActionQueue actions) {
        ActionType actionType = action.getType();
        switch (actionType) {
            case LEAVE_GAME:
                enactLeaveGame((PlayerAction) action, players);
                break;
            case SELECT_SET:
                enactSelectSet((PlayerActionSelectSet) action, board, deck, players);
                break;
            case DRAW_THREE:
                enactDrawThree(board, deck);
                break;
            case SHOW_SET:
                enactShowSet(board, players, deck);
                break;
        }

        ConsensusManager.update(action);
        ConsensusManager.updateMoveQueue(players.getNActivePlayers(), actions);
    }

    private static void enactLeaveGame(PlayerAction playerAction, Players players) {
        int playerID = playerAction.getPlayerID();
        String name = players.getPlayer(playerID).getName();
        players.dropPlayerFromGame(playerID);
        int nPlayers = players.getNActivePlayers();

//        System.out.printf("%s (player %d) has left. %d ", name, playerID + 1, nPlayers);
//        System.out.println(nPlayers == 1 ? "player remains." : "players remain.");

    }

    private static void enactShowSet(Board board, Players players, Deck deck) {
        java.util.Set<Integer> setCardPositions = findSet(board).keySet();
        sendRevealedSet(setCardPositions, players);
        removeSetFromBoard(setCardPositions, board);
        replenishBoard(board, deck);
    }

    private static void enactSelectSet(PlayerActionSelectSet action, Board board, Deck deck, Players players) {
        Set set = removeSetFromBoard(action.getCardPositions(), board);
        System.out.println("PLAYERID: " + action.getPlayerID());
        players.getPlayer(action.getPlayerID()).collectSet(set);
        replenishBoard(board, deck);
    }

    private static Set removeSetFromBoard(Iterable<Integer> setCardPositions, Board board) {
        Card[] setCards = new Card[3];
        int index = 0;
        for (int cardPosition: setCardPositions)
            setCards[index++] = board.removeCard(cardPosition);
        return new Set(setCards);
    }

    private static void replenishBoard(Board board, Deck deck) {
        if (board.nCards() < BASE_BOARD_SIZE && deck.nCards() >= SET_SIZE) {
            System.out.println("ncards: " + board.nCards() + "base size: " + BASE_BOARD_SIZE);
            addToBoardFromDeck(SET_SIZE, board, deck);
        }
    }

    private static void enactDrawThree(Board board, Deck deck) {
        int nCardsToAdd = Math.min(board.nEmptySpots(), SET_SIZE);
        addToBoardFromDeck(nCardsToAdd, board, deck);
    }
}
