package Commands.City;

import Components.City.City;
import Components.Profile.Profile;
import Core.Core;
import Enums.StatusLevel;
import Exceptions.InvalidValue;
import Exceptions.NonExistentLocation;
import Exceptions.Unauthorized;
import Utils.RPGEmbedBuilder;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ShowCity extends Command {

    private final Core core;

    public ShowCity(Core core) {
        super.name = "city";
        super.help = "Shows the information of the city your in.";
        super.category = new Category("City");
        this.core = core;
    }

    @Override
    protected void execute(CommandEvent event) {
        core.coreConsole().consoleCommandLog(event.getAuthor(), this);

        try {
            new Profile(event.getAuthor().getId()).retrieveSimplerProfileData(core.coreDatabase());
            City city = new City(event.getGuild().getId()).retrieveCityData(core.coreDatabase());

            MessageEmbed messageEmbed = buildShowCityMessageEmbed(city, event.getAuthor(), event.getGuild());
            event.getChannel().sendMessage(messageEmbed).queue();
        } catch (Unauthorized | NonExistentLocation e) {
            event.reply(e.getMessage());
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.WARNING);
        } catch (InvalidValue | SQLException e) {
            e.printStackTrace();
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.ERROR);
        }
    }

    private MessageEmbed buildShowCityMessageEmbed(City city, User eventAuthor, Guild guild) {
        EmbedBuilder eb = city.buildCityEmbed(new SimpleDateFormat(core.getDateFormats().get(1)), new SimpleDateFormat(core.getDateFormats().get(3)));
        eb.setThumbnail(guild.getIconUrl());
        return new RPGEmbedBuilder(core).buildBaseEmbed(eb, eventAuthor).build();
    }
}
