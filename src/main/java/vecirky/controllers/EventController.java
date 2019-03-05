package vecirky.controllers;

import java.util.List;
import java.util.Optional;

import vecirky.C;
import vecirky.events.EventCreated;
import vecirky.events.EventDeleted;
import vecirky.events.EventUpdated;
import vecirky.exceptions.ModelCannotBeDeleted;
import vecirky.exceptions.ModelIsInvalid;
import vecirky.exceptions.ModelNotFound;
import vecirky.models.Event;
import vecirky.repositories.AddressRepository;
import vecirky.repositories.EventRepository;

/**
 * Event controller with business logic
 */
public class EventController {

    private EventRepository repository = C.get(EventRepository.class);

    private AddressRepository addressRepository = C.get(AddressRepository.class);

    public List<Event> index() {
        return repository.findAll();
    }

    /**
     * Tries to find event in the database based on its primary key and return it
     *
     * @param id Primary key of the model to be retrieved
     * @return Event model retrieved from the database
     * @throws ModelNotFound
     */
    public Event show(int id) throws ModelNotFound {
        Optional<Event> optional = repository.findBy(id);

        if (!optional.isPresent()) {
            throw new ModelNotFound("Večírek nenalezen.");
        }

        return optional.get();
    }

    /**
     * Stores new model into the database
     *
     * @param event Event model to be stored
     * @return Updated event model as is in the database
     * @throws ModelIsInvalid
     */
    public Event create(Event event) throws ModelIsInvalid {
        if (event.getAddress().getId() == null) {
            Integer idAddress = addressRepository.create(event.getAddress());

            if (idAddress == null) {
                throw new ModelIsInvalid("Večírek se nepovedlo uložit.");
            }
        }

        Integer id = repository.create(event);

        if (id == null) {
            throw new ModelIsInvalid("Večírek se nepovedlo uložit.");
        }

        C.eventBus().emit(new EventCreated(event));

        return event;
    }

    /**
     * Updates already existing event model in the database
     *
     * @param event Event model with updated fields
     * @return Updated event model as is in the database
     * @throws ModelIsInvalid
     */
    public Event update(Event event) throws ModelIsInvalid {
        if (event.getId() == null) {
            throw new ModelIsInvalid("Večírek se nepovedlo aktualizovat");
        }

        boolean okAddress = addressRepository.update(event.getAddress());
        if (!okAddress) {
            throw new ModelIsInvalid("Večírek se nepovedlo aktualizovat");
        }

        boolean ok = repository.update(event);
        if (!ok) {
            throw new ModelIsInvalid("Večírek se nepovedlo aktualizovat");
        }

        C.eventBus().emit(new EventUpdated(event));

        return event;
    }

    /**
     * Deletes the model from the database
     *
     * @param event Event model to be deleted from the database
     * @return Indicator if the deletion was successful
     * @throws ModelCannotBeDeleted
     */
    public Event delete(Event event) throws ModelCannotBeDeleted {
        boolean ok = repository.delete(event);
        if (!ok) {
            throw new ModelCannotBeDeleted("Večírek se nepovedlo zrušit");
        }

        C.eventBus().emit(new EventDeleted(event));

        return event;
    }
}