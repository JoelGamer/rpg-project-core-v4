package Components.Profile;

import Components.Component;
import Controllers.ControllerProfileRank;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.SQLException;

public class ProfileRank extends Component {

    private String name;
    private long minimumPowerRank;

    public ProfileRank(long uid) throws InvalidValue {
        super(uid);
    }

    public ProfileRank retrieveProfileRankData(CoreDatabase coreDatabase) throws InvalidValue, SQLException {
        new ControllerProfileRank(coreDatabase).getProfileRank(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidValue {
        if(name == null || name.equals("")) throw new InvalidValue(this.getClass().getName() + ".setName()");
        this.name = name;
    }

    public long getMinimumPowerRank() {
        return minimumPowerRank;
    }

    public void setMinimumPowerRank(long minimumPowerRank) throws InvalidValue {
        if(minimumPowerRank < 0) throw new InvalidValue(this.getClass().getName() + ".setMinimumPowerRank()");
        this.minimumPowerRank = minimumPowerRank;
    }
}
