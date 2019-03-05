package vecirky.events;

import vecirky.models.Client;

/**
 * Event which indicates that already existing client was updated in the database.
 */
public class ClientUpdated {

    private Client client;

    public ClientUpdated(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

}
