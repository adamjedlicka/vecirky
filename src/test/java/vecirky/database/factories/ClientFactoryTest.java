package vecirky.database.factories;

import org.junit.Before;
import org.junit.Test;
import vecirky.BaseTest;
import vecirky.C;
import vecirky.repositories.ClientRepository;

import static org.junit.Assert.assertEquals;

public class ClientFactoryTest extends BaseTest {

    public ClientRepository repository;

    @Before
    public void setUp() {
        repository = C.get(ClientRepository.class);
    }

    @Test
    public void it_creates_clients() {
        ClientFactory.create();

        assertEquals(1, repository.findAll().size());
    }

}
