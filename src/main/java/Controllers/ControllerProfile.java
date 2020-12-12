package Controllers;

import Components.Guild.Guild;
import Components.Location.Location;
import Components.Profile.Profile;
import Components.Profile.ProfileClass;
import Components.Profile.ProfileMarriage;
import Components.Profile.ProfileRank;
import Core.CoreDatabase;
import Exceptions.Unauthorized;
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

    public void getProfile(Profile profile) throws InvalidValue, SQLException, Unauthorized {
        String searchMethod = (profile.getUid() > 0) ? "uid=" + profile.getUid() : "id='" + profile.getDiscordID() + "'";
        String query = "SELECT uid, id, level, experience, class, rank, power, money, marriage, guild, location, creation_date, creation_location " +
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
            profile.setProfileMarriage(createProfileMarriageObject(resultSet.getLong(9)));
            profile.setGuild(createGuildObject(resultSet.getLong(10)));
            profile.setLocation(new Location(resultSet.getLong(11)).retrieveLocationData(coreDatabase));

            Calendar calendar = new Calendar.Builder().build();
            calendar.setTime(resultSet.getDate(12));
            profile.setProfileCreationDate(calendar);

            profile.setProfileCreationLocation(new Location(resultSet.getInt(13)).retrieveLocationData(coreDatabase));
        }

        statement.close();

        if(profile.getUid() < 1) throw new Unauthorized(profile.getDiscordID());
    }

    public void getSimplerProfile(Profile profile) throws InvalidValue, SQLException, Unauthorized {
        String searchMethod = (profile.getUid() > 0) ? "uid=" + profile.getUid() : "id='" + profile.getDiscordID() + "'";
        String query = "SELECT uid, id " +
                "FROM user " +
                "WHERE " + searchMethod;

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            profile.setUid(resultSet.getLong(1));
            profile.setDiscordID(resultSet.getString(2));
        }

        statement.close();

        if(profile.getUid() < 1) throw new Unauthorized(profile.getDiscordID());
    }

    public void createProfile(Profile profile) throws SQLException {
        String query = "INSERT INTO user(id, level, experience, class, rank, power, money, marriage, guild, location, creation_date, creation_location) " +
                "VALUES('" +
                profile.getDiscordID() + "', " +
                profile.getLevel() + ", " +
                profile.getExperience() + ", " +
                profile.getProfileClass().getUid() + ", " +
                profile.getProfileRank().getUid() + ", " +
                profile.getPower() + ", " +
                profile.getMoney() + ", " +
                profile.getProfileMarriage() + ", " +
                profile.getGuild() + ", " +
                profile.getLocation().getUid() + ", '" +
                coreDatabase.convertCalendarToSQLString(profile.getProfileCreationDate()) + "', " +
                profile.getProfileCreationLocation().getUid() + ")";

        Statement statement = coreDatabase.getConnection().createStatement();
        statement.execute(query);
        statement.close();
    }

    private ProfileMarriage createProfileMarriageObject(long uid) throws InvalidValue, SQLException, Unauthorized {
        if(uid > 0) return new ProfileMarriage(uid).retrieveProfileMarriage(coreDatabase);
        return new ProfileMarriage();
    }

    private Guild createGuildObject(long uid) throws InvalidValue, SQLException, Unauthorized {
        if(uid > 0) return new Guild(uid).retrieveGuildData(coreDatabase);
        return new Guild();
    }
}
