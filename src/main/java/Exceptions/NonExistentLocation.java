package Exceptions;

public class NonExistentLocation extends Exception {
    public NonExistentLocation() {
        super("The location doesn't exist!");
    }
}
