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

public class Server {
    private final static int GAME_PORT = 9090;
    private static final Map<Integer, Interactor> playerClientSockets = new HashMap<>();

    public void start() {
        List<Thread> playerListeners       = new LinkedList<>();
        SynchronisedActionQueue actions    = new SynchronisedActionQueue();
        Map<Integer, Player> activePlayers = new HashMap<>();

        // this is used only temporarily to setup
        Map<Integer, Socket> toPlayerClientWriters = new HashMap<>();

        // open a port and accept message on it
        try (ServerSocket serverSocket = new ServerSocket(GAME_PORT)) {
            System.out.println("serverSocket");
            int newPlayerID = 0;
            int nPlayers = 0;
            while (nPlayers < 1) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("ANOTHER clientSocket");

//                ByteBuffer bf = ByteBuffer.allocate(1024);
//                BufferedInputStream fromClient = new BufferedInputStream(clientSocket.getInputStream());
//                while (true) {
//                    int b = fromClient.read();
//                    if (b == 0) {
//                        break;
//                    }
//                    bf.put((byte)b);
//                }
//                bf.flip();

                AllProtos.ClientRequest request = readClientRequest(clientSocket.getInputStream());
                System.out.println(request);
                // could use the following to get the proto message type, but this won't work in Python:
                // request.getDescriptorForType().getName()
                System.out.println((request.getDescriptorForType().getName()));
                if (request.hasJoinGame()) {
                    AllProtos.ServerResponse sendPlayerIDProto = buildSendPlayerIDProto(newPlayerID);
                    OutputStream streamToClient = clientSocket.getOutputStream();
                    writeServerResponse(sendPlayerIDProto, streamToClient);
                    System.out.println("sent player ID");
                    toPlayerClientWriters.put(newPlayerID, clientSocket);

                    String playerName = request.getJoinGame().getName();
                    Player newPlayer = new Player(newPlayerID, playerName);
                    activePlayers.put(newPlayerID, newPlayer);

                    newPlayerID++;
                } else if (request.hasConfirmPlayerID()) {
                    System.out.println("client confirmed their playerID");
                    // playerClient sent a playerID
                    int playerID = request.getConfirmPlayerID().getPlayerID();

                    if (!toPlayerClientWriters.containsKey(playerID)) {
                        clientSocket.close();
                        continue;
                    }

                    Socket toPlayerSocket = toPlayerClientWriters.remove(playerID);
                    Interactor playerInteractor = new Interactor(clientSocket.getInputStream(), toPlayerSocket.getOutputStream());
                    playerClientSockets.put(playerID, playerInteractor);
                    Thread t = new Thread(new ClientCommunicator(playerID, toPlayerSocket.getOutputStream(), clientSocket.getInputStream(), actions));
                    t.start();
                    playerListeners.add(t);
                    nPlayers++;
                }
//                System.out.println(action.proto);
//                int x = 3;
//                while (x != 4) continue;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        cleanUpFailedClients(toPlayerClientWriters, activePlayers);
        // get moves from players
        System.out.println("run game now");
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
        System.out.println("SENT STATE!");
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

    public static void sendRevealedSet(java.util.Set<Integer> revealedSet, Players players) {
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

    private static void sendServerResponseToAllPlayers(AllProtos.ServerResponse stateProto, Players players) {
        for (int playerID: players.getActivePlayers().keySet()) {
            OutputStream streamToClient = playerClientSockets.get(playerID).getStreamToClient();
            try {
                writeServerResponse(stateProto, streamToClient);
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
        System.out.println("started a new thread");
        while (true) {
            try {
                // deserialize action and add to action queue
                AllProtos.ClientRequest request = Server.readClientRequest(streamFromClient);

                if (! request.hasAction()) {
                    // TODO: instead, send response saying you needa send an action and ya didn't
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

                System.out.println("player interactor about to take control of actions");
                actions.addAction(action);

                // TODO: the bqelow if statement breaks the player process with an infinite null loop... why? because there are 2 threads in the player process that are still running even after connection is shut off... fix this!
//                if (action.getType() == LEAVE_GAME) {
//                    toPlayerClient.close();
//                        fromPlayerClient.close();
//                    break;
//                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}