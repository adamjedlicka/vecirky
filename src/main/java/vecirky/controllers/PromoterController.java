package vecirky.controllers;

import vecirky.C;
import vecirky.events.PromoterCreated;
import vecirky.events.PromoterDeleted;
import vecirky.events.PromoterUpdated;
import vecirky.exceptions.ModelCannotBeDeleted;
import vecirky.exceptions.ModelIsInvalid;
import vecirky.exceptions.ModelNotFound;
import vecirky.models.Promoter;
import vecirky.repositories.PromoterRepository;

import java.util.List;
import java.util.Optional;

/**
 * Promoter controller with business logic
 */
public class PromoterController {

    private PromoterRepository repository = C.get(PromoterRepository.class);

    public List<Promoter> index() {
        return repository.findAll();
    }

    /**
     * Tries to find promoter in the database based on its primary key and return it
     *
     * @param id Primary key of the model to be retrieved
     * @return Promoter model retrieved from the database
     * @throws ModelNotFound
     */
    public Promoter show(int id) throws ModelNotFound {
        Optional<Promoter> optional = repository.findBy(id);

        if (!optional.isPresent()) {
            throw new ModelNotFound("Pořadatel nenalezen.");
        }

        return optional.get();
    }

    /**
     * Stores new model into the database
     *
     * @param promoter Promoter model to be stored
     * @return Updated promoter model as is in the database
     * @throws ModelIsInvalid
     */
    public Promoter create(Promoter promoter) throws ModelIsInvalid {
        Integer id = repository.create(promoter);

        if (id == null) {
            throw new ModelIsInvalid("Pořadatele se nepovedlo uložit.");
        }

        C.eventBus().emit(new PromoterCreated(promoter));

        return promoter;
    }

    /**
     * Updates already existing promoter model in the database
     *
     * @param promoter Promoter model with updated fields
     * @return Updated promoter model as is in the database
     * @throws ModelIsInvalid
     */
    public Promoter update(Promoter promoter) throws ModelIsInvalid {
        if (promoter.getId() == null) {
            throw new ModelIsInvalid("Pořadatele se nepovedlo aktualizovat");
        }

        boolean ok = repository.update(promoter);

        if (!ok) {
            throw new ModelIsInvalid("Pořadatele se nepovedlo aktualizovat");
        }

        C.eventBus().emit(new PromoterUpdated(promoter));

        return promoter;
    }

    /**
     * Deletes the model from the database
     *
     * @param promoter Promoter model to be deleted from the database
     * @return Indicator if the deletion was successful
     * @throws ModelCannotBeDeleted
     */
    public boolean delete(Promoter promoter) throws ModelCannotBeDeleted {
        boolean ok = repository.delete(promoter);
        if (!ok) {
            throw new ModelCannotBeDeleted("Pořadatele se nepovedlo odstranit");
        }

        C.eventBus().emit(new PromoterDeleted(promoter));

        return true;
    }
}
