package vecirky.exports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import vecirky.BaseTest;
import vecirky.models.Event;

public class ExportByEventTest extends BaseTest {

    @Test
    public void it_exports_description() {
        Event event = new Event();
        event.setDescription("popis");

        ExportByEvent export = new ExportByEvent();

        assertEquals("\tPopis:\n"+"popis"+"\n\n", export.description(event));
    }

}
