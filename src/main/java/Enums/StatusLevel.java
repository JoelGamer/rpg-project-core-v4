package Enums;

public enum StatusLevel {
    INFO,
    WARNING,
    ERROR,
    CRITICAL,
    FATAL,
    SYSTEM;

    @Override
    public String toString() {
        return "[" + super.name() + "]";
    }
}
