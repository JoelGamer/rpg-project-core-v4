package Controllers;

import Components.Location.LocationType;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerLocationType {

    private final CoreDatabase coreDatabase;

    public ControllerLocationType(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getLocationType(LocationType locationType) throws InvalidValue, SQLException {
        String query = "SELECT uid, name, referenced_table " +
                "FROM location_type " +
                "WHERE uid=" + locationType.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            locationType.setUid(resultSet.getLong(1));
            locationType.setName(resultSet.getString(2));
            locationType.setAttachedTable(resultSet.getString(3));
        }

        statement.close();
    }
}
