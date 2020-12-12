package Controllers;

import Components.Guild.GuildRank;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerGuildRank {

    private final CoreDatabase coreDatabase;

    public ControllerGuildRank(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getGuildRank(GuildRank guildRank) throws InvalidValue, SQLException {
        String query = "SELECT uid, name, total_minimum_power, user_minimum_level " +
                "FROM guild_rank " +
                "WHERE uid=" + guildRank.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            guildRank.setUid(resultSet.getLong(1));
            guildRank.setName(resultSet.getString(2));
            guildRank.setTotalPowerMinimumRank(resultSet.getLong(3));
            guildRank.setUserLevelMinimumRank(resultSet.getInt(4));
        }

        statement.close();
    }
}
