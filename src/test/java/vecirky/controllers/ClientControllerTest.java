package vecirky.controllers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import vecirky.BaseTest;
import vecirky.exceptions.ModelIsInvalid;
import vecirky.exceptions.ModelNotFound;
import vecirky.database.factories.ClientFactory;
import vecirky.models.Client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClientControllerTest extends BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public ClientController controller;

    @Before
    public void before() {
        controller = new ClientController();
    }

    @Test
    public void client_can_be_created() throws ModelIsInvalid {
        Client client = ClientFactory.make();

        assertNotNull(controller.create(client));

        assertEquals(1, controller.index().size());
    }

    @Test
    public void clint_can_be_retireved() throws ModelIsInvalid, ModelNotFound {
        Client client1 = ClientFactory.make();
        client1 = controller.create(client1);

        Client client2 = ClientFactory.make();
        client2 = controller.create(client2);

        Client client3 = controller.show(client1.getId());
        assertEquals(client1.getId(), client3.getId());

        Client client4 = controller.show(client2.getId());
        assertEquals(client2.getId(), client4.getId());
    }

    @Test
    public void client_can_be_updated() throws ModelIsInvalid, ModelNotFound {
        Client client = ClientFactory.make();
        controller.create(client);

        client.setFirstName("Newname");
        controller.update(client);

        Client newClient = controller.show(client.getId());

        assertEquals("Newname", newClient.getFirstName());
    }

}
