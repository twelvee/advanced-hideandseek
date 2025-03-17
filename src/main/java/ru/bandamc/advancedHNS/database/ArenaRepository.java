package ru.bandamc.advancedHNS.database;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.entities.Arena;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArenaRepository {
    private AdvancedHNS plugin;

    public ArenaRepository(JavaPlugin plugin) {
        this.plugin = (AdvancedHNS) plugin;
    }

    public void createArena(Arena arena) throws SQLException {
        PreparedStatement sql = plugin.getConnection().prepareStatement("INSERT INTO `hns_arenas` (`name`,`world`,`max_seekers`,`max_hiders`,`status`,`pos1`,`pos2`,`spec_pos`,`lobby_pos`,`seekers_spawn_point`,`hiders_spawn_point`) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        sql.setString(1, arena.getName());
        sql.setString(2, arena.getWorld());
        sql.setInt(3, arena.getMaxSeekers());
        sql.setInt(4, arena.getMaxHiders());
        sql.setInt(5, 1); // 0 - editing, 1 - available, 2 - waiting for players, 3 - playing, 4 - cleaning everything up
        sql.setString(6, arena.getPos1().getWorld().getName()+"|"+arena.getPos1().getX()+"|"+arena.getPos1().getY()+"|"+arena.getPos1().getZ());
        sql.setString(7, arena.getPos2().getWorld().getName()+"|"+arena.getPos2().getX()+"|"+arena.getPos2().getY()+"|"+arena.getPos2().getZ());
        sql.setString(8, arena.getSpecPoint().getWorld().getName()+"|"+arena.getSpecPoint().getX()+"|"+arena.getSpecPoint().getY()+"|"+arena.getSpecPoint().getZ());
        sql.setString(9, arena.getLobbyPos().getWorld().getName()+"|"+arena.getLobbyPos().getX()+"|"+arena.getLobbyPos().getY()+"|"+arena.getLobbyPos().getZ());
        sql.setString(10, arena.getSeekersSpawnPos().getWorld().getName()+"|"+arena.getSeekersSpawnPos().getX()+"|"+arena.getSeekersSpawnPos().getY()+"|"+arena.getSeekersSpawnPos().getZ());
        sql.setString(11, arena.getHidersSpawnPos().getWorld().getName()+"|"+arena.getHidersSpawnPos().getX()+"|"+arena.getHidersSpawnPos().getY()+"|"+arena.getHidersSpawnPos().getZ());
        sql.execute();
    }

    public void changeArenaStatus(String name, int status) throws SQLException {
        PreparedStatement sql = plugin.getConnection().prepareStatement("UPDATE `hns_arenas` SET `status`=? WHERE `name`=?");
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
        PreparedStatement sql = plugin.getConnection().prepareStatement("SELECT * FROM `hns_arenas` WHERE `name`=?");
        sql.setString(1, name);
        Bukkit.getLogger().info(sql.toString());
        return sql.executeQuery();
    }

    public ResultSet getAllArenas() throws SQLException {
        PreparedStatement sql = plugin.getConnection().prepareStatement("SELECT * FROM `hns_arenas`");
        return sql.executeQuery();
    }

    public void updateArena(Arena arena) throws SQLException {
        PreparedStatement sql = plugin.getConnection().prepareStatement("UPDATE `hns_arenas` SET `world`=?, `max_seekers`=?, `max_hiders`=?, `status`=?, `pos1`=?, `pos2`=?, `spec_pos`=?, `lobby_pos`=?, `seekers_spawn_point`=?, `hiders_spawn_point`=? WHERE `name`=?");
        sql.setString(1, arena.getWorld());
        sql.setInt(2, arena.getMaxSeekers());
        sql.setInt(3, arena.getMaxHiders());
        sql.setInt(4, 1); // 0 - editing, 1 - available, 2 - waiting for players, 3 - playing, 4 - cleaning everything up
        sql.setString(5, arena.getPos1().getWorld().getName()+"|"+arena.getPos1().getX()+"|"+arena.getPos1().getY()+"|"+arena.getPos1().getZ());
        sql.setString(6, arena.getPos2().getWorld().getName()+"|"+arena.getPos2().getX()+"|"+arena.getPos2().getY()+"|"+arena.getPos2().getZ());
        sql.setString(7, arena.getSpecPoint().getWorld().getName()+"|"+arena.getSpecPoint().getX()+"|"+arena.getSpecPoint().getY()+"|"+arena.getSpecPoint().getZ());
        sql.setString(8, arena.getLobbyPos().getWorld().getName()+"|"+arena.getLobbyPos().getX()+"|"+arena.getLobbyPos().getY()+"|"+arena.getLobbyPos().getZ());
        sql.setString(9, arena.getSeekersSpawnPos().getWorld().getName()+"|"+arena.getSeekersSpawnPos().getX()+"|"+arena.getSeekersSpawnPos().getY()+"|"+arena.getSeekersSpawnPos().getZ());
        sql.setString(10, arena.getHidersSpawnPos().getWorld().getName()+"|"+arena.getHidersSpawnPos().getX()+"|"+arena.getHidersSpawnPos().getY()+"|"+arena.getHidersSpawnPos().getZ());
        sql.setString(11, arena.getName());
        sql.execute();
    }
}
