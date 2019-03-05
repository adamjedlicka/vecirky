package vecirky.repositories.database;

import org.junit.Before;
import org.junit.Test;
import vecirky.BaseTest;
import vecirky.C;
import vecirky.database.factories.EventFactory;
import vecirky.repositories.EventRepository;

import static org.junit.Assert.assertEquals;

public class EventFactoryTest extends BaseTest {

    public EventRepository repository;

    @Before
    public void before() {
        repository = C.get(EventRepository.class);
    }

    @Test
    public void it_creates_events() {
        EventFactory.create();

        assertEquals(1, repository.findAll().size());
    }

}
