import Commands.Profile.ShowProfile;
import Core.Core;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws LoginException {
        buildDiscordBot(createCore());
    }

    private static void buildDiscordBot(Core core) throws LoginException {
        core.coreConsole().consoleLogSystem("Preparing to build bot...", 1);

        JDA jda = new JDABuilder(AccountType.BOT).setToken(core.getToken()).build();

        CommandClientBuilder builder = new CommandClientBuilder();
        EventWaiter waiter = new EventWaiter();

        builder.setOwnerId(core.getOwner());
        builder.setGame(Game.playing(core.getGamePlaying()));
        builder.setPrefix(core.getPrefix());
        builder.setHelpWord(core.getHelpWord());
        builder.setServerInvite(core.getDiscordInviteLink());

        core.coreConsole().consoleLogSystem("Instantiating commands...", 1);

        //Profile Commands
        builder.addCommands(new ShowProfile(core));

        CommandClient client = builder.build();
        jda.addEventListener(client);
        jda.addEventListener(waiter);

        core.coreConsole().consoleLogSystem("Bot build successfully!", 1);
    }

    private static Core createCore() {
        String corePropertiesFile = "src/main/resources/core.properties";
        String configPropertiesFile = "src/main/resources/config.properties";

        try {
            return new Core(corePropertiesFile, configPropertiesFile);
        } catch (IOException e) {
            System.err.println("Verify if both archives exists to initiate the bot! (core.properties and config.properties)");
        } catch (SQLException e) {
            System.err.println("Unable to connect to the server! Verify if the database is online!");
        }

        System.exit(1);
        return null;
    }
}
