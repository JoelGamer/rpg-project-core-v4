package Core;

import Enums.StatusLevel;
import com.jagrosh.jdautilities.command.Command;
import net.dv8tion.jda.core.entities.User;

public class CoreConsole {

    private final Core core;

    public CoreConsole(Core core) {
        this.core = core;
    }

    public void consoleLog(String message, StatusLevel status) {
        String output = status.toString();
        message = " " + message;

        if(status.equals(StatusLevel.INFO)) output += message;
        else output = involveWithConsoleColor(output + message, status);

        System.out.println(output);
    }

    public void consoleLogSystem(String message, StatusLevel status) {
        String output = StatusLevel.SYSTEM.toString() + status.toString();
        message = " " + message;

        if(status.equals(StatusLevel.INFO)) output += message;
        else output = involveWithConsoleColor(output + message, status);

        System.out.println(output);
    }

    public void consoleCommandLog(User user, Command command) {
        String output = StatusLevel.INFO.toString();
        output += " DiscordID:" + user.getId() + " used the command " + command.getClass().getName();

        System.out.println(output);
    }

    private String involveWithConsoleColor(String message, StatusLevel status) {
        return (!status.equals(StatusLevel.WARNING)) ? core.getConsoleTextColors().get(1) + message + core.getConsoleTextColors().get(2) :
                core.getConsoleTextColors().get(0) + message + core.getConsoleTextColors().get(2);
    }
}
