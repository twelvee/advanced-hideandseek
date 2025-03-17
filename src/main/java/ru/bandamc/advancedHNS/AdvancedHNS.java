package ru.bandamc.advancedHNS;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.command.CommandExecutor;
import ru.bandamc.advancedHNS.database.ArenaRepository;
import ru.bandamc.advancedHNS.entities.Arena;
import ru.bandamc.advancedHNS.events.OnPlayerJoinServerEvent;
import ru.bandamc.advancedHNS.events.OnPlayerLeftServerEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public final class AdvancedHNS extends JavaPlugin {

    static public String HNS_PREFIX = "[Advanced HideAndSeek]";
    static public String HNS_CHAT_PREFIX = "§6[Advanced HideAndSeek]§f";

    private ArenaRepository arenaRepository;

    public HashMap<Player, Arena> arenaEdits = new HashMap<>();

    private Connection connection;

    @Override
    public void onEnable() {
        saveResource("config.yml", false);
        saveResource("localization.yml", false);
        LocalizationManager.getInstance();
        saveDefaultConfig();
        arenaRepository = new ArenaRepository(this);
        initDatabase();
        registerEvents();
        getCommand("hns").setExecutor(new CommandExecutor());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnPlayerJoinServerEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeftServerEvent(), this);
    }

    public ArenaRepository getArenaRepository() {
        return arenaRepository;
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

    public Connection getConnection() {
        return connection;
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
                connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName, dbUser, dbPassword);
            } else {
                getLogger().severe(HNS_PREFIX + " Unsupported database type: " + dbType + ", plugins currently support only mysql.");
            }
        } catch (SQLException e) {
            getLogger().severe(HNS_PREFIX + " Failed to connect to database: " + e.getMessage());
        }
    }
}
