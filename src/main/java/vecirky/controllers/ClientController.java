package vecirky.controllers;

import vecirky.C;
import vecirky.events.ClientCreated;
import vecirky.events.ClientDeleted;
import vecirky.events.ClientUpdated;
import vecirky.exceptions.ModelCannotBeDeleted;
import vecirky.exceptions.ModelIsInvalid;
import vecirky.exceptions.ModelNotFound;
import vecirky.models.Client;
import vecirky.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

/**
 * Client controller with business logic
 */
public class ClientController {

    private ClientRepository repository = C.get(ClientRepository.class);

    public List<Client> index() {
        return repository.findAll();
    }

    /**
     * Tries to find client in the database based on its primary key and return it
     *
     * @param id Primary key of the model to be retrieved
     * @return Client model retrieved from the database
     * @throws ModelNotFound
     */
    public Client show(int id) throws ModelNotFound {
        Optional<Client> optional = repository.findBy(id);
        if (!optional.isPresent()) {
            throw new ModelNotFound("Klient nenalezen.");
        }

        return optional.get();
    }

    /**
     * Stores new model into the database
     *
     * @param client Client model to be stored
     * @return Updated client model as is in the database
     * @throws ModelIsInvalid
     */
    public Client create(Client client) throws ModelIsInvalid {
        Integer id = repository.create(client);
        if (id == null) {
            throw new ModelIsInvalid("Klienta se nepovedlo uložit.");
        }

        C.eventBus().emit(new ClientCreated(client));

        return client;
    }

    /**
     * Updates already existing client model in the database
     *
     * @param client Client model with updated fields
     * @return Updated client model as is in the database
     * @throws ModelIsInvalid
     */
    public Client update(Client client) throws ModelIsInvalid {
        if (client.getId() == null) {
            throw new ModelIsInvalid("Klienta se nepovedlo aktualizovat");
        }

        boolean ok = repository.update(client);
        if (!ok) {
            throw new ModelIsInvalid("Klienta se nepovedlo aktualizovat");
        }

        C.eventBus().emit(new ClientUpdated(client));

        return client;
    }

    /**
     * Deletes the model from the database
     *
     * @param client Client model to be deleted from the database
     * @return Indicator if the deletion was successful
     * @throws ModelCannotBeDeleted
     */
    public boolean delete(Client client) throws ModelCannotBeDeleted {
        boolean ok = repository.delete(client);
        if (!ok) {
            throw new ModelCannotBeDeleted("Klienta se nepovedlo odstranit");
        }

        C.eventBus().emit(new ClientDeleted(client));

        return true;
    }

}
