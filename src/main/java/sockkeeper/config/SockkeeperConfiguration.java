package sockkeeper.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SockkeeperConfiguration extends Configuration {
    @JsonProperty private RedisConfig redisConfig;
    @JsonProperty private String systemPort;
}
