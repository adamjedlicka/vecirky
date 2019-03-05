package vecirky.repositories.database;

import org.junit.Before;
import org.junit.Test;
import vecirky.BaseTest;
import vecirky.models.Client;

import static org.junit.Assert.*;

public class DatabaseClientRepositoryTest extends BaseTest {
    private DatabaseClientRepository repo;

    @Before
    public void setUp() {
        repo = new DatabaseClientRepository();
    }

    @Test
    public void it_can_save_and_retrie_records_from_database() {

        assertEquals(0, repo.findAll().size());

        Client client = new Client();
        client.setTitle("");
        client.setFirstName("Karel");
        client.setLastName("Barel");
        client.setEmail("karel.barel@email.cz");
        client.setPhoneNumber("987654321");
        client.setDescription("hipster");
        client.setVarSymbol("2304982333");

        Integer id1 = repo.create(client);
        assertNotNull(id1);

        client = repo.findBy(id1).get();
        assertNotNull(client);

        assertEquals("", repo.findBy(id1).get().getTitle());
        assertEquals("Karel", repo.findBy(id1).get().getFirstName());
        assertEquals("Barel", repo.findBy(id1).get().getLastName());
        assertEquals("karel.barel@email.cz", repo.findBy(id1).get().getEmail());
        assertEquals("987654321", repo.findBy(id1).get().getPhoneNumber());
        assertEquals("hipster", repo.findBy(id1).get().getDescription());
        assertEquals("2304982333", repo.findBy(id1).get().getVarSymbol());

        assertEquals(1, repo.findAll().size());
    }

    @Test
    public void it_can_retrie_specific_record_from_database() {

        assertEquals(0, repo.findAll().size());

        Client client1 = new Client();
        client1.setTitle("");
        client1.setFirstName("Karel");
        client1.setLastName("Barel");
        client1.setEmail("karel.barel@email.cz");
        client1.setPhoneNumber("987654321");
        client1.setDescription("hipster");
        client1.setVarSymbol("2304982333");

        Client client2 = new Client();
        client2.setTitle("Ing.");
        client2.setFirstName("Daniel");
        client2.setLastName("Neprijel");
        client2.setEmail("daniel.neprijel@email.cz");
        client2.setPhoneNumber("123456789");
        client2.setDescription("Intelektual");
        client2.setVarSymbol("11223322");

        Integer id1 = repo.create(client1);
        assertNotNull(id1);
        Integer id2 = repo.create(client2);
        assertNotNull(id2);

        assertEquals("", repo.findBy(id1).get().getTitle());
        assertEquals("Karel", repo.findBy(id1).get().getFirstName());
        assertEquals("Barel", repo.findBy(id1).get().getLastName());
        assertEquals("karel.barel@email.cz", repo.findBy(id1).get().getEmail());
        assertEquals("987654321", repo.findBy(id1).get().getPhoneNumber());
        assertEquals("hipster", repo.findBy(id1).get().getDescription());
        assertEquals("2304982333", repo.findBy(id1).get().getVarSymbol());

        assertEquals("Ing.", repo.findBy(id2).get().getTitle());
        assertEquals("Daniel", repo.findBy(id2).get().getFirstName());
        assertEquals("Neprijel", repo.findBy(id2).get().getLastName());
        assertEquals("daniel.neprijel@email.cz", repo.findBy(id2).get().getEmail());
        assertEquals("123456789", repo.findBy(id2).get().getPhoneNumber());
        assertEquals("Intelektual", repo.findBy(id2).get().getDescription());
        assertEquals("11223322", repo.findBy(id2).get().getVarSymbol());

        assertEquals(2, repo.findAll().size());
    }

    @Test
    public void it_can_update_a_record_and_retrieve_it_back() {

        assertEquals(0, repo.findAll().size());

        Client client1 = new Client();
        client1.setTitle("");
        client1.setFirstName("Karel");
        client1.setLastName("Barel");
        client1.setEmail("karel.barel@email.cz");
        client1.setPhoneNumber("987654321");
        client1.setDescription("hipster");
        client1.setVarSymbol("2304982333");

        Client client2 = new Client();
        client2.setTitle("Ing.");
        client2.setFirstName("Daniel");
        client2.setLastName("Neprijel");
        client2.setEmail("daniel.neprijel@email.cz");
        client2.setPhoneNumber("123456789");
        client2.setDescription("Intelektual");
        client2.setVarSymbol("11223322");

        Integer id1 = repo.create(client1);
        assertNotNull(id1);
        Integer id2 = repo.create(client2);
        assertNotNull(id2);

        client1 = repo.findBy(id1).get();
        assertNotNull(client1);

        client1.setTitle("Bc.");
        client1.setEmail("karel.barel8@gmail.com");
        client1.setPhoneNumber("111222333");
        client1.setDescription("financni poradce");
        client1.setVarSymbol("555666");
        repo.update(client1);

        assertEquals("Bc.", repo.findBy(id1).get().getTitle());
        assertEquals("Karel", repo.findBy(id1).get().getFirstName());
        assertEquals("Barel", repo.findBy(id1).get().getLastName());
        assertEquals("karel.barel8@gmail.com", repo.findBy(id1).get().getEmail());
        assertEquals("111222333", repo.findBy(id1).get().getPhoneNumber());
        assertEquals("financni poradce", repo.findBy(id1).get().getDescription());
        assertEquals("555666", repo.findBy(id1).get().getVarSymbol());

        assertEquals("Ing.", repo.findBy(id2).get().getTitle());
        assertEquals("Daniel", repo.findBy(id2).get().getFirstName());
        assertEquals("Neprijel", repo.findBy(id2).get().getLastName());
        assertEquals("daniel.neprijel@email.cz", repo.findBy(id2).get().getEmail());
        assertEquals("123456789", repo.findBy(id2).get().getPhoneNumber());
        assertEquals("Intelektual", repo.findBy(id2).get().getDescription());
        assertEquals("11223322", repo.findBy(id2).get().getVarSymbol());

        assertEquals(2, repo.findAll().size());
    }

    @Test
    public void it_can_properly_delete_a_record() {

        assertEquals(0, repo.findAll().size());

        Client client1 = new Client();
        client1.setTitle("");
        client1.setFirstName("Karel");
        client1.setLastName("Barel");
        client1.setEmail("karel.barel@email.cz");
        client1.setPhoneNumber("987654321");
        client1.setDescription("hipster");
        client1.setVarSymbol("2304982333");

        Client client2 = new Client();
        client2.setTitle("Ing.");
        client2.setFirstName("Daniel");
        client2.setLastName("Neprijel");
        client2.setEmail("daniel.neprijel@email.cz");
        client2.setPhoneNumber("123456789");
        client2.setDescription("Intelektual");
        client2.setVarSymbol("11223322");

        Integer id1 = repo.create(client1);
        assertNotNull(id1);
        Integer id2 = repo.create(client2);
        assertNotNull(id2);

        client1 = repo.findBy(id1).get();
        assertNotNull(client1);
        client2 = repo.findBy(id2).get();
        assertNotNull(client2);
        assertEquals(2, repo.findAll().size());

        repo.delete(client2);
        assertEquals(1, repo.findAll().size());
        assertTrue(repo.findBy(id1).isPresent());
        assertFalse(repo.findBy(id2).isPresent());

        repo.delete(client1);
        assertEquals(0, repo.findAll().size());
        assertFalse(repo.findBy(id1).isPresent());
    }
}
