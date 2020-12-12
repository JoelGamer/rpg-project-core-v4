package Controllers;

import Components.Guild.Guild;
import Components.Guild.GuildRank;
import Components.Location.Location;
import Components.Profile.Profile;
import Core.CoreDatabase;
import Exceptions.InvalidValue;
import Exceptions.Unauthorized;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class ControllerGuild {

    private final CoreDatabase coreDatabase;

    public ControllerGuild(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getGuild(Guild guild) throws InvalidValue, SQLException, Unauthorized {
        String query = "SELECT uid, name, owner, location, money, rank, creation_date " +
                "FROM guild " +
                "WHERE uid=" + guild.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            guild.setUid(resultSet.getLong(1));
            guild.setName(resultSet.getString(2));
            guild.setOwner(new Profile(resultSet.getLong(3)).retrieveSimplerProfileData(coreDatabase));
            guild.setLocation(new Location(resultSet.getLong(4)));
            guild.setMoney(resultSet.getLong(5));
            guild.setGuildRank(new GuildRank(resultSet.getLong(6)).retrieveGuildRankData(coreDatabase));

            Calendar calendar = new Calendar.Builder().build();
            calendar.setTime(resultSet.getDate(7));
            guild.setGuildCreationDate(calendar);
        }

        statement.close();
    }
}
