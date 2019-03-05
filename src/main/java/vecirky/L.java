package vecirky;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import vecirky.util.UTF8Control;

public class L {

    private static String locale = "cs";

    private static String backupLocale = "cs";

    private static Map<String, ResourceBundle> bundles;

    static {
        bundles = new HashMap<>();

        Locale.setDefault(new Locale(backupLocale));
    }

    private L() {

    }

    /**
     * Returns string from the properties file stored in resources/lang directory.
     * Use dot notation to retrieve desired value.
     *
     * For example if you want to retrieve value behind key named 'c' in a/b
     * directory use a.b.c as input argument.
     *
     * @param key Name of the file and key in dotation
     * @return Coresponding value.
     */
    public static String get(String key) {
        String fullKey = "lang." + key;
        String[] fullPath = fullKey.split("\\.");
        String[] path = new String[fullPath.length - 1];
        for (int i = 0; i < path.length; i++) {
            path[i] = fullPath[i];
        }

        String locator = String.join(".", path);

        if (!bundles.containsKey(locator)) {
            bundles.put(locator, ResourceBundle.getBundle(locator, new Locale(locale), new UTF8Control()));
        }

        return bundles.get(locator).getString(fullPath[fullPath.length - 1]);
    }

    /**
     * Sets the locale
     *
     * @param locale Locale
     */
    public static void setLocale(String locale) {
        L.locale = locale;

        // Reset cached locale strings
        bundles = new HashMap<>();
    }

}
