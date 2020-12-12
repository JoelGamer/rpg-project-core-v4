package Controllers;

import Components.City.City;
import Components.City.CitySize;
import Components.Profile.Profile;
import Core.CoreDatabase;
import Enums.LocationTypes;
import Exceptions.InvalidValue;
import Exceptions.NonExistentLocation;
import Exceptions.Unauthorized;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class ControllerCity {

    private final CoreDatabase coreDatabase;

    public ControllerCity(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getCity(City city) throws InvalidValue, NonExistentLocation, SQLException {
        String searchMethod = (city.getUid() > 0) ? "uid=" + city.getUid() : "id='" + city.getDiscordID() + "'";
        String query = "SELECT uid, id, name, mayor, money, due_rent_date, size, created_date, discord_link " +
                "FROM city " +
                "WHERE " + searchMethod;

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            city.setUid(resultSet.getLong(1));
            city.setDiscordID(resultSet.getString(2));
            city.setLocation(new ControllerLocation(coreDatabase).getLocationByLinkedLocation(LocationTypes.CITY, (int) city.getUid()));
            city.setName(resultSet.getString(3));

            try {
                city.setMayor(new Profile(resultSet.getLong(4)).retrieveSimplerProfileData(coreDatabase));
            } catch (Unauthorized e) {
                e.printStackTrace();
            }

            city.setMoney(resultSet.getLong(5));

            Calendar defaultDueRent = new Calendar.Builder().build();
            defaultDueRent.setTime(resultSet.getDate(6));
            city.setDefaultDueRent(defaultDueRent);

            city.setSize(new CitySize(resultSet.getLong(7)).retrieveCitySize(coreDatabase));

            Calendar createdDate = new Calendar.Builder().build();
            createdDate.setTime(resultSet.getDate(8));
            city.setCreatedDate(createdDate);

            city.setDiscordLink(resultSet.getString(9));
        }

        statement.close();

        if (city.getUid() < 1) throw new NonExistentLocation();
    }

    public void getSimplerCity(City city) throws InvalidValue, NonExistentLocation, SQLException {
        String searchMethod = (city.getUid() > 0) ? "uid=" + city.getUid() : "id='" + city.getDiscordID() + "'";
        String query = "SELECT uid, id " +
                "FROM city " +
                "WHERE " + searchMethod;

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            city.setUid(resultSet.getLong(1));
            city.setDiscordID(resultSet.getString(2));
        }

        if (city.getUid() < 1) throw new NonExistentLocation();
    }

    public void createCity(City city) throws SQLException {
        String query = "INSERT INTO city(id, name, mayor, money, due_rent_date, size, created_date, discord_link) VALUES('" +
                city.getDiscordID() + "', '" +
                city.getName() + "', " +
                city.getMayor().getUid() + ", " +
                city.getMoney() + ", '" +
                coreDatabase.convertCalendarToSQLString(city.getDefaultDueRent()) + "', " +
                city.getSize().getUid() + ", '" +
                coreDatabase.convertCalendarToSQLString(city.getCreatedDate()) + "', '" +
                city.getDiscordLink() + "');";

        Statement statement = coreDatabase.getConnection().createStatement();
        statement.execute(query);
        statement.close();
    }
}
