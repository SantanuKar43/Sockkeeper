package sockkeeper.connection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import sockkeeper.principal.Principal;
import sockkeeper.storage.AddressStore;
import sockkeeper.storage.ConnectionStore;

@Singleton
public class ConnectionManager {
    private final ConnectionStore connectionStore;
    private final AddressStore addressStore;
    private final String hostAddress;

    @Inject
    public ConnectionManager(ConnectionStore connectionStore,
                             AddressStore addressStore,
                             @Named("hostAddress") String hostAddress) {
        this.connectionStore = connectionStore;
        this.addressStore = addressStore;
        this.hostAddress = hostAddress;
    }

    public void connect(Principal principal, Connection connection) {
        connectionStore.put(principal, connection);
        addressStore.put(principal, hostAddress);
    }

    public void terminate(Principal principal) {
        connectionStore.remove(principal);
        addressStore.remove(principal);
    }
}
