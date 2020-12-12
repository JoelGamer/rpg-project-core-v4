package Controllers;

import Components.Location.Location;
import Components.Profile.Profile;
import Components.Profile.ProfileMarriage;
import Core.CoreDatabase;
import Exceptions.InvalidValue;
import Exceptions.Unauthorized;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class ControllerProfileMarriage {

    private final CoreDatabase coreDatabase;

    public ControllerProfileMarriage(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getProfileMarriage(ProfileMarriage profileMarriage) throws InvalidValue, SQLException, Unauthorized {
        String query = "SELECT uid, first_user, second_user, location, marriage_date " +
                "FROM user_marriage " +
                "WHERE uid=" + profileMarriage.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            profileMarriage.setUid(resultSet.getLong(1));
            profileMarriage.setFirstUserMarriage(new Profile(resultSet.getLong(2)).retrieveSimplerProfileData(coreDatabase));
            profileMarriage.setSecondUserMarriage(new Profile(resultSet.getLong(3)).retrieveSimplerProfileData(coreDatabase));
            profileMarriage.setMarriageLocation(new Location(resultSet.getLong(4)));

            Calendar calendar = new Calendar.Builder().build();
            calendar.setTime(resultSet.getDate(5));
            profileMarriage.setMarriageDate(calendar);
        }

        statement.close();
    }
}
