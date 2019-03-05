package vecirky.repositories.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import vecirky.BaseTest;
import vecirky.C;
import vecirky.database.factories.AddressFactory;
import vecirky.database.factories.ClientFactory;
import vecirky.models.Event;
import vecirky.models.EventType;
import vecirky.models.Promoter;
import vecirky.repositories.EventRepository;
import vecirky.repositories.EventTypeRepository;
import vecirky.repositories.PromoterRepository;

public class DatabaseEventRepositoryTest extends BaseTest {

    private EventRepository eventRepo;

    private EventTypeRepository eventTypeRepo;

    private PromoterRepository promoterRepo;

    @Before
    public void before() {
        eventRepo = C.get(EventRepository.class);
        eventTypeRepo = C.get(EventTypeRepository.class);
        promoterRepo = C.get(PromoterRepository.class);
    }

    @Test
    public void event_has_event_type() {
        Promoter p1 = new Promoter();
        p1.setTitle("title1");
        p1.setFirstName("firstName1");
        p1.setLastName("lastName1");
        p1.setEmail("email1");
        p1.setPhoneNumber("phoneNumber1");
        p1.setBankAccount("bankAccount2");
        promoterRepo.create(p1);

        EventType eventType = eventTypeRepo.findBy(1).get();

        Event event1 = new Event();
        event1.setDescription("description");
        event1.setEventDate(new Date());
        event1.setEventType(eventType);
        event1.setPrice(0);
        event1.setClient(ClientFactory.create());
        event1.setAddress(AddressFactory.create());
        event1.setMainPromoter(p1);

        Integer id1 = eventRepo.create(event1);

        Event event2 = eventRepo.findBy(id1).get();

        assertNotNull(event2.getEventType());
        assertEquals(eventType.getName(), event2.getEventType().getName());
    }

    @Test
    public void event_has_minor_promoters() {
        Promoter p1 = new Promoter();
        p1.setTitle("title1");
        p1.setFirstName("firstName1");
        p1.setLastName("lastName1");
        p1.setEmail("email1");
        p1.setPhoneNumber("phoneNumber1");
        p1.setBankAccount("bankAccount2");
        promoterRepo.create(p1);

        Promoter p2 = new Promoter();
        p2.setTitle("title2");
        p2.setFirstName("firstName2");
        p2.setLastName("lastName2");
        p2.setEmail("email2");
        p2.setPhoneNumber("phoneNumber2");
        p2.setBankAccount("bankAccount2");
        promoterRepo.create(p2);

        Promoter p3 = new Promoter();
        p3.setTitle("title3");
        p3.setFirstName("firstName3");
        p3.setLastName("lastName3");
        p3.setEmail("email3");
        p3.setPhoneNumber("phoneNumber3");
        p3.setBankAccount("bankAccount3");
        promoterRepo.create(p3);

        EventType eventType = eventTypeRepo.findBy(1).get();

        Event event1 = new Event();
        event1.setDescription("description");
        event1.setEventDate(new Date());
        event1.setEventType(eventType);
        event1.setPrice(0);
        event1.setClient(ClientFactory.create());
        event1.setAddress(AddressFactory.create());
        event1.setMainPromoter(p3);
        event1.getMinorPromoters().add(p1);
        event1.getMinorPromoters().add(p2);
        int id = eventRepo.create(event1);

        Event event2 = eventRepo.findBy(id).get();

        assertEquals(2, event2.getMinorPromoters().size());

        event1.getMinorPromoters().remove(0);

        eventRepo.update(event1);

        Event event3 = eventRepo.findBy(id).get();

        assertEquals(1, event3.getMinorPromoters().size());
        assertEquals("title2", event3.getMinorPromoters().get(0).getTitle());

        assertTrue(eventRepo.delete(event1));
        assertEquals(0, eventRepo.findAll().size());
    }

}
