package ru.bandamc.advancedHNS;

import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.command.CommandExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class AdvancedHNS extends JavaPlugin {

    static public String HNS_PREFIX = "[Advanced HideAndSeek]";
    static public String HNS_CHAT_PREFIX = "§6[Advanced HideAndSeek]§f";

    private Connection connection;

    @Override
    public void onEnable() {
        saveResource("config.yml", false);
        saveResource("localization.yml", false);
        LocalizationManager.getInstance();
        saveDefaultConfig();
        initDatabase();
        getCommand("hns").setExecutor(new CommandExecutor());
    }

    @Override
    public void onDisable() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                getLogger().severe(HNS_PREFIX + " Failed to close database connection. Error: " + e.getMessage());
            }
        }
    }

    private void initDatabase() {
        String dbType = getConfig().getString("database.type", "mysql");
        String dbHost = getConfig().getString("database.host", "127.0.0.1");
        String dbPort = getConfig().getString("database.port", "3306");
        String dbName = getConfig().getString("database.database", "hideandseek");
        String dbUser = getConfig().getString("database.user", "user");
        String dbPassword = getConfig().getString("database.password", "''");

        try {
            if (dbType.equalsIgnoreCase("mysql")) {
                connection = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName, dbUser, dbPassword);
            } else {
                getLogger().severe(HNS_PREFIX + " Unsupported database type: " + dbType + ", plugins currently support only mysql.");
            }
        } catch (SQLException e) {
            getLogger().severe(HNS_PREFIX + " Failed to connect to database: " + e.getMessage());
        }
    }
}
