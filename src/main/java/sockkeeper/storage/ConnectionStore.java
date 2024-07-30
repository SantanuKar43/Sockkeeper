package sockkeeper.storage;

import sockkeeper.connection.Connection;
import sockkeeper.principal.Principal;

public interface ConnectionStore {
    boolean contains(Principal principal);
    Connection get(Principal principal);
    void put(Principal principal, Connection connection);
    void remove(Principal principal);
}
