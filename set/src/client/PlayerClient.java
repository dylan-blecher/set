package src.client;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class PlayerClient {
    private final static int GAME_PORT = 9090;
    public static void main(String[] args) {
        try {
            InetAddress host = InetAddress.getLocalHost();
            try (var socket = new Socket(host.getHostName(), GAME_PORT)) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                int num = 0;
                while (num < 100) {
                    writer.write("Hello number " + num++ + "\n");
                    writer.flush();
//                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
