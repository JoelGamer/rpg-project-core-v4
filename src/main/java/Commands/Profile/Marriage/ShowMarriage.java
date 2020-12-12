package Commands.Profile.Marriage;

import Components.City.City;
import Components.Profile.Profile;
import Components.Profile.ProfileMarriage;
import Core.Core;
import Enums.StatusLevel;
import Exceptions.InvalidValue;
import Exceptions.NonExistentLocation;
import Exceptions.Unauthorized;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.sql.SQLException;

public class ShowMarriage extends Command {

    private Core core;

    public ShowMarriage(Core core) {
        this.core = core;
    }

    @Override
    protected void execute(CommandEvent event) {
        try {
            new City(event.getGuild().getId()).retrieveSimplerCityData(core.coreDatabase());
            Profile profile = new Profile(event.getAuthor().getId()).retrieveProfileData(core.coreDatabase());


        } catch (NonExistentLocation | Unauthorized e) {
            event.reply(e.getMessage());
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.WARNING);
        } catch (InvalidValue | SQLException e) {
            e.printStackTrace();
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.ERROR);
        }
    }

//    private MessageEmbed buildMarriageMessageEmbed(ProfileMarriage profileMarriage) {
//
//    }
}
