package src.server;

import src.action.Action;
import src.action.ConsensusManager.ConsensusManager;
import src.action.PlayerAction;
import src.action.PlayerActionSelectSet;
import src.action.actionQueue.SynchronisedActionQueue;
import src.cardCollection.board.Board;
import src.game.Game;
import src.game.Result;
import src.networkHelpers.Interactor;
import src.player.Player;
import src.player.Players;
import src.proto.AllProtos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author dylanblecher
 * Run the server for Set for the clients to communicate with
 */

public class Server {
    private final static int GAME_PORT = 9090;
    private final int N_PLAYERS = 2;
    private static final Map<Integer, Interactor> playerClientSockets = new HashMap<>();

    public void start() {
        List<Thread> playerListeners       = new LinkedList<>();
        SynchronisedActionQueue actions    = new SynchronisedActionQueue();
        Map<Integer, Player> activePlayers = new HashMap<>();

        // this is used only temporarily to setup the game
        Map<Integer, Socket> toPlayerClientWriters = new HashMap<>();

        // Keep opening ports and accept messages on it until we have enough players
        try (ServerSocket serverSocket = new ServerSocket(GAME_PORT)) {
            int newPlayerID = 0;
            int nPlayers = 0;
            while (nPlayers < N_PLAYERS) {
                Socket clientSocket = serverSocket.accept();

                // read message from client (deserialise from proto)
                // it will either be the player requesting to join, or confirming joining
                AllProtos.ClientRequest request = readClientRequest(clientSocket.getInputStream());
                if (request.hasJoinGame()) {
                    // initial request to join game from client
                    AllProtos.ServerResponse sendPlayerIDProto = buildSendPlayerIDProto(newPlayerID);
                    OutputStream streamToClient = clientSocket.getOutputStream();
                    writeServerResponse(sendPlayerIDProto, streamToClient);
                    toPlayerClientWriters.put(newPlayerID, clientSocket);

                    String playerName = request.getJoinGame().getName();
                    Player newPlayer = new Player(newPlayerID, playerName);
                    activePlayers.put(newPlayerID, newPlayer);

                    newPlayerID++;
                } else if (request.hasConfirmPlayerID()) {
                    // final request to join game from client, confirm receipt of their ID
                    int playerID = request.getConfirmPlayerID().getPlayerID();

                    if (!toPlayerClientWriters.containsKey(playerID)) {
                        clientSocket.close();
                        continue;
                    }

                    Socket toPlayerSocket = toPlayerClientWriters.remove(playerID);
                    Interactor playerInteractor = new Interactor(clientSocket.getInputStream(), toPlayerSocket.getOutputStream());
                    playerClientSockets.put(playerID, playerInteractor);

                    // Create a thread to run communication with each player in a separate thread.
                    Thread t = new Thread(new ClientCommunicator(playerID, toPlayerSocket.getOutputStream(), clientSocket.getInputStream(), actions));
                    t.start();
                    playerListeners.add(t);
                    nPlayers++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        cleanUpFailedClients(toPlayerClientWriters, activePlayers);

        // Run the game once all players are in.
        Game game = new Game(activePlayers, actions);
        game.run();

        waitForPlayersToFinish(playerListeners);
        System.out.println("Laters.\n\n\n\n\n\n");
    }

    public static AllProtos.ClientRequest readClientRequest(InputStream streamFromClient) throws IOException {
        return AllProtos.ClientRequest.parseDelimitedFrom(streamFromClient);
    }

    public static void writeServerResponse (AllProtos.ServerResponse responseProto, OutputStream streamToClient) throws IOException {
        responseProto.writeDelimitedTo(streamToClient);
    }

    private AllProtos.ServerResponse buildSendPlayerIDProto(int playerID) {
        return AllProtos.ServerResponse
                .newBuilder()
                .setPlayerID(playerID)
                .build();
    }

    private static void waitForPlayersToFinish(List<Thread> playerListeners) {
        for (Thread playerListener : playerListeners) {
            try {
                playerListener.join();
            } catch (InterruptedException e) {
                System.out.println("Not going to wait for that thread to finish"); }
        }
    }

    private void cleanUpFailedClients(Map<Integer, Socket> toPlayerClients, Map<Integer, Player> activePlayers) {
        // toPlayerClients should be empty UNLESS client never created a socket to listen to send to server
//        TODO: abstract this in the same way i did in playerClient with SocketWriter and SocketReader
        for (int playerID: toPlayerClients.keySet()) {
            activePlayers.remove(playerID);
            System.out.println("there was something to cleanup!");
            try {
                toPlayerClients.get(playerID).close();
            } catch (IOException e) { continue; }
        }

        toPlayerClients = null;
    }

    /**
     * The helper functions below construct and send messages (protos) to the players
     */

    public static void tellPlayer(PlayerAction action, String errorMessage) throws IOException {
        int playerID = action.getPlayerID();
        OutputStream streamToClient = playerClientSockets.get(playerID).getStreamToClient();
        AllProtos.ServerResponse errorMessageProto = buildErrorMessage(errorMessage);
        writeServerResponse(errorMessageProto, streamToClient);
    }

    private static AllProtos.ServerResponse buildErrorMessage(String errorMessage) {
        return AllProtos.ServerResponse
                .newBuilder()
                .setErrorMessage(errorMessage)
                .build();
    }

    public static void sendStateForDisplay(Board board, Players players) {
        // build state proto
        AllProtos.ServerResponse stateProto = AllProtos.ServerResponse
                .newBuilder()
                .setState(buildStateProto(board, players))
                .build();

        // send state to all players
        sendServerResponseToAllPlayers(stateProto, players);
    }

    // send an error message to all active players
    public static void sendErrorMessageToPlayers(String errorMessage, Players players) {
        AllProtos.ServerResponse errorMsgProto = buildErrorMessage(errorMessage);
        sendServerResponseToAllPlayers(errorMsgProto, players);
    }

    public static void sendResultToPlayers(Result result, Players players) {
        // build result proto
        AllProtos.ServerResponse resultProto = AllProtos.ServerResponse
                .newBuilder()
                .setResult(result.getProto())
                .build();

        // send result to all players
        sendServerResponseToAllPlayers(resultProto, players);
    }

    public static void sendRevealedSet(java.util.Set<Integer> revealedSet, Board board, Players players) {
        // build revealed set proto
        AllProtos.ServerResponse revealedSetProto = AllProtos.ServerResponse
                .newBuilder()
                .setRevealedSet(getRevealedSetProto(revealedSet))
                .build();

        // send revealed set to all players
        sendServerResponseToAllPlayers(revealedSetProto, players);
        System.out.println("sent revealedSet!");
    }

    private static AllProtos.RevealedSet getRevealedSetProto(Set<Integer> revealedSet) {
        if (revealedSet == null) return AllProtos.RevealedSet.newBuilder().build();
        else return AllProtos.RevealedSet.newBuilder().addAllCardPositions(revealedSet).build();
    }

    private static void sendServerResponseToAllPlayers(AllProtos.ServerResponse responseProto, Players players) {
        for (int playerID: players.getActivePlayers().keySet()) {
            OutputStream streamToClient = playerClientSockets.get(playerID).getStreamToClient();
            try {
                writeServerResponse(responseProto, streamToClient);
            } catch (IOException e) {
//                TODO: handle this!
                e.printStackTrace();
            }
        }
    }

    private static AllProtos.State buildStateProto(Board board, Players players) {
        return AllProtos.State
                .newBuilder()
                .addAllBoard(board.getCardsProto())
                .setConsensuses(ConsensusManager.getConsensusesProto(players.getNActivePlayers()))
                .addAllActivePlayers(players.getActivePlayersProto())
                .build();
    }
}

/**
 * Interact with 1 player in a thread to receive player actions and send responses (e.g. the board)
 */
class ClientCommunicator implements Runnable {
    private final int playerId;
    private final OutputStream streamToClient;
    private final InputStream streamFromClient;
    private final SynchronisedActionQueue actions;

    public ClientCommunicator(int playerId, OutputStream streamToClient, InputStream streamFromClient, SynchronisedActionQueue actions) {

        this.playerId = playerId;
        this.streamToClient = streamToClient;
        this.streamFromClient = streamFromClient;
        this.actions = actions;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // deserialize action and add to action queue
                AllProtos.ClientRequest request = Server.readClientRequest(streamFromClient);

                if (! request.hasAction()) {
                    continue;
                }
                Action action = null;

                AllProtos.Action actionProto = request.getAction();
                switch (actionProto.getType()) {
                    case REQUEST_SHOW_SET:
                    case REQUEST_DRAW_THREE:
                    case LEAVE_GAME:
                        action = new PlayerAction(actionProto);
                        break;
                    case SELECT_SET:
                        action = new PlayerActionSelectSet(actionProto);
                }

                assert(action != null);

                actions.addAction(action);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}