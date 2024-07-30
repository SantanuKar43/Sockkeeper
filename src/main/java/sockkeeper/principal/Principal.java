package sockkeeper.principal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Principal {
    @JsonProperty
    private String id;

    public Principal(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Principal)) return false;
        Principal principal = (Principal) object;
        return Objects.equals(id, principal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
