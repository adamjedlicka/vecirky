package vecirky.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public abstract class RepositoryTest<Model, Key> {

    @Test
    public void model_can_be_persisted() {
        for (Repository<Model, Key> r : getRepositories()) {
            Model m = makeModel();

            assertNotNull(r.create(m));

            assertEquals(1, r.findAll().size());
        }
    }

    public abstract List<Repository<Model, Key>> getRepositories();

    public abstract Model makeModel();

}
