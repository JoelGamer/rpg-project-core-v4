package Exceptions;

/**
 * The {@code AccessDenied} exception prevents unregistered users to use the bot's commands.
 *
 * @author Guilherme Theodoro
 * @since 0.0.1
 * @version 0.0.1
 */

public class AccessDenied extends Exception {
    public AccessDenied(String discordID) {
        super("The user <@" + discordID + "> doesn't have an account.");
    }
}
