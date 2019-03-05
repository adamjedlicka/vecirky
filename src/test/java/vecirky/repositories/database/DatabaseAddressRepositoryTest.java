package vecirky.repositories.database;

import org.junit.Before;
import org.junit.Test;
import vecirky.BaseTest;
import vecirky.models.Address;

import static org.junit.Assert.*;

public class DatabaseAddressRepositoryTest extends BaseTest {

    private DatabaseAddressRepository repo;

    @Before
    public void setUp() {
        repo = new DatabaseAddressRepository();
    }

    @Test
    public void it_can_save_and_retrie_records_from_database() {

        assertEquals(0, repo.findAll().size());

        Address adr = new Address();
        adr.setCity("Statenice");
        adr.setStreet("U Krize");
        adr.setNumberOfDescriptive(456789);
        adr.setZipCode(11111);

        Integer id1 = repo.create(adr);
        assertNotNull(id1);

        assertEquals(1, repo.findAll().size());

        assertEquals("Statenice", repo.findBy(id1).get().getCity());
        assertEquals("U Krize", repo.findBy(id1).get().getStreet());
        assertEquals(456789, (int) repo.findBy(id1).get().getNumberOfDescriptive());
        assertEquals(11111, (int) repo.findBy(id1).get().getZipCode());
    }

    @Test
    public void it_can_retrie_specific_record_from_database() {
        Address adr1 = new Address();
        adr1.setCity("Statenice");
        adr1.setStreet("U Krize");
        adr1.setNumberOfDescriptive(456789);
        adr1.setZipCode(11111);

        Address adr2 = new Address();
        adr2.setCity("Horomerice");
        adr2.setStreet("babajaga");
        adr2.setNumberOfDescriptive(12345);
        adr2.setZipCode(76543);

        Integer id1 = repo.create(adr1);
        assertNotNull(id1);
        Integer id2 = repo.create(adr2);
        assertNotNull(id2);

        assertEquals("Statenice", repo.findBy(id1).get().getCity());
        assertEquals("U Krize", repo.findBy(id1).get().getStreet());
        assertEquals(456789, (int) repo.findBy(id1).get().getNumberOfDescriptive());
        assertEquals(11111, (int) repo.findBy(id1).get().getZipCode());

        assertEquals("Horomerice", repo.findBy(id2).get().getCity());
        assertEquals("babajaga", repo.findBy(id2).get().getStreet());
        assertEquals(12345, (int) repo.findBy(id2).get().getNumberOfDescriptive());
        assertEquals(76543, (int) repo.findBy(id2).get().getZipCode());

        assertEquals(2, repo.findAll().size());
    }

    @Test
    public void it_can_update_a_record_and_retrieve_it_back() {
        Address adr1 = new Address();
        adr1.setCity("Statenice");
        adr1.setStreet("U Krize");
        adr1.setNumberOfDescriptive(456789);
        adr1.setZipCode(11111);

        Address adr2 = new Address();
        adr2.setCity("Horomerice");
        adr2.setStreet("babajaga");
        adr2.setNumberOfDescriptive(12345);
        adr2.setZipCode(76543);

        Integer id1 = repo.create(adr1);
        assertNotNull(id1);
        Integer id2 = repo.create(adr2);
        assertNotNull(id2);

        adr1 = repo.findBy(id1).get();
        adr1.setCity("Praha");
        adr1.setStreet("Italska");
        adr1.setNumberOfDescriptive(7898789);
        adr1.setZipCode(567765);
        repo.update(adr1);

        assertEquals("Praha", repo.findBy(id1).get().getCity());
        assertEquals("Italska", repo.findBy(id1).get().getStreet());
        assertEquals(7898789, (int) repo.findBy(id1).get().getNumberOfDescriptive());
        assertEquals(567765, (int) repo.findBy(id1).get().getZipCode());

        assertEquals("Horomerice", repo.findBy(id2).get().getCity());
        assertEquals("babajaga", repo.findBy(id2).get().getStreet());
        assertEquals(12345, (int) repo.findBy(id2).get().getNumberOfDescriptive());
        assertEquals(76543, (int) repo.findBy(id2).get().getZipCode());

        assertEquals(2, repo.findAll().size());
    }

    @Test
    public void it_can_properly_delete_a_record() {
        Address adr1 = new Address();
        adr1.setCity("Statenice");
        adr1.setStreet("U Krize");
        adr1.setNumberOfDescriptive(456789);
        adr1.setZipCode(11111);

        Address adr2 = new Address();
        adr2.setCity("Horomerice");
        adr2.setStreet("babajaga");
        adr2.setNumberOfDescriptive(12345);
        adr2.setZipCode(76543);

        Integer id1 = repo.create(adr1);
        assertNotNull(id1);
        Integer id2 = repo.create(adr2);
        assertNotNull(id2);

        adr1 = repo.findBy(id1).get();
        repo.delete(adr1);

        assertEquals(false, repo.findBy(id1).isPresent());
        assertEquals(1, repo.findAll().size());

        assertEquals("Horomerice", repo.findBy(id2).get().getCity());
        assertEquals("babajaga", repo.findBy(id2).get().getStreet());
        assertEquals(12345, (int) repo.findBy(id2).get().getNumberOfDescriptive());
        assertEquals(76543, (int) repo.findBy(id2).get().getZipCode());
    }


}
