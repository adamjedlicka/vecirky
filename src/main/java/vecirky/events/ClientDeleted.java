package vecirky.events;

import vecirky.models.Client;

/**
 * Event which indicates that a client was deleted from the database.
 */
public class ClientDeleted {

    private Client client;

    public ClientDeleted(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

}
