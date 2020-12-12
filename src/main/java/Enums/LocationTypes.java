package Enums;

public enum LocationTypes {
    CITY(1);

    private final int type;

    LocationTypes(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
