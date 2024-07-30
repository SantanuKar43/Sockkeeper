package sockkeeper.requestresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sockkeeper.principal.Principal;


@Getter
@Setter
@NoArgsConstructor
public class SendMessageRequest {
    @JsonProperty
    private Principal principal;

    @JsonProperty
    private String message;

    public SendMessageRequest(Principal principal, String message) {
        this.principal = principal;
        this.message = message;
    }
}
