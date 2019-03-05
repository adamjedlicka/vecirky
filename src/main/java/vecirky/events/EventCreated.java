package vecirky.events;

import vecirky.models.Event;

/**
 * Event which indicates that new event was created and stored into the database.
 */
public class EventCreated {

    private Event event;

    public EventCreated(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

}
