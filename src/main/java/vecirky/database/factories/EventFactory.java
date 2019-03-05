package vecirky.database.factories;

import com.github.javafaker.Faker;
import vecirky.C;
import vecirky.models.Event;
import vecirky.models.Promoter;
import vecirky.repositories.EventRepository;

import java.util.concurrent.TimeUnit;

/**
 * Factory for making event models
 */
public class EventFactory {

    private static Faker faker = new Faker();

    /**
     * Makes new event model
     */
    public static Event make() {
        Event event = new Event();

        event.setDescription(faker.witcher().quote());
        event.setEventDate(faker.date().future(100, TimeUnit.DAYS));
        event.setPrice(faker.number().numberBetween(1000, 50000));
        event.setEventType(EventTypeFactory.create());
        event.setMainPromoter(PromoterFactory.create());
        event.setClient(ClientFactory.create());
        event.setAddress(AddressFactory.create());

        for (int i = 0; i < faker.number().numberBetween(0, 5); i++) {
            Promoter promoter = PromoterFactory.create();

            event.getMinorPromoters().add(promoter);
        }

        return event;
    }

    /**
     * Makes new event model and stores it into the database
     */
    public static Event create() {
        Event event = make();

        C.get(EventRepository.class).create(event);

        return event;
    }


}
