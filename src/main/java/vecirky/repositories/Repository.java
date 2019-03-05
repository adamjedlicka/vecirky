package vecirky.repositories;

import java.util.List;
import java.util.Optional;

/**
 * Interface for storing and retrieving models from persistent storage such as
 * database or file.
 *
 * Takes two generic arguments. First is the type of model stored in the data
 * store and second is unique identification key which can be used to retrieve
 * the record from the datastore.
 */
public interface Repository<Model, Key> {

    /**
     * Attemts to find the record identified by the passed key and return it. If no
     * such record exists returns an empty optional.
     *
     * @param key Unique key identifiing the record
     * @return Optional of the retireved record
     */
    public Optional<Model> findBy(Key key);

    /**
     * Returns a collection of all records matching the model type of this
     * repository.
     *
     * @return List of all records
     */
    public List<Model> findAll();

    /**
     * Creates new record in the datastore based on the values of passed model in.
     * If the creation was successfull, set the identifier of the passed in model to
     * the new value of identifier assigned in the datastore.
     *
     * @param model Model to be stored in the datastore
     * @return primary of inserted record or null if insertion failed.
     */
    public Key create(Model model);

    /**
     * Updates already existing record in the datastore with new values from the
     * passed in model.
     *
     * @param model Model to be updated in the datastore
     * @return Boolean indicating whether the update was successull or not
     */
    public boolean update(Model model);

    /**
     * Deletes a record in the datastore matching the passe in model.
     *
     * @param model Model to be deleted
     * @return Boolean indicating whether the deletion was successfull or not
     */
    public boolean delete(Model model);

}
