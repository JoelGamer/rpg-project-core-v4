package Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code CoreDatabase} class is responsible to connect the database for the controllers
 *
 * @author Guilherme Theodoro
 * @since 0.0.1
 * @version 0.0.1
 */

public class CoreDatabase {

    private final Connection connection;

    public CoreDatabase(Core core) throws SQLException {
        this.connection = performDatabaseConnection(core.databaseUrlToString(), core.getDatabaseUser(), core.getDatabasePassword());
    }

    private Connection performDatabaseConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }
}
