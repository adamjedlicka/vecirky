package vecirky.events;

import vecirky.models.Client;

/**
 * Event which indicates than new client was created and stored in the database.
 */
public class ClientCreated {

    private Client client;

    public ClientCreated(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

}
