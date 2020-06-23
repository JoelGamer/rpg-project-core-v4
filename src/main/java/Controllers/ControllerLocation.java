package Controllers;

import Components.Component;
import Components.Location.Location;
import Components.Location.LocationType;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerLocation {

    private final CoreDatabase coreDatabase;

    public ControllerLocation(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getLocation(Location location) throws InvalidValue, SQLException {
        String query = "SELECT uid_location, name_location, type_location, uid_linked_location " +
                "FROM location " +
                "WHERE uid_location=" + location.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            location.setUid(resultSet.getLong(1));
            location.setName(resultSet.getString(2));
            location.setLocationType(new LocationType(resultSet.getLong(3)).retrieveLocationTypeData(coreDatabase));
            location.setLinkedLocation(new Component(resultSet.getLong(4)));
        }

        statement.close();
    }
}
