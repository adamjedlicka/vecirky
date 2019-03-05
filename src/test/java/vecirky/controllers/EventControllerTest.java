package vecirky.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import vecirky.BaseTest;
import vecirky.database.factories.EventFactory;
import vecirky.exceptions.ModelIsInvalid;
import vecirky.exceptions.ModelNotFound;
import vecirky.models.Event;

public class EventControllerTest extends BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public EventController controller;

    @Before
    public void before() {
        controller = new EventController();
    }

    @Test
    public void event_can_be_created() throws ModelIsInvalid {
        Event event = EventFactory.make();

        assertNotNull(controller.create(event));

        assertEquals(1, controller.index().size());
    }

    @Test
    public void event_can_be_retireved() throws ModelIsInvalid, ModelNotFound {
        Event event1 = EventFactory.make();
        event1 = controller.create(event1);

        Event event2 = EventFactory.make();
        event2 = controller.create(event2);

        Event event3 = controller.show(event1.getId());
        assertEquals(event1.getId(), event3.getId());

        Event event4 = controller.show(event2.getId());
        assertEquals(event2.getId(), event4.getId());
    }

    @Test
    public void event_can_be_updated() throws ModelIsInvalid, ModelNotFound {
        Event event = EventFactory.make();
        controller.create(event);

        event.setDescription("Description");
        controller.update(event);

        Event newEvent = controller.show(event.getId());

        assertEquals("Description", newEvent.getDescription());
    }
}
