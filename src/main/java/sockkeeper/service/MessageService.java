package sockkeeper.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import sockkeeper.client.Client;
import sockkeeper.connection.Connection;
import sockkeeper.principal.Principal;
import sockkeeper.requestresponse.SendMessageRequest;
import sockkeeper.storage.AddressStore;
import sockkeeper.storage.ConnectionStore;

import java.io.IOException;
import java.util.Optional;

@Singleton
public class MessageService {
    private final AddressStore addressStore;
    private final Client client;
    private final ConnectionStore connectionStore;

    @Inject
    public MessageService(AddressStore addressStore, Client client, ConnectionStore connectionStore) {
        this.addressStore = addressStore;
        this.client = client;
        this.connectionStore = connectionStore;
    }

    public void sendMessage(String message, Principal principal) throws IOException, InterruptedException {
        if (connectionStore.contains(principal)) {
            sendMessage(connectionStore.get(principal), message);
        } else {
            Optional<String> ipAddress = addressStore.get(principal);
            if (ipAddress.isEmpty()) {
                throw new RuntimeException("address not found");
            }
            SendMessageRequest sendMessageRequest = new SendMessageRequest(principal, message);
            client.sendMessage(ipAddress.get(), sendMessageRequest);
        }
    }

    private void sendMessage(Connection connection, String message) throws IOException {
        if (connection.isOpen()) {
            connection.sendMessage(message);
        } else {
            throw new RuntimeException("connection is closed");
        }
    }
}
