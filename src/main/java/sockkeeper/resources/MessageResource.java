package sockkeeper.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import sockkeeper.service.MessageService;
import sockkeeper.requestresponse.SendMessageRequest;

import java.io.IOException;

@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Path("/")
public class MessageResource {
    private final MessageService messageService;

    @Inject
    public MessageResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @POST
    @Path("/sendMessage")
    public void sendMessage(SendMessageRequest sendMessageRequest) throws IOException, InterruptedException {
        messageService.sendMessage(sendMessageRequest.getMessage(), sendMessageRequest.getPrincipal());
    }

}
