package Commands.Profile;

import Components.Profile.Profile;
import Core.Core;
import Exceptions.AccessDenied;
import Exceptions.InvalidValue;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        core.coreConsole().consoleLog("DiscordID:" + event.getAuthor().getId() + " used the command ShowProfile",1);
        User profileToShow = (event.getMessage().getMentionedMembers().size() > 0) ?
                event.getMessage().getMentionedMembers().get(0).getUser() :
                event.getAuthor();

        if(event.getMessage().getMentionedMembers().size() > 1) event.reply("I can only show one profile at a time... I'll only search the first mentioned user.");

        try {
            Profile profile = new Profile(profileToShow.getId()).retrieveProfileData(core.coreDatabase());

            MessageEmbed messageEmbed = buildShowProfileMessageEmbed(profile, event.getAuthor(), profileToShow);
            event.getChannel().sendMessage(messageEmbed).queue();
        } catch (AccessDenied e) {
            core.coreConsole().consoleLog(e.getMessage(), 2);
            event.reply(e.getMessage());
        } catch (InvalidValue | SQLException e) {
            e.printStackTrace();
            core.coreConsole().consoleLog(e.getMessage(), 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MessageEmbed buildShowProfileMessageEmbed(Profile profile, User eventAuthor, User profileToShow) {
        EmbedBuilder eb = profile.buildProfileEmbed(new SimpleDateFormat(core.getDateFormats().get(1)));
        eb.setTitle(profileToShow.getAsTag() + " Profile's");
        eb.setColor(core.getDiscordEmbedColor());
        eb.setThumbnail(profileToShow.getAvatarUrl());
        eb.setFooter("Sent Date: " + new SimpleDateFormat(core.getDateFormats().get(0)).format(Calendar.getInstance().getTime()) +
                " | Requested by: " + eventAuthor.getAsTag(), eventAuthor.getAvatarUrl());
        return eb.build();
    }
}
