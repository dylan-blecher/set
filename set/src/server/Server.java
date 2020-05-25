package src.server;

import src.action.Action;
import src.action.PlayerAction;
import src.action.actionQueue.SynchronisedActionQueue;
import src.game.Game;
import src.networkHelpers.Interactor;
import src.networkHelpers.SocketReader;
import src.networkHelpers.SocketWriter;
import src.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static src.server.JsonConverter.deserialise;
import static src.server.JsonConverter.getJsonString;

public class Server {
    private final static int GAME_PORT = 9090;
    private static final Map<Integer, Interactor> playerClientInteractors = new HashMap<>();

    public void start() {
        List<Thread> playerListeners       = new LinkedList<>();
        SynchronisedActionQueue actions    = new SynchronisedActionQueue();
        Map<Integer, Player> activePlayers = new HashMap<>();

        // this is used only temporarily to setup
        Map<Integer, SocketWriter> toPlayerClientWriters = new HashMap<>();

        // open a port and accept message on it
        try (ServerSocket serverSocket = new ServerSocket(GAME_PORT)) {
            System.out.println("serverSocket");
            int newPlayerID = 0;
            int nPlayers = 0;
            while (nPlayers < 2) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("ANOTHER clientSocket");
                SocketReader fromPlayerClient = new SocketReader(clientSocket);

                SocketMessage socketMessage = (SocketMessage) deserialise(fromPlayerClient.readLine(), SocketMessage.class);
                String command = socketMessage.getMessageType();
                if (command.equals("REQUEST_PLAYER_ID")) {
                    SocketWriter toPlayerClient = new SocketWriter(clientSocket);
                    toPlayerClient.write(getJsonString(new SocketMessage("GIVE_PLAYER_ID", newPlayerID)));
                    System.out.println("sent player ID");
                    toPlayerClientWriters.put(newPlayerID, toPlayerClient);
                    activePlayers.put(newPlayerID, new Player(newPlayerID, socketMessage.getMessage()));
                    newPlayerID++;
                } else if (command.equals("CONFIRM_PLAYER_ID")) {
                    // playerClient sent a playerID
                    int playerID = parseInt(socketMessage.getMessage());

                    if (!toPlayerClientWriters.containsKey(playerID)) {
                        clientSocket.close();
                        continue;
                    }

                    SocketWriter toPlayerClient = toPlayerClientWriters.remove(playerID);
                    Interactor playerInteractor = new Interactor(fromPlayerClient, toPlayerClient);
                    playerClientInteractors.put(playerID, playerInteractor);
                    Thread t = new Thread(new ClientCommunicator(playerID, toPlayerClient, fromPlayerClient, actions));
                    t.start();
                    playerListeners.add(t);
                    nPlayers++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        cleanUpFailedClients(toPlayerClientWriters, activePlayers);
        // get moves from players
        Game game = new Game(activePlayers, actions);
        game.run();

        waitForPlayersToFinish(playerListeners);
        System.out.println("Laters.\n\n\n\n\n\n");
    }

    private static void waitForPlayersToFinish(List<Thread> playerListeners) {
        for (Thread playerListener : playerListeners) {
            try {
                playerListener.join();
            } catch (InterruptedException e) {
                System.out.println("Not going to wait for that thread to finish"); }
        }
    }

    private void cleanUpFailedClients(Map<Integer, SocketWriter> toPlayerClients, Map<Integer, Player> activePlayers) {
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

    public static void tellPlayer(PlayerAction action, String errorMessage) {
        int playerID = action.getPlayerID();
        try {
            playerClientInteractors.get(playerID).getWriter().write(getJsonString(new SocketMessage("GIVE_ERROR_MESSAGE", errorMessage)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

class ClientCommunicator implements Runnable {
    private final int playerId;
    private final SocketWriter toPlayerClient;
    private final SocketReader fromPlayerClient;
    private final SynchronisedActionQueue actions;

    public ClientCommunicator(int playerId, SocketWriter toPlayerClient, SocketReader fromPlayerClient, SynchronisedActionQueue actions) {

        this.playerId = playerId;
        this.toPlayerClient = toPlayerClient;
        this.fromPlayerClient = fromPlayerClient;
        this.actions = actions;
    }

    @Override
    public void run() {
        System.out.println("started a new thread");
        try {
            while (true) {
                // deserialize action and add to action queue
                Action action = (Action) deserialise(fromPlayerClient.readLine(), Action.class);
                System.out.println("player interactor about to take control of actions");
                actions.addAction(action);

                // TODO: the below if statement breaks the player process with an infinite null loop... why? because there are 2 threads in the player process that are still running even after connection is shut off... fix this!
//                if (action.getType() == LEAVE_GAME) {
//                    toPlayerClient.close();
//                        fromPlayerClient.close();
//                    break;
//                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}