package sockkeeper.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisConfig {
    private String hostname;
    private String port;
}
