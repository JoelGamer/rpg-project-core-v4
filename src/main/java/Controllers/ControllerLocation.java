package Controllers;

import Components.Component;
import Components.Location.Location;
import Components.Location.LocationType;
import Core.CoreDatabase;
import Enums.LocationTypes;
import Exceptions.InvalidValue;
import Exceptions.NonExistentLocation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerLocation {

    private final CoreDatabase coreDatabase;

    public ControllerLocation(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getLocation(Location location) throws InvalidValue, SQLException {
        String query = "SELECT uid, name, type, linked_location " +
                "FROM location " +
                "WHERE uid=" + location.getUid();

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

    public Location getLocationByLinkedLocation(LocationTypes locationType, int uid) throws InvalidValue, NonExistentLocation, SQLException {
        Location location = null;
        String query = "SELECT uid, name, type, linked_location " +
                "FROM location " +
                "WHERE type=" + locationType.getType() + " AND linked_location=" + uid;

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            location = new Location(resultSet.getLong(1));
            location.setName(resultSet.getString(2));
            location.setLocationType(new LocationType(resultSet.getLong(3)).retrieveLocationTypeData(coreDatabase));
            location.setLinkedLocation(new Component(resultSet.getLong(4)));
        }

        statement.close();

        if (location == null) throw new NonExistentLocation();

        return location;
    }

    public void createLocation(Location location) throws SQLException {
        String query = "INSERT INTO location(name, type, linked_location) VALUES('" +
                location.getName() + "', " +
                location.getLocationType().getUid() + ", " +
                location.getLinkedLocation().getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        statement.execute(query);
        statement.close();
    }
}
