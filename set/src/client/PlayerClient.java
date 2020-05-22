package src.client;

import src.action.Action;
import src.action.ActionType;
import src.action.PlayerAction;
import src.action.PlayerActionSelectSet;
import src.action.actionQueue.ActionQueue;
import src.networkHelpers.SocketReader;
import src.networkHelpers.SocketWriter;
import src.player.PlayerInteractor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

import static src.action.ActionType.LEAVE_GAME;
import static src.player.PlayerInteractor.farewellPlayer;
import static src.player.PlayerInteractor.readPlayerName;

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

            // once off (just to establish a connection) we will write to the thing that will read
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(fromServerSocket.getOutputStream()));
                String playerName = readPlayerName();
                System.out.println("REQUEST_PLAYER_ID");
                writer.write("REQUEST_PLAYER_ID" + "@" + playerName + "\n");
                writer.flush();

                // exponential backoff yay
                long delay = 25;
                while (!playerID.isPresent()) {
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
            } catch (Exception e) {
                if (writer != null) writer.close();
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

        Thread actionDrainer = new Thread(new ActionQueueDrainer(actions, toServer));
        actionDrainer.start();

        Thread feedbackGetter = new Thread(new FeedbackGetter(fromServer));
        feedbackGetter.start();

        // deals with stdin moves
        while (true) {
            PlayerAction action = PlayerInteractor.getAction(playerID.get());
            if (action != null) {
                synchronized (actions) {
                    actions.addAction(action);
                    System.out.println("adding action to playerqueue");
                    if (actions.size() == 1) {
                        System.out.println("player side notifying");
                        actions.notify();
                    }
                }
                if (action.getType() == LEAVE_GAME) {
                    farewellPlayer(action.getPlayerID());
                    break;
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
            Action action = null;
            synchronized (actions) {
                try {
                    if (actions.isEmpty()) {
                        System.out.println("start client wait");
                        actions.wait();
                        System.out.println("finish client wait");
                    }
                } catch (InterruptedException e) {
                    continue;
                }

                action = actions.getNext();
            }

            assert(action != null);

            try {
                if (action != null) {
                    System.out.println("client is sending an action!");
                    sendAction(action);
                }
            } catch (IOException e) {
                // TODO: handle this exception
                 continue;
            }

        }
    }

    private void sendAction(Action action) throws IOException {
        toServer.write(serializeAction((PlayerAction) action));
    }

    // moveType@playerID@optionalSetCardIDsSeparatedByAtSeparator
    // for example "SELECT_SET@1@2@4@16"
    // for example "REQUEST_DRAW_3@3"
    private String serializeAction(PlayerAction action) {
        ActionType actionType = action.getType();
        String serializedAction = actionType + "@" + action.getPlayerID();

        if (actionType == ActionType.SELECT_SET) {
            int[] cardPositions = ((PlayerActionSelectSet) action).getCardPositions();
            for (int cardPosition: cardPositions) {
                serializedAction += "@" + cardPosition;
            }
        }
        System.out.println(serializedAction);
        return serializedAction;
    }
}

// This thread is in charge of displaying it the game may send a message to a player.
class FeedbackGetter implements Runnable {
    private final SocketReader fromServer;

    public FeedbackGetter(SocketReader fromServer) {
        this.fromServer = fromServer;
    }

    @Override
    public void run() {
// other thread sends a move to server as soon as there is one
        while (true) {
            try {
                System.out.println(fromServer.readLine());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}