package Commands.City;

import Components.City.City;
import Components.Location.Location;
import Components.Profile.Profile;
import Core.Core;
import Enums.LocationTypes;
import Enums.StatusLevel;
import Exceptions.InvalidValue;
import Exceptions.NonExistentLocation;
import Exceptions.Unauthorized;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.sql.SQLException;

public class CreateCity extends Command {

    private final Core core;

    public CreateCity(Core core) {
        super.name = "create_city";
        super.help = "Creates a city in the server that the command was executed.";
        super.category = new Category("City");
        this.core = core;
    }

    @Override
    protected void execute(CommandEvent event) {
        core.coreConsole().consoleCommandLog(event.getAuthor(), this);
        Profile profile = new Profile(event.getAuthor().getId());
        City city = new City(event.getGuild().getId());

        try {
            profile.retrieveSimplerProfileData(core.coreDatabase());
            city.retrieveSimplerCityData(core.coreDatabase());
            event.reply("This server is already a city! You cannot create two city's for the same server.");
        } catch (NonExistentLocation e) {
            createCity(city, profile, event);
        } catch (Unauthorized e) {
            e.printStackTrace();
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.WARNING);
        } catch (InvalidValue | SQLException e) {
            e.printStackTrace();
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.ERROR);
        }
    }

    private void createCity(City city, Profile mayor, CommandEvent event) {
        try {
            city.setName(event.getGuild().getName());
            city.setMayor(mayor);
            city.createCityData(core.coreDatabase());
            new Location(city).createLocationData(core.coreDatabase(), city.getName(), LocationTypes.CITY);
            event.reply("Well done! Now this server is a city and part of the RPG universe!\n Try testing it out using **rs!city**.");
        } catch (InvalidValue | SQLException ex) {
            ex.printStackTrace();
            core.coreConsole().consoleLog(ex.getMessage(), StatusLevel.ERROR);
        }
    }
}
