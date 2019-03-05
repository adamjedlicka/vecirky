package vecirky.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import vecirky.support.Config;

public class PropertiesConfig implements Config {

    private final Properties properties;

    private final String file;

    private final Map<String, String> overrides;

    public PropertiesConfig(String file) {
        this.properties = new Properties();
        this.file = file;
        this.overrides = new HashMap<>();

        FileInputStream fis;
        try {
            fis = new FileInputStream(this.file);
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String get(String key) {
        String override = overrides.get(key);
        if (override != null) {
            return override;
        }

        return properties.getProperty(key);
    }

    @Override
    public String get(String key, String defaultValue) {
        String value = get(key);

        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    @Override
    public void set(String key, String value) {
        overrides.put(key, value);
    }

}
