package src.networkHelpers;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author dylanblecher
 * For each client (player) we need to be able to read from and write to them.
 * This handles their reader and writer using socket communication.
 */
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
