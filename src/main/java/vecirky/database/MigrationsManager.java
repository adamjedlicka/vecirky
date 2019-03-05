package vecirky.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vecirky.C;
import vecirky.G;

public class MigrationsManager {

    private final Connection connection;

    private final String migrationsDirectory;

    private final String migrationsTable;

    public MigrationsManager() {
        this("migrations", "db_migrations");
    }

    public MigrationsManager(String directory, String table) {
        connection = C.get(Connection.class);
        migrationsDirectory = C.config().get("DB_MIGRATIONS_DIRECTORY", directory);
        migrationsTable = C.config().get("DB_MIGRATIONS_TABLE", table);

        createMigrationsTable();
    }

    /**
     * Creates migration table in the dazabase only if it does not already exists
     *
     * @return Indication if SQL command run successfully
     */
    public boolean createMigrationsTable() {
        Statement statement;
        try {
            statement = connection.createStatement();
            return statement.execute(sqlCreateTable());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Executes all migrations which were not already executed
     *
     * @return Indication if migrations were run successfully
     */
    public boolean runMigrations() {
        List<String> executedMigrations = getExecutedMigrations();
        List<String> allMigrations = getAllMigrations();

        allMigrations.removeAll(executedMigrations);

        for (String migration : allMigrations) {
            boolean result = runMigration(migration);
            if (result == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the list of names of all already executed migrations. Executed
     * migrations are stored in the migrations table in the databse.
     *
     * @return List of all already executed migrations
     */
    public List<String> getExecutedMigrations() {
        List<String> executedMigrations = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + migrationsTable);
            while (resultSet.next()) {
                String migration = resultSet.getString("migration");
                executedMigrations.add(migration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return executedMigrations;
    }

    /**
     * Returns names of all migrations in the migration. directory.
     *
     * TODO : Does not work when there is a space character in the path.
     * 
     * @return List of names of all migrations in the migrations directory
     */
    public List<String> getAllMigrations() {
        List<String> allMigrations = new ArrayList<>();

        File directory = new File(G.getResourcePath(migrationsDirectory));

        File[] files = directory.listFiles();
        if (files == null) {
            return allMigrations;
        }

        Arrays.sort(files);

        for (File file : files) {
            allMigrations.add(file.getName());
        }

        return allMigrations;
    }

    /**
     * Executes single migration defined by its file name passed in as parameter
     *
     * @param migration Name of the file in the migrations directory
     * @return Indicator if the migration was successfull
     */
    public boolean runMigration(String migration) {
        File file = new File(G.getResourcePath(migrationsDirectory), migration);

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            String content = String.join("\n", lines);

            Statement statement = connection.createStatement();
            statement.execute(content);

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertMigration());
            preparedStatement.setString(1, migration);
            preparedStatement.execute();

            // System.out.print("Executed migration: ");
            // System.out.println(migration);
        } catch (IOException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    private String sqlCreateTable() {
        return "CREATE TABLE IF NOT EXISTS " + migrationsTable + " ( migration TEXT PRIMARY KEY );";
    }

    private String sqlInsertMigration() {
        return "INSERT INTO " + migrationsTable + " (migration) VALUES (?);";
    }

}
