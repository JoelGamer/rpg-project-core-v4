package Components.Location;

import Components.Component;
import Controllers.ControllerLocation;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.SQLException;

public class Location extends Component {

    private String name;
    private LocationType locationType;
    private Component linkedLocation;

    public Location(long uid) throws InvalidValue {
        super(uid);
    }

    public Location retrieveLocationData(CoreDatabase coreDatabase) throws InvalidValue, SQLException {
        new ControllerLocation(coreDatabase).getLocation(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Component getLinkedLocation() {
        return linkedLocation;
    }

    public void setLinkedLocation(Component linkedLocation) {
        this.linkedLocation = linkedLocation;
    }
}
