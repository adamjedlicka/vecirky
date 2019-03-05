package vecirky.database.factories;

import org.junit.Before;
import org.junit.Test;
import vecirky.BaseTest;
import vecirky.C;
import vecirky.repositories.AddressRepository;

import static org.junit.Assert.assertEquals;

public class AddressFactoryTest extends BaseTest {

    public AddressRepository repository;

    @Before
    public void before() {
        repository = C.get(AddressRepository.class);
    }

    @Test
    public void it_creates_addresses() {
        AddressFactory.create();

        assertEquals(1, repository.findAll().size());
    }

}
