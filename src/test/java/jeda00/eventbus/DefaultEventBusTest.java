package jeda00.eventbus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DefaultEventBusTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public DefaultEventBus eventBus;

    @Before
    public void setUp() {
        eventBus = new DefaultEventBus();
    }

    @Test
    public void it_can_be_subscribed_to_and_event_fired() {
        eventBus.subscribe(String.class, event -> assertTrue(event instanceof String));

        eventBus.subscribe(String.class, event -> event.getClass().equals(String.class));

        eventBus.subscribe(String.class, event -> assertEquals("Hello, World!", event));
        eventBus.subscribe(String.class, event -> assertNotEquals("Bye, World!", event));

        eventBus.emit("Hello, World!");

        assertEquals(1, eventBus.getSentEvents(String.class).size());
    }

    public void simpleSubscriber(String event) {
        fail("Should not be called.");
    }

    @Test
    public void subscriber_can_unsubscribe() {
        boolean[] called = new boolean[1];
        called[0] = false;

        int id = eventBus.subscribe(String.class, this::simpleSubscriber);

        eventBus.subscribe(String.class, e -> called[0] = true);

        boolean unsubscribed = eventBus.unsubscribe(String.class, id);

        eventBus.emit("Message");

        assertTrue(unsubscribed);
        assertTrue(called[0]);
    }

    @Test
    public void only_subscribers_for_emited_event_type_get_notified() {
        boolean[] called = new boolean[1];
        called[0] = false;

        eventBus.subscribe(String.class, e -> fail("Should not be called"));
        eventBus.subscribe(Boolean.class, e -> called[0] = true);

        eventBus.emit(true);

        assertTrue(called[0]);
    }

}
