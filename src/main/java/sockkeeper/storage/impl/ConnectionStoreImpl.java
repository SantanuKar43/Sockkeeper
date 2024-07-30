package sockkeeper.storage.impl;

import com.google.inject.Singleton;
import sockkeeper.connection.Connection;
import sockkeeper.principal.Principal;
import sockkeeper.storage.ConnectionStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ConnectionStoreImpl implements ConnectionStore {
    private final Map<Principal, Connection> principalConnectionMap;

    public ConnectionStoreImpl() {
        this.principalConnectionMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean contains(Principal principal) {
        return principalConnectionMap.containsKey(principal);
    }

    @Override
    public Connection get(Principal principal) {
        return principalConnectionMap.get(principal);
    }

    @Override
    public void put(Principal principal, Connection connection) {
        principalConnectionMap.put(principal, connection);
    }

    @Override
    public void remove(Principal principal) {
        principalConnectionMap.remove(principal);
    }
}
