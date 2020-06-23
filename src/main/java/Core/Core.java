package Core;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The {@code Core} class is responsible for everything, this class is the one that controls every other core
 * components, making it the most important out of all classes.
 *
 * <p>This class will control everything in the project. A single class that governs everything in the project.
 *
 * @author Guilherme Theodoro
 * @since 0.0.1
 * @version 0.0.1
 */

public class Core extends CoreVariables {

    private final CoreConsole coreConsole;
    private final CoreDatabase coreDatabase;

    public Core(String corePropertiesFile, String configPropertiesFile) throws IOException, SQLException {
        super(corePropertiesFile, configPropertiesFile);
        this.coreConsole = new CoreConsole(this);
        this.coreDatabase = new CoreDatabase(this);
    }

    public String databaseUrlToString() {
        return "jdbc:mysql://" + getDatabaseHost() + "/" + getDatabaseName() + "?useTimezone=true&serverTimezone=UTC&useSSL=true";
    }

    public CoreConsole coreConsole() {
        return this.coreConsole;
    }

    public CoreDatabase coreDatabase() {
        return this.coreDatabase;
    }
}
