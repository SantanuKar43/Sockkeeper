package sockkeeper.storage;

import sockkeeper.principal.Principal;

import java.util.Optional;

public interface AddressStore {
    Optional<String> get(Principal principal);
    void put(Principal principal, String ipAddress);
    void remove(Principal principal);
}
