package vecirky.database.factories;

import com.github.javafaker.Faker;
import vecirky.C;
import vecirky.models.Client;
import vecirky.repositories.ClientRepository;

/**
 * Factory for making client models
 */
public class ClientFactory {

    private static Faker faker = new Faker();

    /**
     * Makes new client model
     */
    public static Client make() {
        Client client = new Client();

        client.setFirstName(faker.name().firstName());
        client.setLastName(faker.name().lastName());
        client.setDescription(faker.lebowski().quote());
        client.setEmail(faker.internet().emailAddress());
        client.setPhoneNumber(faker.phoneNumber().phoneNumber());
        client.setVarSymbol(faker.number().digits(10));

        return client;
    }

    /**
     * Makes new client model and stores it into the database
     */
    public static Client create() {
        Client client = make();

        C.get(ClientRepository.class).create(client);

        return client;
    }

}
