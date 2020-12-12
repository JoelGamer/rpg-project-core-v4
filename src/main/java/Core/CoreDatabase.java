package Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The {@code CoreDatabase} class is responsible to connect the database for the controllers
 *
 * @author Guilherme Theodoro
 * @since 0.0.1
 * @version 0.0.1
 */

public class CoreDatabase {

    private final Core core;
    private final Connection connection;

    public CoreDatabase(Core core) throws SQLException {
        this.core = core;
        this.connection = performDatabaseConnection(this.core.databaseUrlToString(), this.core.getDatabaseUser(), this.core.getDatabasePassword());
    }

    private Connection performDatabaseConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public String convertCalendarToSQLString(Calendar calendar) {
        return new SimpleDateFormat(this.core.getDateFormats().get(2)).format(calendar.getTime());
    }

    public Connection getConnection() {
        return connection;
    }
}
