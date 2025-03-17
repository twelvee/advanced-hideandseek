package ru.bandamc.advancedHNS.database;

import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArenaRepository {
    private AdvancedHNS plugin;

    public ArenaRepository(JavaPlugin plugin) {
        this.plugin = (AdvancedHNS) plugin;
    }

    public void createArena(String world, String name) throws SQLException {
        PreparedStatement sql = plugin.getConnection().prepareStatement("INSERT INTO `hns_arenas` (`name`,`world`,`max_seekers`,`max_hiders`,`status`) VALUES (?,?,?,?,?)");
        sql.setString(1, name);
        sql.setString(2, world);
        sql.setInt(3, 1);
        sql.setInt(4, 1);
        sql.setInt(5, 0); // 0 - editing, 1 - available, 2 - waiting for players, 3 - playing, 4 - cleaning everything up
        sql.execute();
    }
}
