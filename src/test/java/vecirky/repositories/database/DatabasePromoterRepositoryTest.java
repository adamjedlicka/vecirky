package vecirky.repositories.database;

import org.junit.Before;
import org.junit.Test;
import vecirky.BaseTest;
import vecirky.models.Promoter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DatabasePromoterRepositoryTest extends BaseTest {

    private DatabasePromoterRepository repo;

    @Before
    public void setUp(){
        repo = new DatabasePromoterRepository();
    }

    @Test
    public void it_can_save_and_retrie_records_from_database() {

        assertEquals(0, repo.findAll().size());

        Promoter promoter = new Promoter();
        promoter.setTitle("");
        promoter.setFirstName("Petr");
        promoter.setLastName("Svetr");
        promoter.setEmail("petr.svetr@vecirky.cz");
        promoter.setPhoneNumber("232323233");
        promoter.setBankAccount("11113434343434/0000");

        Integer id1 = repo.create(promoter);
        assertNotNull(id1);

        assertEquals("", repo.findBy(id1).get().getTitle());
        assertEquals("Petr", repo.findBy(id1).get().getFirstName());
        assertEquals("Svetr", repo.findBy(id1).get().getLastName());
        assertEquals("petr.svetr@vecirky.cz", repo.findBy(id1).get().getEmail());
        assertEquals("232323233", repo.findBy(id1).get().getPhoneNumber());
        assertEquals("11113434343434/0000", repo.findBy(id1).get().getBankAccount());

        assertEquals(1, repo.findAll().size());
    }

    @Test
    public void it_can_retrie_specific_record_from_database() {

        assertEquals(0, repo.findAll().size());

        Promoter promoter1 = new Promoter();
        promoter1.setTitle("");
        promoter1.setFirstName("Petr");
        promoter1.setLastName("Svetr");
        promoter1.setEmail("petr.svetr@vecirky.cz");
        promoter1.setPhoneNumber("232323233");
        promoter1.setBankAccount("11113434343434/0000");

        Promoter promoter2 = new Promoter();
        promoter2.setTitle("");
        promoter2.setFirstName("Michael");
        promoter2.setLastName("Nezajel");
        promoter2.setEmail("michael.nezajel@vecirky.cz");
        promoter2.setPhoneNumber("676767677");
        promoter2.setBankAccount("11113434343434/0000");

        Integer id1 = repo.create(promoter1);
        assertNotNull(id1);
        Integer id2 = repo.create(promoter2);
        assertNotNull(id2);

        assertEquals("", repo.findBy(id1).get().getTitle());
        assertEquals("Petr", repo.findBy(id1).get().getFirstName());
        assertEquals("Svetr", repo.findBy(id1).get().getLastName());
        assertEquals("petr.svetr@vecirky.cz", repo.findBy(id1).get().getEmail());
        assertEquals("232323233", repo.findBy(id1).get().getPhoneNumber());
        assertEquals("11113434343434/0000", repo.findBy(id1).get().getBankAccount());

        assertEquals("", repo.findBy(id2).get().getTitle());
        assertEquals("Michael", repo.findBy(id2).get().getFirstName());
        assertEquals("Nezajel", repo.findBy(id2).get().getLastName());
        assertEquals("michael.nezajel@vecirky.cz", repo.findBy(id2).get().getEmail());
        assertEquals("676767677", repo.findBy(id2).get().getPhoneNumber());
        assertEquals("11113434343434/0000", repo.findBy(id2).get().getBankAccount());

        assertEquals(2, repo.findAll().size());
    }


    @Test
    public void it_can_update_a_record_and_retrieve_it_back() {

        assertEquals(0, repo.findAll().size());

        Promoter promoter1 = new Promoter();
        promoter1.setTitle("");
        promoter1.setFirstName("Petr");
        promoter1.setLastName("Svetr");
        promoter1.setEmail("petr.svetr@vecirky.cz");
        promoter1.setPhoneNumber("232323233");
        promoter1.setBankAccount("11113434343434/0000");

        Promoter promoter2 = new Promoter();
        promoter2.setTitle("");
        promoter2.setFirstName("Michael");
        promoter2.setLastName("Nezajel");
        promoter2.setEmail("michael.nezajel@vecirky.cz");
        promoter2.setPhoneNumber("676767677");
        promoter2.setBankAccount("11113434343434/0000");

        Integer id1 = repo.create(promoter1);
        assertNotNull(id1);
        Integer id2 = repo.create(promoter2);
        assertNotNull(id2);

        assertEquals(2, repo.findAll().size());

        promoter1 = repo.findBy(id1).get();
        assertNotNull(promoter1);

        assertEquals(2, repo.findAll().size());

        promoter1.setTitle("Bc.");
        promoter1.setPhoneNumber("123123123");
        repo.update(promoter1);

        assertEquals(2, repo.findAll().size());

        assertEquals("Bc.", repo.findBy(id1).get().getTitle());
        assertEquals("Petr", repo.findBy(id1).get().getFirstName());
        assertEquals("Svetr", repo.findBy(id1).get().getLastName());
        assertEquals("petr.svetr@vecirky.cz", repo.findBy(id1).get().getEmail());
        assertEquals("123123123", repo.findBy(id1).get().getPhoneNumber());
        assertEquals("11113434343434/0000", repo.findBy(id1).get().getBankAccount());

        assertEquals("", repo.findBy(id2).get().getTitle());
        assertEquals("Michael", repo.findBy(id2).get().getFirstName());
        assertEquals("Nezajel", repo.findBy(id2).get().getLastName());
        assertEquals("michael.nezajel@vecirky.cz", repo.findBy(id2).get().getEmail());
        assertEquals("676767677", repo.findBy(id2).get().getPhoneNumber());
        assertEquals("11113434343434/0000", repo.findBy(id2).get().getBankAccount());

        assertEquals(2, repo.findAll().size());
    }

    @Test
    public void it_can_properly_delete_a_record() {

        assertEquals(0, repo.findAll().size());

        Promoter promoter1 = new Promoter();
        promoter1.setTitle("");
        promoter1.setFirstName("Petr");
        promoter1.setLastName("Svetr");
        promoter1.setEmail("petr.svetr@vecirky.cz");
        promoter1.setPhoneNumber("232323233");
        promoter1.setBankAccount("11113434343434/0000");

        Promoter promoter2 = new Promoter();
        promoter2.setTitle("");
        promoter2.setFirstName("Michael");
        promoter2.setLastName("Nezajel");
        promoter2.setEmail("michael.nezajel@vecirky.cz");
        promoter2.setPhoneNumber("676767677");
        promoter2.setBankAccount("11113434343434/0000");

        Integer id1 = repo.create(promoter1);
        assertNotNull(id1);
        Integer id2 = repo.create(promoter2);
        assertNotNull(id2);

        assertEquals(2, repo.findAll().size());

        promoter1 = repo.findBy(id1).get();
        assertNotNull(promoter1);
        promoter2 = repo.findBy(id2).get();
        assertNotNull(promoter2);


        repo.delete(promoter1);
        assertEquals(1, repo.findAll().size());
        repo.delete(promoter2);
        assertEquals(0, repo.findAll().size());

    }
}
