package vecirky.database.factories;

import com.github.javafaker.Faker;
import vecirky.C;
import vecirky.models.EventType;
import vecirky.repositories.EventTypeRepository;

/**
 * Factory for making EventType models
 */
public class EventTypeFactory {

    public static Faker faker = new Faker();

    /**
     * Makes new eventType model
     */
    public static EventType make() {
        EventType eventType = new EventType();

        eventType.setName(faker.stock().nsdqSymbol());
        eventType.setDescription(faker.witcher().quote());
        eventType.setPrice(faker.number().numberBetween(1000, 50000));

        return eventType;
    }

    /**
     * Makes new EventType model and stores it into the database
     */
    public static EventType create() {
        EventType eventType = make();

        C.get(EventTypeRepository.class).create(eventType);

        return eventType;
    }

}
