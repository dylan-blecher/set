package src.networkHelpers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketWriter {
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