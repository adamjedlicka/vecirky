package vecirky.database.factories;

import com.github.javafaker.Faker;
import vecirky.C;
import vecirky.models.Address;
import vecirky.repositories.AddressRepository;

/**
 * Factory for making address models
 */
public class AddressFactory {

    private static Faker faker = new Faker();

    /**
     * Makes new address model
     */
    public static Address make() {
        Address address = new Address();

        address.setCity(faker.address().city());
        address.setStreet(faker.address().streetAddress());
        address.setNumberOfDescriptive(faker.number().numberBetween(100, 999));
        address.setZipCode(faker.number().numberBetween(10000, 99999));

        return address;
    }

    /**
     * Makes new address model and stores it into the database
     */
    public static Address create() {
        Address address = make();

        C.get(AddressRepository.class).create(address);

        return address;
    }

}
