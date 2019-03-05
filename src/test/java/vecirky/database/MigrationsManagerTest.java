package vecirky.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import vecirky.Init;

public class MigrationsManagerTest {

    public MigrationsManager mm;

    @Before
    public void setUp() {
        Init.init(config -> {
            config.set("DB_DATABASE", ":memory:");
        });

        mm = new MigrationsManager("test_migrations", "db_migrations");
    }

    @Test
    public void it_has_no_migrations_at_init() {
        assertEquals(0, mm.getExecutedMigrations().size());
    }

    @Test
    public void it_successfully_runs_migrations() {
        assertTrue(mm.runMigrations());
        assertEquals(2, mm.getExecutedMigrations().size());
    }

    @Test
    public void it_returns_list_of_executed_migrations() {
        assertEquals(0, mm.getExecutedMigrations().size());
        mm.runMigrations();
        assertEquals(2, mm.getExecutedMigrations().size());
    }

    @Test
    public void it_runs_migration_only_once() {
        assertTrue(mm.runMigration("010_create_first_table.sql"));
        assertFalse(mm.runMigration("010_create_first_table.sql"));
    }

    @Test
    public void it_runs_only_nonexecuted_migrations() {
        assertEquals(0, mm.getExecutedMigrations().size());

        mm.runMigration("010_create_first_table.sql");

        assertEquals(1, mm.getExecutedMigrations().size());

        assertTrue(mm.runMigrations());

        assertEquals(2, mm.getExecutedMigrations().size());
    }

}
