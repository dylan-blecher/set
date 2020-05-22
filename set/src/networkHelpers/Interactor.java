package src.networkHelpers;

public class Interactor {
    private SocketReader reader;
    private SocketWriter writer;

    public Interactor(SocketReader reader, SocketWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public SocketReader getReader() {
        return reader;
    }

    public SocketWriter getWriter() {
        return writer;
    }
}
