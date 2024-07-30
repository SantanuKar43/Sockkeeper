package sockkeeper.storage.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import sockkeeper.principal.Principal;
import sockkeeper.storage.AddressStore;

import java.util.Optional;

@Singleton
public class RedisAddressStore implements AddressStore {
    private static final String ID_PREFIX = "SKPR_";
    private final RedissonClient redisson;

    @Inject
    public RedisAddressStore(RedissonClient redisson) {
        this.redisson = redisson;
    }

    @Override
    public Optional<String> get(Principal principal) {
        try {
            RBucket<String> bucket = redisson.getBucket(formatKey(principal.getId(), ID_PREFIX));
            if (!bucket.isExists()) {
                return Optional.empty();
            }
            String address = bucket.get();
            return Optional.of(address);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatKey(String id, String idPrefix) {
        return idPrefix + id;
    }

    @Override
    public void put(Principal principal, String ipAddress) {
        try {
            RBucket<Object> idBucket = redisson.getBucket(formatKey(principal.getId(), ID_PREFIX));
            idBucket.set(ipAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Principal principal) {
        try {
            redisson.getBucket(formatKey(principal.getId(), ID_PREFIX)).delete();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
