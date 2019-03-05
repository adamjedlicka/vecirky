package vecirky.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import vecirky.support.Config;

public class PropertiesConfigTest {

    @Test
    public void it_works() {
        Config cfg = new PropertiesConfig("src/test/resources/test.properties");

        assertEquals("testValue", cfg.get("testKey"));
    }

    @Test
    public void it_returns_default_value_in_not_present() {
        Config cfg = new PropertiesConfig("src/test/resources/test.properties");

        assertEquals("default", cfg.get("definetellyNotPresent", "default"));
    }

}
