package sockkeeper.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import sockkeeper.connection.Connection;
import sockkeeper.connection.ConnectionManager;
import sockkeeper.principal.Principal;

@Slf4j
@Singleton
public class WebSocketService {

    private final ConnectionManager connectionManager;

    @Inject
    public WebSocketService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void handleConnectionOpen(Session session, String username) {
        session.setMaxIdleTimeout(-1);
        log.info("Yay! Opened socket connection by: {}", username);
        Principal principal = new Principal(username);
        connectionManager.connect(principal, new Connection(session));
    }

    public void handleMessage(Session session, String message, String username) {
        log.info("Yay! received message: {} from: {}", message, username);
    }

    public void handleClose(Session session, String username) {
        Principal principal = new Principal(username);
        connectionManager.terminate(principal);
    }
}
