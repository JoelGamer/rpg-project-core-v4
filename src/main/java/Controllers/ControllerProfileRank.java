package Controllers;

import Components.Profile.ProfileRank;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerProfileRank {

    private final CoreDatabase coreDatabase;

    public ControllerProfileRank(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getProfileRank(ProfileRank profileRank) throws SQLException, InvalidValue {
        String query = "SELECT uid, name, minimum_power " +
                "FROM user_rank " +
                "WHERE uid=" + profileRank.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            profileRank.setUid(resultSet.getLong(1));
            profileRank.setName(resultSet.getString(2));
            profileRank.setMinimumPowerRank(resultSet.getLong(3));
        }

        statement.close();
    }
}
