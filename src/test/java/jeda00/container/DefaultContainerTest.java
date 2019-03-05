package jeda00.container;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class DefaultContainerTest {

    public DefaultContainer container;

    @Before
    public void setUp() {
        container = new DefaultContainer();
    }

    @Test
    public void interface_can_be_resolved() {
        container.bindFactory(Adder.class, SimpleAdder::new);

        Adder adder = container.get(Adder.class);

        assertNotNull(adder);

        assertEquals(2, adder.add(1, 1));
    }

    @Test
    public void has_method_works() {
        assertFalse(container.has(Adder.class));
        assertFalse(container.has(Multiplier.class));

        container.bindFactory(Adder.class, SimpleAdder::new);

        assertTrue(container.has(Adder.class));
        assertFalse(container.has(Multiplier.class));
    }

    @Test
    public void singleton_can_be_resolved() {
        container.bindSingletonFactory(Adder.class, SimpleAdder::new);

        Adder adderResolved1 = container.get(Adder.class);
        Adder adderResolved2 = container.get(Adder.class);

        assertEquals(adderResolved1, adderResolved2);
    }

    @Test
    public void singleton_instance_can_be_resolved() {
        Adder adder = new SimpleAdder();

        container.bindSingletonInstance(Adder.class, adder);

        Adder adderResolved = container.get(Adder.class);

        assertEquals(adder, adderResolved);
    }

    @Test
    public void multiple_interfaces_can_be_resolved() {
        container.bindFactory(Adder.class, SimpleAdder::new);
        container.bindFactory(Multiplier.class, SimpleMultiplier::new);

        Adder adder = container.get(Adder.class);
        Multiplier multiplier = container.get(Multiplier.class);

        assertNotEquals(adder, multiplier);

        assertEquals(6, adder.add(3, 3));
        assertEquals(9, multiplier.multiply(3, 3));
    }

    @Test
    public void interface_implementation_can_be_replaced() {
        container.bindFactory(Adder.class, SimpleAdder::new);

        Adder adder = container.get(Adder.class);
        assertEquals(2, adder.add(1, 1));

        container.bindFactory(Adder.class, BadAdder::new);

        Adder subber = container.get(Adder.class);
        assertEquals(0, subber.add(1, 1));
    }

    @Test
    public void interface_singleton_implementation_can_be_replaced() {
        container.bindSingletonFactory(Adder.class, SimpleAdder::new);

        Adder adder = container.get(Adder.class);
        assertEquals(2, adder.add(1, 1));

        container.bindSingletonFactory(Adder.class, BadAdder::new);

        Adder subber = container.get(Adder.class);
        assertEquals(0, subber.add(1, 1));
        assertNotEquals(adder, subber);
    }

}
