package ru.bandamc.advancedHNS;

import fr.mrmicky.fastboard.adventure.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.command.CommandExecutor;
import ru.bandamc.advancedHNS.database.ArenaRepository;
import ru.bandamc.advancedHNS.entities.Arena;
import ru.bandamc.advancedHNS.events.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public final class AdvancedHNS extends JavaPlugin {

    static public String HNS_PREFIX = "[Advanced HideAndSeek]";
    static public String HNS_CHAT_PREFIX = "§6[Advanced HideAndSeek]§f";

    private ArenaRepository arenaRepository;


    public HashMap<String, Arena> arenas = new HashMap<>();
    public HashMap<Arena, ArrayList<Player>> arenaPlayers = new HashMap<>();
    public HashMap<Player, Arena> playerArena = new HashMap<>();
    public HashMap<Player, Arena> arenaEdits = new HashMap<>();

    public final Map<Player, FastBoard> boards = new HashMap<>();

    private Connection connection;

    @Override
    public void onEnable() {
        saveResource("config.yml", false);
        saveResource("localization.yml", false);
        LocalizationManager.getInstance();
        saveDefaultConfig();
        arenaRepository = new ArenaRepository(this);
        initDatabase();

        listAllArenas();

        registerEvents();
        getCommand("hns").setExecutor(new CommandExecutor());
    }

    public void listAllArenas() {
        ResultSet allArenas;
        try {
            allArenas = this.arenaRepository.getAllArenas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                if (!allArenas.next()) break;
                Arena arena = new Arena();
                arena.setName(allArenas.getString("name"));
                arena.setWorld(allArenas.getString("world"));
                arena.setMaxHiders(allArenas.getInt("max_hiders"));
                arena.setMaxSeekers(allArenas.getInt("max_seekers"));
                arena.setPos1(allArenas.getString("pos1"));
                arena.setPos2(allArenas.getString("pos2"));
                arena.setSpecPos(allArenas.getString("spec_pos"));
                arena.setLobbyPos(allArenas.getString("lobby_pos"));
                arena.setSeekersSpawnPos(allArenas.getString("seekers_spawn_point"));
                arena.setHidersSpawnPos(allArenas.getString("hiders_spawn_point"));
                arena.setMinHiders(allArenas.getInt("min_hiders"));
                arena.setMinSeekers(allArenas.getInt("min_seekers"));
                arena.setMaxPlayers(allArenas.getInt("max_players"));
                arena.setStatus(allArenas.getInt("status"));
                arenas.put(allArenas.getString("name"), arena);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnPlayerJoinServerEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeftServerEvent(), this);

        // Advanced HNS API events
        getServer().getPluginManager().registerEvents(new OnArenaInitEvent(), this);
        getServer().getPluginManager().registerEvents(new OnArenaDeleteEvent(), this);
        getServer().getPluginManager().registerEvents(new OnArenaEditEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnArenaSaveEvent(this), this);

        getServer().getPluginManager().registerEvents(new OnArenaJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new OnArenaLeaveEvent(), this);
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
