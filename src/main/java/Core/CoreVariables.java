package Core;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * The {@code CoreVariables} class is responsible to retrieve all data from both core.properties file
 * and the config.properties file, thus creating a object that holds all of the fixed data for
 * others Core classes to use.
 *
 * @author Guilherme Theodoro
 * @since 0.0.1
 * @version 0.0.1
 */

public class CoreVariables {

    // Database Info
    private String databaseHost;
    private String databaseName;
    private String databaseUsername;
    private String databasePassword;

    // Console Log Data
    private final ArrayList<String> consoleTags = new ArrayList<>();
    private final ArrayList<String> consoleTextColors = new ArrayList<>();
    private final ArrayList<String> dateFormats = new ArrayList<>();

    // Bot Info
    private String token;
    private String owner;
    private String gamePlaying;
    private String prefix;
    private String helpWord;
    private String discordInviteLink;
    private Color discordEmbedColor;


    public CoreVariables(String corePropertiesFile, String configPropertiesFile) throws IOException {
        loadCoreProperties(corePropertiesFile);
        loadConfigurationProperties(configPropertiesFile);
    }

    public void loadCoreProperties(String propertiesFile) throws IOException {
        Properties properties = new Properties();
        InputStream input = new FileInputStream(propertiesFile);
        properties.load(input);

        setDatabaseHost(properties);
        setDatabaseName(properties);
        setDatabaseUsername(properties);
        setDatabasePassword(properties);
        setConsoleTags(properties);
        setConsoleTextColors(properties);
        setDateFormats(properties);
    }

    public void loadConfigurationProperties(String propertiesFile) throws IOException {
        Properties properties = new Properties();
        InputStream input = new FileInputStream(propertiesFile);
        properties.load(input);

        setToken(properties);
        setOwner(properties);
        setGamePlaying(properties);
        setPrefix(properties);
        setHelpWord(properties);
        setDiscordInviteLink(properties);
        setDiscordEmbedColor(properties);
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseUser() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public ArrayList<String> getConsoleTags() {
        return consoleTags;
    }

    public ArrayList<String> getConsoleTextColors() {
        return consoleTextColors;
    }

    public ArrayList<String> getDateFormats() {
        return dateFormats;
    }

    public String getToken() {
        return token;
    }

    public String getOwner() {
        return owner;
    }

    public String getGamePlaying() {
        return gamePlaying;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getHelpWord() {
        return helpWord;
    }

    public String getDiscordInviteLink() {
        return discordInviteLink;
    }

    public Color getDiscordEmbedColor() {
        return discordEmbedColor;
    }

    private void setDatabaseHost(Properties properties) {
        this.databaseHost = properties.getProperty("databaseHost");
    }

    private void setDatabaseName(Properties properties) {
        this.databaseName = properties.getProperty("databaseName");
    }

    private void setDatabaseUsername(Properties properties) {
        this.databaseUsername = properties.getProperty("databaseUser");
    }

    private void setDatabasePassword(Properties properties) {
        this.databasePassword = properties.getProperty("databasePassword");
    }

    private void setConsoleTags(Properties properties) {
        this.consoleTags.add(properties.getProperty("consoleInfo"));
        this.consoleTags.add(properties.getProperty("consoleWarning"));
        this.consoleTags.add(properties.getProperty("consoleError"));
        this.consoleTags.add(properties.getProperty("consoleCriticalError"));
        this.consoleTags.add(properties.getProperty("consoleFailure"));
        this.consoleTags.add(properties.getProperty("consoleSystem"));
    }

    private void setConsoleTextColors(Properties properties) {
        this.getConsoleTextColors().add(properties.getProperty("consoleWarningColor"));
        this.getConsoleTextColors().add(properties.getProperty("consoleErrorColor"));
        this.getConsoleTextColors().add(properties.getProperty("consoleResetColor"));
    }

    private void setDateFormats(Properties properties) {
        this.getDateFormats().add(properties.getProperty("formatDateHour"));
        this.getDateFormats().add(properties.getProperty("formatDate"));
        this.getDateFormats().add(properties.getProperty("formatSQLDate"));
        this.getDateFormats().add(properties.getProperty("formatPaymentDate"));
    }

    private void setToken(Properties properties) {
        this.token = properties.getProperty("token");
    }

    private void setOwner(Properties properties) {
        this.owner = properties.getProperty("owner");
    }

    private void setGamePlaying(Properties properties) {
        this.gamePlaying = properties.getProperty("gamePlaying");
    }

    private void setPrefix(Properties properties) {
        this.prefix = properties.getProperty("prefix");
    }

    private void setHelpWord(Properties properties) {
        this.helpWord = properties.getProperty("helpWord");
    }

    private void setDiscordInviteLink(Properties properties) {
        this.discordInviteLink = properties.getProperty("discordInviteLink");
    }

    private void setDiscordEmbedColor(Properties properties) {
        int red = Integer.parseInt(properties.getProperty("valueRed"));
        int green = Integer.parseInt(properties.getProperty("valueGreen"));
        int blue = Integer.parseInt(properties.getProperty("valueBlue"));

        this.discordEmbedColor = new Color(red, green, blue);
    }
}
