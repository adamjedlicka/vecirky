package vecirky.database.factories;

import com.github.javafaker.Faker;
import vecirky.C;
import vecirky.models.Promoter;
import vecirky.repositories.PromoterRepository;

/**
 * Factory for making promoter models
 */
public class PromoterFactory {

    private static Faker faker = new Faker();

    /**
     * Makes new promoter model
     */
    public static Promoter make() {
        Promoter promoter = new Promoter();

        promoter.setTitle(faker.name().title());
        promoter.setFirstName(faker.name().firstName());
        promoter.setLastName(faker.name().lastName());
        promoter.setEmail(faker.internet().emailAddress());
        promoter.setPhoneNumber(faker.phoneNumber().phoneNumber());
        promoter.setBankAccount(faker.number().digits(10));

        return promoter;
    }

    /**
     * Makes new promoter model and stores it into the database
     */
    public static Promoter create() {
        Promoter promoter = make();

        C.get(PromoterRepository.class).create(promoter);

        return promoter;
    }

}
