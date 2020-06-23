package Core;

import Exceptions.InvalidStatus;

public class CoreConsole {

    private final Core core;

    public CoreConsole(Core core) {
        this.core = core;
    }

    public void consoleLog(String message, int status) {
        try {
            if(status > 5 || status < 1) throw new InvalidStatus(1, 5);

            String output = core.getConsoleTags().get(status - 1);
            message = " " + message;

            if(status == 1) output += message;
            else output = involveWithConsoleColor(output + message, status);

            System.out.println(output);
        } catch (InvalidStatus e) {
            consoleLogSystem(e.getMessage(), 3);
        }
    }

    public void consoleLogSystem(String message, int status) {
        try {
            if(status > 5 || status < 1) throw new InvalidStatus(1, 5);

            String output = core.getConsoleTags().get(5) + core.getConsoleTags().get(status - 1);
            message = " " + message;

            if(status == 1) output += message;
            else output = involveWithConsoleColor(output + message, status);

            System.out.println(output);
        } catch (InvalidStatus e) {
            consoleLogSystem(e.getMessage(), 3);
        }
    }

    private String involveWithConsoleColor(String message, int status) {
        return (status > 2) ? core.getConsoleTextColors().get(1) + message + core.getConsoleTextColors().get(2) :
                core.getConsoleTextColors().get(0) + message + core.getConsoleTextColors().get(2);
    }
}
