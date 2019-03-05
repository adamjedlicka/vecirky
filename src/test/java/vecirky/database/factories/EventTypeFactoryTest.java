package vecirky.database.factories;

import org.junit.Before;
import org.junit.Test;
import vecirky.BaseTest;
import vecirky.C;
import vecirky.repositories.EventTypeRepository;

import static org.junit.Assert.assertEquals;

public class EventTypeFactoryTest extends BaseTest {

    public EventTypeRepository repository;

    @Before
    public void before() {
        repository = C.get(EventTypeRepository.class);
    }

    @Test
    public void it_creates_event_types() {
        int size = repository.findAll().size();

        EventTypeFactory.create();

        assertEquals(size + 1, repository.findAll().size());
    }

}
