package src;

import src.game.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Server {
    private final static int GAME_PORT = 9090;
    private Map<Integer, Socket> toPlayerSockets = new HashMap<>();

    public void start() {
        Game game = new Game();

        List<Thread> playerListeners = new LinkedList<>();

        // open a port and accept message on it
        try (var serverSocket = new ServerSocket(GAME_PORT)) {
            System.out.println("serverSocket");
            int newPlayerID = 0;
            while (newPlayerID <= 2) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("ANOTHER clientSocket");
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String msg = reader.readLine();
                if (msg.equals("REQUEST_PLAYER_ID")) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    writer.write(newPlayerID + "\n");
                    writer.flush();
                    System.out.println("sent player ID");
                    toPlayerSockets.put(newPlayerID, clientSocket);

                    newPlayerID++;
                } else {
                    // playerClient sent a playerID
                    int playerID = parseInt(msg);

                    if (!toPlayerSockets.containsKey(playerID)) {
                        clientSocket.close();
                        continue;
                    }

                    Socket toClientSocket = toPlayerSockets.remove(playerID);
                    Thread t = new Thread(new ClientCommunicator(playerID, toClientSocket, clientSocket));
                    t.start();
                    playerListeners.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        cleanUpClientSockets();
        waitForPlayersToFinish(playerListeners);
        System.out.println("Laters.\n\n\n\n\n\n");
//        game.run();
    }

    private static void waitForPlayersToFinish(List<Thread> playerListeners) {
        for (Thread playerListener : playerListeners) {
            try {
                playerListener.join();
            } catch (InterruptedException e) {
                System.out.println("Not going to wait for that thread to finish"); }
        }
    }

    private void cleanUpClientSockets() {
        // toPlayerSockets should be empty UNLESS client never created a socket to listen to send to server
        for (Socket socket: toPlayerSockets.values()) {
            System.out.println("there was something to cleanup!");
            try {
                socket.close();
            } catch (IOException e) { continue; }
        }

        toPlayerSockets = null;
    }
}

class ClientCommunicator implements Runnable {
    private final int playerId;
    private final Socket toClientSocket;
    private final Socket fromClientSocket;

    public ClientCommunicator(int playerId, Socket toClientSocket, Socket fromClientSocket) {
        if (toClientSocket.isClosed()) {
            throw new RuntimeException("to client is closed");
        }
        if (fromClientSocket.isClosed()) {
            throw new RuntimeException("from client is closed");
        }
        this.playerId = playerId;
        this.toClientSocket = toClientSocket;
        this.fromClientSocket = fromClientSocket;
    }

    @Override
    public void run() {
        System.out.println("started a new thread");
        try (toClientSocket; fromClientSocket) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(toClientSocket.getOutputStream()))) {
                writer.write(playerId + "\n");
                writer.flush();
                System.out.println("sent playerID" + playerId);

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()))) {
                    // yucky
                    String line = reader.readLine();
                    while (line != null) {
                        System.out.println(line);
                        Thread.sleep(1000);
                        line = reader.readLine();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}