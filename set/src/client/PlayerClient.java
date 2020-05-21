package src.client;

import src.action.Action;
import src.action.actionQueue.ActionQueue;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

class SocketWriter {
    BufferedWriter writer;
    Socket socket;
    public SocketWriter(Socket socket) throws IOException {
        this.socket = socket;
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void write(String s) throws IOException {
        writer.write(s + "\n");
        writer.flush();
    }

    public void write(Integer num) throws IOException {
        this.write(num.toString());
    }

    public void close() throws IOException {
        writer.close();
        if (!socket.isClosed()) socket.close();
    }
}

class SocketReader {
    BufferedReader reader;
    Socket socket;
    public SocketReader(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void close() throws IOException {
        System.out.println("closereader");
        reader.close();
        if (!socket.isClosed()) socket.close();
    }
}

public class PlayerClient {
    private final static int GAME_PORT = 9090;
    private final static ActionQueue actions = new ActionQueue();

    public static void main(String[] args) throws IOException, InterruptedException {
        Optional<Integer> playerID = Optional.empty();
        SocketReader fromServer;
        SocketWriter toServer;

        try {
            InetAddress host = InetAddress.getLocalHost();
            Socket fromServerSocket = new Socket(host.getHostName(), GAME_PORT);
            fromServer = new SocketReader(fromServerSocket);

            // writing once off to the thing that will read just to establish a connection
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fromServerSocket.getOutputStream()))) {
                System.out.println("REQUEST_PLAYER_ID");
                writer.write("REQUEST_PLAYER_ID\n");
                writer.flush();

                // exponential backoff yay
                long delay = 25;
                while (playerID.isEmpty()) {
                    String msg = fromServer.readLine();
                    System.out.println(msg);
                    if (msg.equals("REQUEST_PLAYER_ID")) {
                        System.out.println("TRYING TO GET PLAYER ID");
                        writer.write(msg + "\n");
                        writer.flush();
                        Thread.sleep(delay);
                        delay *= 2;
                    } else playerID = Optional.of(Integer.valueOf(msg));
                }
            }

            try {
                toServer = new SocketWriter(new Socket(host.getHostName(), GAME_PORT));
                toServer.write(playerID.get());
            } catch (IOException e) {
                fromServer.close();
                throw e;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

        // deals with stdin moves
        Thread t = new Thread(new ActionQueueDrainer(actions, toServer));
        t.start();

        while (true) {
            Action action = src.player.PlayerInteractor.getAction(playerID.get());
            if (action != null) {
                synchronized (actions) {
                    actions.addAction(action);
                    if (actions.size() == 1) actions.notify();
                }
            }
        }
        // TODO: call this when above loop ain't infinite t.join();
        // TODO: close() (my function close) reader and writer and
    }
}

// thread that gets actions by scanning stdin and adds to actions queue
class ActionQueueDrainer implements Runnable {
    private final SocketWriter toServer;
    private final ActionQueue actions;

    public ActionQueueDrainer(ActionQueue actions, SocketWriter toServer) {
        this.actions = actions;
        this.toServer = toServer;
    }

    @Override
    public void run() {
// other thread sends a move to server as soon as there is one
        while (true) {
            synchronized (actions) {
                if (actions.isEmpty()) {
                    try {
                        actions.wait();
                    } catch (InterruptedException e) {
                        continue;
                    }
                }

                Action action = actions.getNext();
                while (true) {
                    try {
                        sendAction(action);
                        break;
                    } catch (IOException e) {
                        // TODO: handle this exception
                         continue;
                    }
                }
            }
        }
    }

    private void sendAction(Action action) throws IOException {
        toServer.write("player sent an action " + action.getType());
    }
}