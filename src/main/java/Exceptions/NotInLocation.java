package Exceptions;

import Components.Profile.Profile;

public class NotInLocation extends Exception {
    public NotInLocation(Profile profile) {
        super("The user <@"+ profile.getDiscordID() +"> is not in the location");
    }
}
