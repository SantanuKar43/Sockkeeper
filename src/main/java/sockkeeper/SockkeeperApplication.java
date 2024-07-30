package sockkeeper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.websocket.server.ServerEndpointConfig;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;
import sockkeeper.bootstrap.SockkeeperModule;
import sockkeeper.bootstrap.WebSocketConfigurator;
import sockkeeper.config.SockkeeperConfiguration;
import sockkeeper.service.WebSocketService;
import sockkeeper.health.BasicHealthCheck;
import sockkeeper.health.RedisHealth;
import sockkeeper.resources.MessageResource;
import sockkeeper.resources.WebSocketResource;

public class SockkeeperApplication extends Application<SockkeeperConfiguration> {

    public static void main(final String[] args) throws Exception {
        new SockkeeperApplication().run(args);
    }

    @Override
    public String getName() {
        return "sockkeeper";
    }

    @Override
    public void initialize(final Bootstrap<SockkeeperConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final SockkeeperConfiguration configuration,
                    final Environment environment) {

        Injector injector = Guice.createInjector(new SockkeeperModule(configuration));
        environment.healthChecks().register("basic", injector.getInstance(BasicHealthCheck.class));
        environment.healthChecks().register("redis", injector.getInstance(RedisHealth.class));
        environment.jersey().register(injector.getInstance(MessageResource.class));

        // Use the existing ServletContextHandler provided by Dropwizard
        ServletContextHandler contextHandler = environment.getApplicationContext();

        // Create the WebSocketHandler instance
        WebSocketService webSocketService = injector.getInstance(WebSocketService.class);

        // Initialize the WebSocket container with the custom configurator
        JakartaWebSocketServletContainerInitializer.configure(contextHandler, (servletContext, wsContainer) -> {
            ServerEndpointConfig.Configurator configurator = new WebSocketConfigurator(webSocketService);
            ServerEndpointConfig config = ServerEndpointConfig.Builder
                    .create(WebSocketResource.class, "/connect/{username}")
                    .configurator(configurator)
                    .build();
            wsContainer.addEndpoint(config);
        });
    }

}
