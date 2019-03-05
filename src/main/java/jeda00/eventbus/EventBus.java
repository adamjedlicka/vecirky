package jeda00.eventbus;

import java.util.function.Consumer;

/**
 * EventBus which can be used as mediator for observer pattern
 * <p>
 * Anyone who has access to the instance of EventBus can send events, which are
 * plain old Java objects. Anyone can also subscribe to any event type and when
 * someone emits new event of said event type, every listener will get notified
 * by the callback method supplied during registration.
 */
public interface EventBus {

    /**
     * Subscribe to receiving events of specified type
     *
     * @param event   Event class which handler subscribes to
     * @param handler Handler for receiving events
     * @return The ID of listener which can be used for future unsubscribtion
     */
    public <T> int subscribe(Class<T> event, Consumer<T> handler);

    public default <T> int subscribeAndRunWith(Class<T> event, Consumer<T> handler, T value) {
        int id = subscribe(event, handler);

        handler.accept(value);

        return id;
    }

    /**
     * Unsubscribe from receiving events of specified type
     *
     * @param event Event class which handler un-subscribes from
     * @param id    Id which was returned from the subscribe method to identify the
     *              listener
     * @return Indication if unsubscribtion was succesfull
     */
    public <T> boolean unsubscribe(Class<T> event, int id);

    /**
     * Emit new event and notify all subscribers
     *
     * @param event Event instance to emit
     */
    public <T> void emit(T event);

}
