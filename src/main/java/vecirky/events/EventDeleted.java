package vecirky.events;

import vecirky.models.Event;

/**
 * Event which indicates that an event was deleted from the database.
 */
public class EventDeleted {

    private Event event;

    public EventDeleted(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

}

