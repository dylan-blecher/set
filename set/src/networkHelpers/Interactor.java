package src.networkHelpers;

import java.io.InputStream;
import java.io.OutputStream;

public class Interactor {
    private InputStream streamFromClient;
    private OutputStream streamToClient;

    public Interactor(InputStream streamFromClient, OutputStream streamToClient) {
        this.streamFromClient = streamFromClient;
        this.streamToClient = streamToClient;
    }

    public InputStream getStreamFromClient() {
        return streamFromClient;
    }
    public OutputStream getStreamToClient() {
        return streamToClient;
    }
}
