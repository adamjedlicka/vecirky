package vecirky.events;

import vecirky.models.Event;

/**
 * Event which indicates that already existing event was updated in the database.
 */
public class EventUpdated {

    private Event event;

    public EventUpdated(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

}

