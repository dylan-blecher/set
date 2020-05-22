package src.networkHelpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketReader {
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
        reader.close();
        if (!socket.isClosed()) socket.close();
    }
}