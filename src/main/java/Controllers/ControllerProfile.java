package Controllers;

import Components.Guild.Guild;
import Components.Location.Location;
import Components.Profile.Profile;
import Components.Profile.ProfileClass;
import Components.Profile.ProfileMarriage;
import Components.Profile.ProfileRank;
import Core.CoreDatabase;
import Exceptions.AccessDenied;
import Exceptions.InvalidValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class ControllerProfile {

    private final CoreDatabase coreDatabase;

    public ControllerProfile(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getProfile(Profile profile) throws AccessDenied, InvalidValue, SQLException {
        String searchMethod = (profile.getUid() > 0) ? "uid_user=" + profile.getUid() : "id_user='" + profile.getDiscordID() + "'";
        String query = "SELECT uid_user,id_user,level_user,experience_user,uid_class,uid_rank,power_user,money_user,uid_marriage,uid_guild,uid_location,cdate_user,clocation_user " +
                "FROM user " +
                "WHERE " + searchMethod;

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            profile.setUid(resultSet.getLong(1));
            profile.setDiscordID(resultSet.getString(2));
            profile.setLevel(resultSet.getInt(3));
            profile.setExperience(resultSet.getLong(4));
            profile.setProfileClass(new ProfileClass(resultSet.getLong(5)).retrieveProfileClassData(coreDatabase));
            profile.setProfileRank(new ProfileRank(resultSet.getLong(6)).retrieveProfileRankData(coreDatabase));
            profile.setPower(resultSet.getLong(7));
            profile.setMoney(resultSet.getLong(8));
            profile.setProfileMarriage(createProfileMarriage(resultSet.getLong(9)));
            profile.setGuild(createGuild(resultSet.getLong(10)));
            profile.setLocation(new Location(resultSet.getLong(11)).retrieveLocationData(coreDatabase));

            Calendar calendar = new Calendar.Builder().build();
            calendar.setTime(resultSet.getDate(12));
            profile.setProfileCreationDate(calendar);

            profile.setProfileCreationLocation(new Location(resultSet.getInt(13)).retrieveLocationData(coreDatabase));
        }

        if(profile.getUid() < 1) throw new AccessDenied(profile.getDiscordID());

        statement.close();
    }

    public void getSimplerProfile(Profile profile) throws InvalidValue, SQLException {
        String query = "SELECT uid_user, id_user " +
                "FROM user " +
                "WHERE uid_user=" + profile.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            profile.setUid(resultSet.getLong(1));
            profile.setDiscordID(resultSet.getString(2));
        }

        statement.close();
    }

    private ProfileMarriage createProfileMarriage(long uid) throws AccessDenied, InvalidValue, SQLException {
        if(uid > 0) return new ProfileMarriage(uid).retrieveProfileMarriage(coreDatabase);
        return new ProfileMarriage();
    }

    private Guild createGuild(long uid) throws AccessDenied, InvalidValue, SQLException {
        if(uid > 0) return new Guild(uid).retrieveGuildData(coreDatabase);
        return new Guild();
    }
}
