package src.server;

public class SocketMessage {
    // TODO: maybe i should make messageType an enum...
    private String messageType;
    private String message;

    public SocketMessage(String messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    public SocketMessage(String messageType, int message) {
        this(messageType, String.valueOf(message));
    }

//    @Override
//    public String toString() {
//        return String.format("MessageForClient [message=%s]", message);
//    }

    // jackson2 requires a getter for every attribute in order to serialize!
    public String getMessage() {
        return message;
    }

    public String getMessageType() {
        return messageType;
    }
}
