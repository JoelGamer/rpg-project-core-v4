package Components.Guild;

import Components.Component;
import Components.Location.Location;
import Components.Profile.Profile;
import Controllers.ControllerGuild;
import Core.CoreDatabase;
import Exceptions.InvalidValue;
import Exceptions.Unauthorized;

import java.sql.SQLException;
import java.util.Calendar;

public class Guild extends Component {

    private String name;
    private Profile owner;
    private Location location;
    private long money;
    private GuildRank guildRank;
    private Calendar guildCreationDate;

    public Guild(long uid) throws InvalidValue {
        super(uid);
    }

    public Guild() {
        buildEmptyGuild();
    }

    public Guild retrieveGuildData(CoreDatabase coreDatabase) throws InvalidValue, SQLException, Unauthorized {
        new ControllerGuild(coreDatabase).getGuild(this);
        return this;
    }

    private void buildEmptyGuild() {
        this.name = "N/A";
        this.owner = null;
        this.location = null;
        this.money = 0;
        this.guildRank = null;
        this.guildCreationDate = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public GuildRank getGuildRank() {
        return guildRank;
    }

    public void setGuildRank(GuildRank guildRank) {
        this.guildRank = guildRank;
    }

    public Calendar getGuildCreationDate() {
        return guildCreationDate;
    }

    public void setGuildCreationDate(Calendar guildCreationDate) {
        this.guildCreationDate = guildCreationDate;
    }
}
