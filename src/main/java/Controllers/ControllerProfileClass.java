package Controllers;

import Components.Profile.ProfileClass;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerProfileClass {

    private final CoreDatabase coreDatabase;

    public ControllerProfileClass(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public List<ProfileClass> getProfileClasses() {
        List<ProfileClass> profileClasses = new ArrayList<>();
        String query = "SELECT uid, name FROM user_class";

        try {
            Statement statement = coreDatabase.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                ProfileClass profileClass = new ProfileClass(resultSet.getLong(1));
                profileClass.setName(resultSet.getString(2));
                profileClasses.add(profileClass);
            }

            statement.close();
        } catch (SQLException | InvalidValue e) {
            e.printStackTrace();
        }

        return profileClasses;
    }

    public void getProfileClass(ProfileClass profileClass) throws InvalidValue, SQLException {
        String query = "SELECT uid, name FROM user_class WHERE uid=" + profileClass.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            profileClass.setUid(resultSet.getLong(1));
            profileClass.setName(resultSet.getString(2));
        }

        statement.close();
    }
}
