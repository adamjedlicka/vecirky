package vecirky.repositories;

import java.util.List;

/**
 * Repository which can be searched with string string keywords
 */
public interface Searchable<Model> {

    /**
     * Searches the datastore and returns relevatn records matching the search
     * string.
     *
     * @param search Search string used in matching the records
     * @return Collection of models matching the search
     */
    public List<Model> search(String search);

}
