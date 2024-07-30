package sockkeeper.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.dropwizard.core.server.DefaultServerFactory;
import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import sockkeeper.config.RedisConfig;
import sockkeeper.config.SockkeeperConfiguration;
import sockkeeper.storage.AddressStore;
import sockkeeper.storage.ConnectionStore;
import sockkeeper.storage.impl.ConnectionStoreImpl;
import sockkeeper.storage.impl.RedisAddressStore;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.http.HttpClient;

public class SockkeeperModule extends AbstractModule {

    private final SockkeeperConfiguration sockkeeperConfiguration;

    public SockkeeperModule(SockkeeperConfiguration sockkeeperConfiguration) {
        this.sockkeeperConfiguration = sockkeeperConfiguration;
    }

    @Override
    public void configure() {
        //bindings go here
        bind(AddressStore.class).to(RedisAddressStore.class);
        bind(ConnectionStore.class).to(ConnectionStoreImpl.class);
    }

    @Provides
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Provides
    public HttpClient providesHttpClient() {
        return HttpClient.newHttpClient();
    }

    @Provides
    @Named("hostAddress")
    public String providesHostAddress() {
        try {
            int httpPort = 0;
            DefaultServerFactory serverFactory = (DefaultServerFactory) this.sockkeeperConfiguration.getServerFactory();
            for (ConnectorFactory connector : serverFactory.getApplicationConnectors()) {
                if (connector.getClass().isAssignableFrom(HttpConnectorFactory.class)) {
                    httpPort = ((HttpConnectorFactory) connector).getPort();
                    break;
                }
            }
            return InetAddress.getLocalHost().getHostAddress() + ":" + httpPort;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Singleton
    @Provides
    public SockkeeperConfiguration getSockkeeperConfiguration() {
        return this.sockkeeperConfiguration;
    }

    @Singleton
    @Provides
    public RedissonClient providesRedissonClient(SockkeeperConfiguration configuration) {
        Config config = new Config();
        config.useSingleServer().setAddress(getFormattedRedisUrl(configuration.getRedisConfig()));
        return Redisson.create(config);
    }

    private String getFormattedRedisUrl(RedisConfig redisConfig) {
        return "redis://" + redisConfig.getHostname() + ":" + redisConfig.getPort();
    }
}
