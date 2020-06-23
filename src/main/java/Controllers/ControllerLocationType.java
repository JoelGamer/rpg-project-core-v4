package Controllers;

import Components.Location.Location;
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
        String query = "SELECT uid_location_type, name_location_type, table_location_type " +
                "FROM location_type " +
                "WHERE uid_location_type=" + locationType.getUid();

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
