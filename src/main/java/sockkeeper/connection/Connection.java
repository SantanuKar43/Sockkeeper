package sockkeeper.connection;


import jakarta.websocket.Session;

import java.io.IOException;

public class Connection {
    private final Session session;

    public Connection(Session session) {
        this.session = session;
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public boolean isOpen() {
        return session.isOpen();
    }
}
