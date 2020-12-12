package Commands.Profile;

import Components.City.City;
import Components.Profile.Profile;
import Core.Core;
import Enums.StatusLevel;
import Exceptions.NonExistentLocation;
import Exceptions.Unauthorized;
import Exceptions.InvalidValue;
import Utils.RPGEmbedBuilder;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ShowProfile extends Command {

    private final Core core;

    public ShowProfile(Core core) {
        super.name = "profile";
        super.help = "Shows the information of your profile or the one that you mentioned.";
        super.arguments = "(mentioned_user)";
        super.category = new Category("Profile");
        this.core = core;
    }

    @Override
    protected void execute(CommandEvent event) {
        core.coreConsole().consoleCommandLog(event.getAuthor(), this);
        User profileToShow = (event.getMessage().getMentionedMembers().size() > 0) ?
                event.getMessage().getMentionedMembers().get(0).getUser() :
                event.getAuthor();

        if(event.getMessage().getMentionedMembers().size() > 1) event.reply("I can only show one profile at a time... I'll only search the first mentioned user.");

        try {
            new City(event.getGuild().getId()).retrieveSimplerCityData(core.coreDatabase());
            Profile profile = new Profile(profileToShow.getId()).retrieveProfileData(core.coreDatabase());

            MessageEmbed messageEmbed = buildShowProfileMessageEmbed(profile, event.getAuthor(), profileToShow);
            event.getChannel().sendMessage(messageEmbed).queue();
        } catch (NonExistentLocation | Unauthorized e) {
            event.reply(e.getMessage());
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.WARNING);
        } catch (InvalidValue | SQLException e) {
            e.printStackTrace();
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.ERROR);
        }
    }

    private MessageEmbed buildShowProfileMessageEmbed(Profile profile, User eventAuthor, User profileToShow) {
        EmbedBuilder eb = profile.buildProfileEmbed(new SimpleDateFormat(core.getDateFormats().get(1)), profileToShow);
        return new RPGEmbedBuilder(core).buildBaseEmbed(eb, eventAuthor).build();
    }
}
