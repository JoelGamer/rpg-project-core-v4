package Components.Profile;

import Components.Component;
import Controllers.ControllerProfileClass;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.SQLException;

public class ProfileClass extends Component {

    private String name;

    public ProfileClass(long uid) throws InvalidValue {
        super(uid);
    }

    public ProfileClass retrieveProfileClassData(CoreDatabase coreDatabase) throws InvalidValue, SQLException {
        new ControllerProfileClass(coreDatabase).getProfileClass(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidValue {
        if(name == null || name.equals("")) throw new InvalidValue(this.getClass().getName() + ".setName()");
        this.name = name;
    }
}
