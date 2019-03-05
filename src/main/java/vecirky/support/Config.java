package vecirky.support;

public interface Config {

    public String get(String key);

    public String get(String key, String defaultValue);

    public void set(String key, String value);

}
