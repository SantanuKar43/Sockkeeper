package sockkeeper.bootstrap;

import jakarta.websocket.server.ServerEndpointConfig;
import sockkeeper.service.WebSocketService;
import sockkeeper.resources.WebSocketResource;

public class WebSocketConfigurator extends ServerEndpointConfig.Configurator {

    private final WebSocketService webSocketService;

    public WebSocketConfigurator(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if (endpointClass == WebSocketResource.class) {
            return endpointClass.cast(new WebSocketResource(webSocketService));
        }
        return super.getEndpointInstance(endpointClass);
    }
}
