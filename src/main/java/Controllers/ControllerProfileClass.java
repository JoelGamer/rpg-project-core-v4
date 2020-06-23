package Controllers;

import Components.Profile.ProfileClass;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerProfileClass {

    private final CoreDatabase coreDatabase;

    public ControllerProfileClass(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getProfileClass(ProfileClass profileClass) throws SQLException, InvalidValue {
        String query = "SELECT uid_class, name_class " +
                "FROM user_class " +
                "WHERE uid_class=" + profileClass.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            profileClass.setUid(resultSet.getLong(1));
            profileClass.setName(resultSet.getString(2));
        }

        statement.close();
    }
}
