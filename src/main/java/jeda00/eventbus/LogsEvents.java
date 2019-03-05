package jeda00.eventbus;

import java.util.List;

/**
 * EventBus logs sent events and can reutnr them for the given event type
 */
public interface LogsEvents {

    /**
     * Returns list of sent events of the given type
     *
     * @param event Type of the events which are returned
     * @return List of sent events of the given type
     */
    public <T> List<T> getSentEvents(Class<T> event);

}
