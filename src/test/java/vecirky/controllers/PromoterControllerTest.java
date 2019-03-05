package vecirky.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import vecirky.BaseTest;
import org.junit.rules.ExpectedException;
import vecirky.database.factories.PromoterFactory;
import vecirky.exceptions.ModelIsInvalid;
import vecirky.exceptions.ModelNotFound;
import vecirky.models.Promoter;

public class PromoterControllerTest extends BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public PromoterController controller;

    @Before
    public void before() {
        controller = new PromoterController();
    }

    @Test
    public void promoter_can_be_created() throws ModelIsInvalid {
        Promoter promoter = PromoterFactory.make();

        assertNotNull(controller.create(promoter));

        assertEquals(1, controller.index().size());
    }

    @Test
    public void promoter_can_be_retireved() throws ModelIsInvalid, ModelNotFound {
        Promoter promoter1 = PromoterFactory.make();
        promoter1 = controller.create(promoter1);

        Promoter promoter2 = PromoterFactory.make();
        promoter2 = controller.create(promoter2);

        Promoter promoter3 = controller.show(promoter1.getId());
        assertEquals(promoter1.getId(), promoter3.getId());

        Promoter promoter4 = controller.show(promoter2.getId());
        assertEquals(promoter2.getId(), promoter4.getId());
    }

    @Test
    public void promoter_can_be_updated() throws ModelIsInvalid, ModelNotFound {
        Promoter promoter = PromoterFactory.make();
        controller.create(promoter);

        promoter.setFirstName("Newname");
        controller.update(promoter);

        Promoter newPromoter = controller.show(promoter.getId());

        assertEquals("Newname", newPromoter.getFirstName());
    }
}
