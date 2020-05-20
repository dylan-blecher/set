package src;

import src.game.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class runSet {
    private final static int GAME_PORT = 9090;

    public static void main(String[] args) {
        Game game = new Game();

        List<Thread> playerListeners = new LinkedList<>();

        // open a port and accept message on it
        try (var serverSocket = new ServerSocket(GAME_PORT)) {
            System.out.println("ANOTHER serverSocket");
            int nPlayers = 0;
            while (nPlayers <= 2) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("ANOTHER clientSocket");
                Thread t = new Thread(new PlayerListener(nPlayers++, clientSocket));
                t.start();
                playerListeners.add(t);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        for (Thread playerListener : playerListeners) {
            try {
                playerListener.join();
            } catch (InterruptedException e) {
                continue;
            }
        }
        System.out.println("Laters.\n\n\n\n\n\n");
//        game.run();
    }
}

class PlayerListener implements Runnable {
    private final int playerId;
    private final Socket clientSocket;

    public PlayerListener(int playerId, Socket clientSocket) {
        this.playerId = playerId;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("started a new thread");
        try (clientSocket) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                // yucky
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    Thread.sleep(1000);
                    line = reader.readLine();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}