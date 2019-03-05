package vecirky.repositories.database;

import org.junit.Before;
import org.junit.Test;
import vecirky.BaseTest;
import vecirky.models.EventType;

import static org.junit.Assert.*;

public class DatabaseEventTypeRepositoryTest extends BaseTest {
    private DatabaseEventTypeRepository repo;

    @Before
    public void setUp() {
        repo = new DatabaseEventTypeRepository();
    }

    @Test
    public void it_can_save_and_retrieve_records_from_database() {
        assertEquals(4, repo.findAll().size());

        EventType eventType = new EventType();
        eventType.setName("Saturnalia");
        eventType.setDescription("Ancient ritual");
        eventType.setTasks("Get a pine tree, place it in the middle of the square, and light it on fire.");
        eventType.setPrice(5000);

        Integer id1 = repo.create(eventType);
        assertNotNull(id1);

        assertEquals(5, repo.findAll().size());

        assertEquals("Saturnalia", repo.findBy(id1).get().getName());
        assertEquals("Ancient ritual", repo.findBy(id1).get().getDescription());
        assertEquals("Get a pine tree, place it in the middle of the square, and light it on fire.",
                repo.findBy(id1).get().getTasks());
        assertEquals(5000, repo.findBy(id1).get().getPrice());
    }

    @Test
    public void it_can_retrie_specific_record_from_database() {
        assertEquals(4, repo.findAll().size());

        EventType eventType1 = new EventType();
        eventType1.setName("Saturnalia");
        eventType1.setDescription("Ancient ritual");
        eventType1.setTasks("Get a pine tree, place it in the middle of the square, and light it on fire.");
        eventType1.setPrice(5000);

        EventType eventType2 = new EventType();
        eventType2.setName("Gladiator game");
        eventType2.setDescription("Ancient tournament");
        eventType2.setTasks("Get a swordsman and five starving lions, and organise the event.");
        eventType2.setPrice(5000);

        Integer id1 = repo.create(eventType1);
        assertNotNull(id1);
        Integer id2 = repo.create(eventType2);
        assertNotNull(id2);

        assertEquals("Saturnalia", repo.findBy(id1).get().getName());
        assertEquals("Ancient ritual", repo.findBy(id1).get().getDescription());
        assertEquals("Get a pine tree, place it in the middle of the square, and light it on fire.",
                repo.findBy(id1).get().getTasks());
        assertEquals(5000, repo.findBy(id1).get().getPrice());

        assertEquals("Gladiator game", repo.findBy(id2).get().getName());
        assertEquals("Ancient tournament", repo.findBy(id2).get().getDescription());
        assertEquals("Get a swordsman and five starving lions, and organise the event.",
                repo.findBy(id2).get().getTasks());
        assertEquals(5000, repo.findBy(id2).get().getPrice());

        assertEquals(6, repo.findAll().size());
    }

    @Test
    public void it_can_update_a_record_and_retrieve_it_back() {
        assertEquals(4, repo.findAll().size());

        EventType eventType1 = new EventType();
        eventType1.setName("Saturnalia");
        eventType1.setDescription("Ancient ritual");
        eventType1.setTasks("Get a pine tree, place it in the middle of the square, and light it on fire.");
        eventType1.setPrice(5000);

        EventType eventType2 = new EventType();
        eventType2.setName("Gladiator game");
        eventType2.setDescription("Ancient tournament");
        eventType2.setTasks("Get a swordsman and five starving lions, and organise the event.");
        eventType2.setPrice(5000);

        Integer id1 = repo.create(eventType1);
        assertNotNull(id1);
        Integer id2 = repo.create(eventType2);
        assertNotNull(id2);

        eventType1 = repo.findBy(id1).get();
        assertNotNull(eventType1);

        eventType1.setName("Wrestling event");
        eventType1.setDescription("Fight with no rules between two champions");
        eventType1.setTasks("Send invites to everyone on the guest list, arrange a referee, an ambulance and medics");
        eventType1.setPrice(7777);

        repo.update(eventType1);

        assertEquals("Wrestling event", repo.findBy(id1).get().getName());
        assertEquals("Fight with no rules between two champions", repo.findBy(id1).get().getDescription());
        assertEquals("Send invites to everyone on the guest list, arrange a referee, an ambulance and medics",
                repo.findBy(id1).get().getTasks());
        assertEquals(7777, repo.findBy(id1).get().getPrice());

        assertEquals("Gladiator game", repo.findBy(id2).get().getName());
        assertEquals("Ancient tournament", repo.findBy(id2).get().getDescription());
        assertEquals("Get a swordsman and five starving lions, and organise the event.",
                repo.findBy(id2).get().getTasks());
        assertEquals(5000, repo.findBy(id2).get().getPrice());

        assertEquals(6, repo.findAll().size());
    }

    @Test
    public void it_can_properly_delete_a_record() {
        assertEquals(4, repo.findAll().size());

        EventType eventType1 = new EventType();
        eventType1.setName("Saturnalia");
        eventType1.setDescription("Ancient ritual");
        eventType1.setTasks("Get a pine tree, place it in the middle of the square, and light it on fire.");
        eventType1.setPrice(5000);

        EventType eventType2 = new EventType();
        eventType2.setName("Gladiator game");
        eventType2.setDescription("Ancient tournament");
        eventType2.setTasks("Get a swordsman and five starving lions, and organise the event.");
        eventType2.setPrice(5000);

        Integer id1 = repo.create(eventType1);
        assertNotNull(id1);
        Integer id2 = repo.create(eventType2);
        assertNotNull(id2);

        assertEquals("Saturnalia", repo.findBy(id1).get().getName());
        assertEquals("Ancient ritual", repo.findBy(id1).get().getDescription());
        assertEquals("Get a pine tree, place it in the middle of the square, and light it on fire.",
                repo.findBy(id1).get().getTasks());
        assertEquals(5000, repo.findBy(id1).get().getPrice());

        assertEquals("Gladiator game", repo.findBy(id2).get().getName());
        assertEquals("Ancient tournament", repo.findBy(id2).get().getDescription());
        assertEquals("Get a swordsman and five starving lions, and organise the event.",
                repo.findBy(id2).get().getTasks());
        assertEquals(5000, repo.findBy(id2).get().getPrice());

        assertEquals(6, repo.findAll().size());

        eventType1 = repo.findBy(id1).get();
        assertNotNull(eventType1);
        eventType2 = repo.findBy(id2).get();
        assertNotNull(eventType2);

        repo.delete(eventType1);
        assertEquals(5, repo.findAll().size());
        repo.delete(eventType2);
        assertEquals(4, repo.findAll().size());
    }
}
