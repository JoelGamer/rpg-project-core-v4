package Components.Profile;

import Components.Component;
import Components.Location.Location;
import Controllers.ControllerProfileMarriage;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.SQLException;
import java.util.Calendar;

public class ProfileMarriage extends Component {

    private Profile firstUserMarriage;
    private Profile secondUserMarriage;
    private Location marriageLocation;
    private Calendar marriageDate;

    public ProfileMarriage(long uid) throws InvalidValue {
        super(uid);
    }

    public ProfileMarriage() {
        buildEmptyMarriage();
    }

    public ProfileMarriage retrieveProfileMarriage(CoreDatabase coreDatabase) throws InvalidValue, SQLException {
        new ControllerProfileMarriage(coreDatabase).getProfileMarriage(this);
        return this;
    }

    private void buildEmptyMarriage() {
        this.firstUserMarriage = null;
        this.secondUserMarriage = null;
        this.marriageLocation = null;
        this.marriageDate = null;
    }

    public Profile getFirstUserMarriage() {
        return firstUserMarriage;
    }

    public void setFirstUserMarriage(Profile firstUserMarriage) {
        this.firstUserMarriage = firstUserMarriage;
    }

    public Profile getSecondUserMarriage() {
        return secondUserMarriage;
    }

    public void setSecondUserMarriage(Profile secondUserMarriage) {
        this.secondUserMarriage = secondUserMarriage;
    }

    public Location getMarriageLocation() {
        return marriageLocation;
    }

    public void setMarriageLocation(Location marriageLocation) {
        this.marriageLocation = marriageLocation;
    }

    public Calendar getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(Calendar marriageDate) {
        this.marriageDate = marriageDate;
    }
}
