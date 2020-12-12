package Components.City;

import Components.Component;
import Components.Location.Location;
import Components.Profile.Profile;
import Controllers.ControllerCity;
import Core.CoreDatabase;
import Exceptions.InvalidValue;
import Exceptions.NonExistentLocation;
import net.dv8tion.jda.core.EmbedBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class City extends Component {

    private String discordID;
    private String name;
    private Location location;
    private Profile mayor;
    private long money;
    private Calendar defaultDueRent;
    private CitySize size;
    private Calendar createdDate;
    private String discordLink;

    public City(long uid) throws InvalidValue {
        super(uid);
    }

    public City(String discordID) {
        this.setDiscordID(discordID);
    }

    public City retrieveCityData(CoreDatabase coreDatabase) throws InvalidValue, NonExistentLocation, SQLException {
        new ControllerCity(coreDatabase).getCity(this);
        return this;
    }

    public City retrieveSimplerCityData(CoreDatabase coreDatabase) throws InvalidValue, NonExistentLocation, SQLException {
        new ControllerCity(coreDatabase).getSimplerCity(this);
        return this;
    }

    public void createCityData(CoreDatabase coreDatabase) throws InvalidValue, SQLException {
        createCityTemplate();
        new ControllerCity(coreDatabase).createCity(this);
    }

    private void createCityTemplate() throws InvalidValue {
        this.setMoney(0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 5);
        this.setDefaultDueRent(calendar);

        this.setSize(new CitySize(1));
        this.setCreatedDate(Calendar.getInstance());
        this.setDiscordLink("tmp");
    }

    public EmbedBuilder buildCityEmbed(SimpleDateFormat dateFormat, SimpleDateFormat dueDateFormat) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("City of " + name);
        eb.addField("Mayor", "<@"+ mayor.getDiscordID() +">", true);
        eb.addField("Money", "G$"+ money, true);
        eb.addField("Due Rent Date", dueDateFormat.format(defaultDueRent.getTime()), true);
        eb.addField("Size", size.getName(), true);
        eb.addField("Creation Date", dateFormat.format(createdDate.getTime()), true);
        eb.addField("Discord Server Link", discordLink, true);

        return eb;
    }

    public String getDiscordID() {
        return discordID;
    }

    public void setDiscordID(String discordID) {
        this.discordID = discordID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Profile getMayor() {
        return mayor;
    }

    public void setMayor(Profile mayor) {
        this.mayor = mayor;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public Calendar getDefaultDueRent() {
        return defaultDueRent;
    }

    public void setDefaultDueRent(Calendar defaultDueRent) {
        this.defaultDueRent = defaultDueRent;
    }

    public CitySize getSize() {
        return size;
    }

    public void setSize(CitySize size) {
        this.size = size;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public String getDiscordLink() {
        return discordLink;
    }

    public void setDiscordLink(String discordLink) {
        this.discordLink = discordLink;
    }
}
