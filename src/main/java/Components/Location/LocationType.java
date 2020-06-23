package Components.Location;

import Components.Component;
import Controllers.ControllerLocationType;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.SQLException;

public class LocationType extends Component {

    private String name;
    private String attachedTable;

    public LocationType(long uid) throws InvalidValue {
        super(uid);
    }

    public LocationType retrieveLocationTypeData(CoreDatabase coreDatabase) throws InvalidValue, SQLException {
        new ControllerLocationType(coreDatabase).getLocationType(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttachedTable() {
        return attachedTable;
    }

    public void setAttachedTable(String attachedTable) {
        this.attachedTable = attachedTable;
    }
}
