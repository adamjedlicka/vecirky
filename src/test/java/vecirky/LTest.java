package vecirky;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LTest extends BaseTest {

    @Test
    public void it_returns_value_of_coresponding_key() {
        String value1 = L.get("strings.key1");

        assertEquals("value 1", value1);
    }

    @Test
    public void it_can_parse_composite_directory_structure() {
        String value = L.get("a.b.c.key");

        assertEquals("value", value);
    }

    @Test
    public void locale_can_be_cahnged() {
        String value1 = L.get("strings.key1");

        assertEquals("value 1", value1);

        L.setLocale("en");

        String value2 = L.get("strings.key1");

        assertEquals("value 1 but in eng", value2);
    }

}
