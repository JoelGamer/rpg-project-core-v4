package Utils;

import Core.Core;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RPGEmbedBuilder {

    private final Core core;

    public RPGEmbedBuilder(Core core) {
        this.core = core;
    }

    public EmbedBuilder buildBaseEmbed(EmbedBuilder eb, User eventAuthor) {
        eb.setColor(core.getDiscordEmbedColor());
        eb.setFooter("Sent Date: " + new SimpleDateFormat(core.getDateFormats().get(0)).format(Calendar.getInstance().getTime()) +
                " | Requested by: " + eventAuthor.getAsTag(), eventAuthor.getAvatarUrl());

        return eb;
    }
}
