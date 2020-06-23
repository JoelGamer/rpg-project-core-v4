package Components.Profile;

import Components.Component;
import Components.Guild.Guild;
import Components.Location.Location;
import Controllers.ControllerProfile;
import Core.CoreDatabase;
import Exceptions.AccessDenied;
import Exceptions.InvalidValue;
import net.dv8tion.jda.core.EmbedBuilder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Profile extends Component {

    private String discordID;
    private int level;
    private long experience;
    private ProfileClass profileClass;
    private ProfileRank profileRank;
    private long power;
    private long money;
    private ProfileMarriage profileMarriage;
    private Guild guild;
    private Location location;
    private Calendar profileCreationDate;
    private Location profileCreationLocation;

    public Profile(long uid) throws InvalidValue {
        super(uid);
    }

    public Profile(String discordID) {
        this.discordID = discordID;
    }

    public Profile retrieveProfileData(CoreDatabase coreDatabase) throws AccessDenied, InvalidValue, SQLException {
        new ControllerProfile(coreDatabase).getProfile(this);
        return this;
    }

    public Profile retrieveSimplerProfileData(CoreDatabase coreDatabase) throws InvalidValue, SQLException {
        new ControllerProfile(coreDatabase).getSimplerProfile(this);
        return this;
    }

    public EmbedBuilder buildProfileEmbed(SimpleDateFormat simpleDateFormat) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.addField("Name","<@"+ getDiscordID() +">",true);
        eb.addField("Level/XP",getLevel() +"/"+ getExperience(),true);
        eb.addField("ProfileClass", getProfileClass().getName(), true);
        eb.addField("Rank", getProfileRank().getName() ,true);
        eb.addField("Power", getPower() + "pp", true);
        eb.addField("Money", "G$" + getMoney(), true);
        eb.addField("Marital Status", getMarriagePartner(), true);
        eb.addField("Guild", getGuild().getName(), true);
        eb.addField("Location", getLocation().getName(), true);
        eb.addField("Creation Date", simpleDateFormat.format(getProfileCreationDate().getTime()) , true);
        eb.addField("Location of Creation", getProfileCreationLocation().getName(), true);
        return eb;
    }

    public String getMarriagePartner() {
        if(profileMarriage.getFirstUserMarriage() == null) return "N/A";
        return (profileMarriage.getFirstUserMarriage().discordID.equals(getDiscordID())) ?
                "<@" + profileMarriage.getSecondUserMarriage().getDiscordID() + ">" :
                "<@" + profileMarriage.getFirstUserMarriage().getDiscordID() + ">";
    }

    public String getDiscordID() {
        return discordID;
    }

    public void setDiscordID(String discordID) throws InvalidValue {
        if(discordID == null) throw new InvalidValue(this.getClass().getName() + ".setDiscordID");
        this.discordID = discordID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public ProfileClass getProfileClass() {
        return profileClass;
    }

    public void setProfileClass(ProfileClass profileClass) {
        this.profileClass = profileClass;
    }

    public ProfileRank getProfileRank() {
        return profileRank;
    }

    public void setProfileRank(ProfileRank profileRank) {
        this.profileRank = profileRank;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public ProfileMarriage getProfileMarriage() {
        return profileMarriage;
    }

    public void setProfileMarriage(ProfileMarriage profileMarriage) {
        this.profileMarriage = profileMarriage;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Calendar getProfileCreationDate() {
        return profileCreationDate;
    }

    public void setProfileCreationDate(Calendar profileCreationDate) {
        this.profileCreationDate = profileCreationDate;
    }

    public Location getProfileCreationLocation() {
        return profileCreationLocation;
    }

    public void setProfileCreationLocation(Location profileCreationLocation) {
        this.profileCreationLocation = profileCreationLocation;
    }
}
