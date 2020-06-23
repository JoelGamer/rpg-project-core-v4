package Exceptions;

/**
 * The {@code InvalidStatus} exception prevents the inappropriate usage of the consoleLog from the {@code CoreConsole}
 * making sure that a status code informed on its usage is correct.
 *
 * @author Guilherme Theodoro
 * @see Core.CoreConsole
 * @since 0.0.1
 * @version 0.0.1
 */

public class InvalidStatus extends Exception {
    public InvalidStatus(int minimum, int maximum) {
        super("The code that was informed is invalid! Enter a code in the range of " + minimum + " to " + maximum);
    }
}
