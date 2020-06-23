package Exceptions;

/**
 * The {@code InvalidValue} exception prevents any unwanted values to the components creation, making sure that
 * all values are notNull and above 0 or 1.
 *
 * @author Guilherme Theodoro
 * @since 0.0.1
 * @version 0.0.1
 */

public class InvalidValue extends Exception {
    public InvalidValue(String classAndMethod) {
        super("The value that was inserted in " + classAndMethod + " was null or lower than 0 or 1.");
    }
}
