package ru.bandamc.advancedHNS.database;

import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void changeArenaStatus(String name, int status) throws SQLException {
        PreparedStatement sql = plugin.getConnection().prepareStatement("UPDATE `hns_arenas` SET `status`=? WHERE name=?");
        sql.setInt(1, status);
        sql.setString(2, name);
        sql.execute();
    }

    public void deleteArena(String name) throws SQLException {
        PreparedStatement sql = plugin.getConnection().prepareStatement("DELETE FROM `hns_arenas` WHERE name=?");
        sql.setString(1, name);
        sql.execute();
    }

    public ResultSet getArenaInfo(String name) throws SQLException {
        PreparedStatement sql = plugin.getConnection().prepareStatement("SELECT * FROM `hns_arenas` WHERE name=?");
        sql.setString(1, name);
        return sql.executeQuery();
    }

    public ResultSet getAllArenas() throws SQLException {
        PreparedStatement sql = plugin.getConnection().prepareStatement("SELECT * FROM `hns_arenas`");
        return sql.executeQuery();
    }
}
