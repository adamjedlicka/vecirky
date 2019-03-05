package vecirky;

import javafx.scene.control.Alert;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class G {

    public static URL getResource(String name) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        return loader.getResource(name);
    }

    public static String getResourcePath(String name) {
        return getResource(name).getFile();
    }

    public static LocalDate toLocalDate(Date dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }

        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date toDate(LocalDate dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }

        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText(message);
        alert.show();
    }

    public static Integer toInteger(String s) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
