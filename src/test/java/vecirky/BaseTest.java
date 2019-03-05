package vecirky;

import org.junit.Before;

public abstract class BaseTest {

    @Before
    public void setUpBaseTest() {
        Init.init(config -> {
            config.set("DB_DATABASE", ":memory:");
        });

        Init.runMigrations();
    }

}
