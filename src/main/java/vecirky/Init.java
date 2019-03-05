package vecirky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;

import jeda00.container.Bindable;
import jeda00.eventbus.DefaultEventBus;
import jeda00.eventbus.EventBus;

import vecirky.database.MigrationsManager;
import vecirky.repositories.AddressRepository;
import vecirky.repositories.ClientRepository;
import vecirky.repositories.EventRepository;
import vecirky.repositories.EventTypeRepository;
import vecirky.repositories.PromoterRepository;
import vecirky.repositories.database.DatabaseAddressRepository;
import vecirky.repositories.database.DatabaseClientRepository;
import vecirky.repositories.database.DatabaseEventRepository;
import vecirky.repositories.database.DatabaseEventTypeRepository;
import vecirky.repositories.database.DatabasePromoterRepository;
import vecirky.support.Config;
import vecirky.util.PropertiesConfig;

public class Init {

    public static Bindable container;

    public static void init(Consumer<Config> configurator) {
        container = C.reset();

        Config config = new PropertiesConfig("config.properties");

        container.bindSingletonInstance(Config.class, config);

        configurator.accept(config);

        container.bindSingletonFactory(EventBus.class, DefaultEventBus::new);

        bindDatabaseConnection();

        bindRepositories();

        L.setLocale(C.config().get("LOCALE", "cs"));
    }

    public static void init() {
        init(c -> {
        });
    }

    public static void runMigrations() {
        MigrationsManager migrationsManager = new MigrationsManager();
        boolean ok = migrationsManager.runMigrations();

        if (!ok) {
            System.err.println("Migrations were not executed successfully...");
            System.exit(1);
        }
    }

    private static void bindDatabaseConnection() {
        try {
            String dbConnection = C.config().get("DB_CONNECTION");
            String dbDatabase = C.config().get("DB_DATABASE");

            Connection connection = DriverManager.getConnection("jdbc:" + dbConnection + ":" + dbDatabase);
            connection.createStatement().execute("PRAGMA foreign_keys = ON;");
            container.bindSingletonInstance(Connection.class, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void bindRepositories() {
        container.bindFactory(AddressRepository.class, DatabaseAddressRepository::new);
        container.bindFactory(EventTypeRepository.class, DatabaseEventTypeRepository::new);
        container.bindFactory(ClientRepository.class, DatabaseClientRepository::new);
        container.bindFactory(PromoterRepository.class, DatabasePromoterRepository::new);
        container.bindFactory(EventRepository.class, DatabaseEventRepository::new);
    }

}
