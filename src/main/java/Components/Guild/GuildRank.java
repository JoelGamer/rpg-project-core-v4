package Components.Guild;

import Components.Component;
import Controllers.ControllerGuildRank;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.SQLException;

public class GuildRank extends Component {

    private String name;
    private long totalPowerMinimumRank;
    private int userLevelMinimumRank;

    public GuildRank(long uid) throws InvalidValue {
        super(uid);
    }

    public GuildRank retrieveGuildRankData(CoreDatabase coreDatabase) throws InvalidValue, SQLException {
        new ControllerGuildRank(coreDatabase).getGuildRank(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalPowerMinimumRank() {
        return totalPowerMinimumRank;
    }

    public void setTotalPowerMinimumRank(long totalPowerMinimumRank) {
        this.totalPowerMinimumRank = totalPowerMinimumRank;
    }

    public int getUserLevelMinimumRank() {
        return userLevelMinimumRank;
    }

    public void setUserLevelMinimumRank(int userLevelMinimumRank) {
        this.userLevelMinimumRank = userLevelMinimumRank;
    }
}
