package vecirky.database.factories;

import org.junit.Before;
import org.junit.Test;
import vecirky.BaseTest;
import vecirky.C;
import vecirky.repositories.PromoterRepository;

import static junit.framework.TestCase.assertEquals;

public class PromoterFactoryTest extends BaseTest {

    public PromoterRepository repository;

    @Before
    public void before() {
        repository = C.get(PromoterRepository.class);
    }

    @Test
    public void it_creates_promoters() {
        PromoterFactory.create();

        assertEquals(1, repository.findAll().size());
    }

}
