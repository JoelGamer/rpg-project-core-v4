package Commands.Profile;

import Components.City.City;
import Components.Profile.Profile;
import Components.Profile.ProfileClass;
import Controllers.ControllerProfileClass;
import Core.Core;
import Enums.StatusLevel;
import Exceptions.InvalidValue;
import Exceptions.NonExistentLocation;
import Exceptions.Unauthorized;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CreateProfile extends Command {

    private final Core core;
    private final EventWaiter waiter;

    public CreateProfile(Core core, EventWaiter waiter) {
        super.name = "create_profile";
        super.help = "Creates you're very own rpg discord profile.";
        super.category = new Category("Profile");
        this.core = core;
        this.waiter = waiter;
    }

    @Override
    protected void execute(CommandEvent event) {
        core.coreConsole().consoleCommandLog(event.getAuthor(), this);
        Profile profile = new Profile(event.getAuthor().getId());

        try {
            new City(event.getGuild().getId()).retrieveSimplerCityData(core.coreDatabase());
            profile.retrieveSimplerProfileData(core.coreDatabase());
            event.reply("You already have an account! You can't create two accounts for the same discord user!");
        } catch (Unauthorized e) {
            List<ProfileClass> profileClasses = new ControllerProfileClass(core.coreDatabase()).getProfileClasses();
            event.reply(showAllProfileClasses(profileClasses));
            event.reply("Use a number to select your class!");

            waiter.waitForEvent(GuildMessageReceivedEvent.class, ev -> ev.getAuthor().equals(event.getAuthor()) && ev.getChannel().equals(event.getChannel()), ev -> {
                try {
                    int selectedClass = Integer.parseInt(ev.getMessage().getContentRaw());

                    if(selectedClass > profileClasses.size()) {
                        event.reply("The number must be between 1 through " + profileClasses.size());
                        return;
                    }

                    profile.setProfileClass(new ProfileClass(selectedClass));

                    createProfile(profile, event);
                    event.reply("At last! You have created successfully you're RPG account!");
                } catch (NumberFormatException | InvalidValue nfe) {
                    core.coreConsole().consoleLog("Expected number but got something else (CreateProfile.java)", StatusLevel.WARNING);
                    event.reply("I was expecting a number for you to select the class...");
                }
            }, 30, TimeUnit.SECONDS, () -> event.reply("You didn't choose your class..."));
        } catch (NonExistentLocation e) {
            event.reply(e.getMessage());
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.WARNING);
        } catch (InvalidValue | SQLException e) {
            e.printStackTrace();
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.ERROR);
        }
    }

    private void createProfile(Profile profile, CommandEvent event) {
        try {
            City city = new City(event.getGuild().getId()).retrieveCityData(core.coreDatabase());
            profile.setLocation(city.getLocation());
            profile.setProfileCreationLocation(city.getLocation());

            profile.createProfileData(core.coreDatabase());
        } catch (NonExistentLocation e) {
            event.reply(e.getMessage());
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.WARNING);
        } catch (InvalidValue | SQLException e) {
            e.printStackTrace();
            core.coreConsole().consoleLog(e.getMessage(), StatusLevel.ERROR);
        }
    }

    private String showAllProfileClasses(List<ProfileClass> profileClasses) {
        StringBuilder stringBuilder = new StringBuilder("```Available classes to select:\n");
        for (ProfileClass profileClass : profileClasses) {
            stringBuilder.append(profileClass.getName()).append("\n");
        }
        return stringBuilder.append("```").toString();
    }
}
