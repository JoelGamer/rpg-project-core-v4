package Commands.Profile.Marriage;

import Core.Core;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Marry extends Command {

    private Core core;

    public Marry(Core core) {
        super.name = "marriage_marry";
        this.core = core;
    }

    @Override
    protected void execute(CommandEvent event) {

    }
}
