package sockkeeper.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import sockkeeper.service.WebSocketService;


@Singleton
@Slf4j
@ServerEndpoint("/connect/{username}")
public class WebSocketResource {

    private final WebSocketService webSocketService;

    @Inject
    public WebSocketResource(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        webSocketService.handleConnectionOpen(session, username);
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("username") String username) {
        webSocketService.handleMessage(session, message, username);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        webSocketService.handleClose(session, username);
    }

}
