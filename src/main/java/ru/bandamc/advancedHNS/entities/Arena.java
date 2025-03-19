package ru.bandamc.advancedHNS.entities;

import com.destroystokyo.paper.ClientOption;
import net.kyori.adventure.bossbar.BossBarImplementation;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Arena {
    String name;
    String world;
    Location pos1;
    Location pos2;
    Location specPoint;
    Location lobbyPos;
    Location seekersSpawnPos;
    Location hidersSpawnPos;

    HashMap<Player, Actor> players = new HashMap<>();

    int minSeekers;
    int minHiders;
    int maxPlayers;

    int maxSeekers;
    int maxHiders;
    int status;

    ArrayList<String> errorMessages = new ArrayList<>();
    private int prepareTime = 60;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getWorld() {
        return this.world;
    }

    public void setPos1(String pos) {
        var result = pos.split("\\|");
        this.pos1 = new Location(Bukkit.getWorld(result[0]),
                Float.parseFloat(result[1]),
                Float.parseFloat(result[2]),
                Float.parseFloat(result[3]));
        ;
    }

    public Location getPos1() {
        return this.pos1;
    }

    public void setPos2(String pos) {
        var result = pos.split("\\|");
        this.pos2 = new Location(Bukkit.getWorld(result[0]),
                Float.parseFloat(result[1]),
                Float.parseFloat(result[2]),
                Float.parseFloat(result[3]));
        ;
    }

    public Location getPos2() {
        return this.pos2;
    }

    public void setSpecPos(String pos) {
        var result = pos.split("\\|");
        this.specPoint = new Location(Bukkit.getWorld(result[0]),
                Float.parseFloat(result[1]),
                Float.parseFloat(result[2]),
                Float.parseFloat(result[3]));
        ;
    }

    public Location getSpecPoint() {
        return this.specPoint;
    }

    public void setLobbyPos(String pos) {
        var result = pos.split("\\|");
        this.lobbyPos = new Location(Bukkit.getWorld(result[0]),
                Float.parseFloat(result[1]),
                Float.parseFloat(result[2]),
                Float.parseFloat(result[3]));
        ;
    }

    public Location getLobbyPos() {
        return this.lobbyPos;
    }

    public void setSeekersSpawnPos(String pos) {
        var result = pos.split("\\|");
        this.seekersSpawnPos = new Location(Bukkit.getWorld(result[0]),
                Float.parseFloat(result[1]),
                Float.parseFloat(result[2]),
                Float.parseFloat(result[3]));
    }

    public Location getSeekersSpawnPos() {
        return this.seekersSpawnPos;
    }

    public void setHidersSpawnPos(String pos) {
        var result = pos.split("\\|");
        this.hidersSpawnPos = new Location(Bukkit.getWorld(result[0]),
                Float.parseFloat(result[1]),
                Float.parseFloat(result[2]),
                Float.parseFloat(result[3]));
        ;
    }

    public Location getHidersSpawnPos() {
        return this.hidersSpawnPos;
    }

    public void setMaxSeekers(int num) {
        this.maxSeekers = num;
    }

    public int getMaxSeekers() {
        return this.maxSeekers;
    }

    public void setMaxHiders(int num) {
        this.maxHiders = num;
    }

    public int getMaxHiders() {
        return this.maxHiders;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setMinSeekers(int num) {
        this.minSeekers = num;
    }

    public int getMinSeekers() {
        return this.minSeekers;
    }

    public void setMinHiders(int num) {
        this.minHiders = num;
    }

    public int getMinHiders() {
        return this.minHiders;
    }

    public void setMaxPlayers(int num) {
        this.maxPlayers = num;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    // true if everything aight
    // false if not
    public boolean validateToStore() {
        this.errorMessages.clear();

        if (this.pos1.getX() == 0 && this.pos1.getY() == 0 && this.pos1.getZ() == 0) {
            this.errorMessages.add("Pos1 must be set.");
        }
        if (this.pos2.getX() == 0 && this.pos2.getY() == 0 && this.pos2.getZ() == 0) {
            this.errorMessages.add("Pos1 must be set.");
        }
        if (this.specPoint.getX() == 0 && this.specPoint.getY() == 0 && this.specPoint.getZ() == 0) {
            this.errorMessages.add("Spectator position must be set.");
        }
        if (this.lobbyPos.getX() == 0 && this.lobbyPos.getY() == 0 && this.lobbyPos.getZ() == 0) {
            this.errorMessages.add("Lobby position must be set.");
        }
        if (this.hidersSpawnPos.getX() == 0 && this.hidersSpawnPos.getY() == 0 && this.hidersSpawnPos.getZ() == 0) {
            this.errorMessages.add("Hiders spawn must be set.");
        }
        if (this.seekersSpawnPos.getX() == 0 && this.seekersSpawnPos.getY() == 0 && this.seekersSpawnPos.getZ() == 0) {
            this.errorMessages.add("Seekers spawn must be set.");
        }
        if (this.maxHiders <= 0) {
            this.errorMessages.add("Max hiders must be 1 and more.");
        }
        if (this.maxSeekers <= 0) {
            this.errorMessages.add("Max seekers must be 1 and more.");
        }
        if (this.minSeekers <= 0) {
            this.errorMessages.add("Min seekers must be 1 and more.");
        }
        if (this.minHiders <= 0) {
            this.errorMessages.add("Min hiders must be 1 and more.");
        }
        if (this.maxPlayers < 2) {
            this.errorMessages.add("Max players must be 2 and more.");
        }

        return this.errorMessages.isEmpty();
    }

    public ArrayList<String> getValidationErrors() {
        return this.errorMessages;
    }

    public boolean isReadyToJoin() {
        // todo: validate seekers and hiders min amounts
        boolean hasFreeSpace = true;
        int nHiders = 0;
        int nPlayers = 0;
        for (Actor actor : this.players.values()) {
            nPlayers++;
            if (actor instanceof Hider) {
                nHiders++;
            } else {
                //seeker
            }
        }
        Bukkit.getLogger().info("Joining arena with maxplayers of " + maxPlayers + " and total amount of players is: " + nPlayers);
        if (nPlayers + 1 >= maxPlayers) hasFreeSpace = false;
        if (nHiders + 1 >= maxHiders) hasFreeSpace = false;

        return (this.status == 1 || this.status == 2) && hasFreeSpace;
    }

    public void joinHiders(Player player) {
        this.players.put(player, new Hider(player));
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        String language = player.getClientOption(ClientOption.LOCALE);
        plugin.boards.get(player).updateLines(
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.youre_in_arena")),
                Component.text(name),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status").replace("{status}", LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status_" + status))),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.hider")),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.footer"))
        );
    }

    public void joinSeekers(Player player) {
        this.players.put(player, new Seeker(player));
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        String language = player.getClientOption(ClientOption.LOCALE);
        plugin.boards.get(player).updateLines(
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.youre_in_arena")),
                Component.text(name),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status").replace("{status}", LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status_" + status))),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.seeker")),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.footer"))
        );
    }

    public HashMap<Player, Actor> getPlayers() {
        return this.players;
    }

    public String getStatusText() {
        switch (this.status) {
            case 0:
                return "Editing";
            case 1:
                return "Ready";
            case 2:
                return "Waiting for players..";
            case 3:
                return "In-progress";
            case 4:
                return "Cleaning";
        }
        return "Unavailable";
    }

    public ArrayList<Hider> getHiders() {
        ArrayList<Hider> hiders = new ArrayList<>();
        for (var actor : this.players.values()) {
            if (actor instanceof Hider)
                hiders.add((Hider) actor);
        }
        return hiders;
    }

    public ArrayList<Hider> getSeekers() {
        ArrayList<Hider> hiders = new ArrayList<>();
        for (var actor : this.players.values()) {
            if (actor instanceof Hider)
                hiders.add((Hider) actor);
        }
        return hiders;
    }

    public void start() {
        this.status = 3;
        for (var player : this.players.values()) {
            String language = player.getPlayer().getClientOption(ClientOption.LOCALE);

            if (player instanceof Seeker) {
                player.getPlayer().teleport(this.getSeekersSpawnPos());
                // blind seeker
                // todo: timer to reset effect
                player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 61*20, 0, false,
                        false));
                player.getPlayer().setWalkSpeed(0);
                player.getPlayer().setFlySpeed(0);
                player.getPlayer().setMetadata("seekerTimedOut", new FixedMetadataValue(JavaPlugin.getPlugin(AdvancedHNS.class), true));
                BossBar seekersStartBossBar = Bukkit.createBossBar(
                        LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".bossbars.seekers_start").replace("{time}", "60"),
                        BarColor.RED,
                        BarStyle.SOLID
                );
                seekersStartBossBar.addPlayer(player.getPlayer());
                player.getPlayer().setMetadata("activeBossBar", new FixedMetadataValue(JavaPlugin.getPlugin(AdvancedHNS.class), seekersStartBossBar));

                JavaPlugin.getPlugin(AdvancedHNS.class).boards.get(player.getPlayer()).updateLines(
                        Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.youre_in_arena")),
                        Component.text(name),
                        Component.text(""),
                        Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status").replace("{status}", LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status_" + status))),
                        Component.text(""),
                        Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.seeker")),
                        Component.text(""),
                        Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.footer"))
                );
            }

            if (player instanceof Hider) {
                player.getPlayer().teleport(this.getHidersSpawnPos());
                player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*20, 0, false, true));
                BossBar hidersStartBossBar = Bukkit.createBossBar(
                        LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".bossbars.hiders_start").replace("{time}", "60"),
                        BarColor.GREEN,
                        BarStyle.SOLID
                );
                hidersStartBossBar.addPlayer(player.getPlayer());
                player.getPlayer().setMetadata("activeBossBar", new FixedMetadataValue(JavaPlugin.getPlugin(AdvancedHNS.class), hidersStartBossBar));

                JavaPlugin.getPlugin(AdvancedHNS.class).boards.get(player.getPlayer()).updateLines(
                        Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.youre_in_arena")),
                        Component.text(name),
                        Component.text(""),
                        Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status").replace("{status}", LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status_" + status))),
                        Component.text(""),
                        Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.hider")),
                        Component.text(""),
                        Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.footer"))
                );
            }

            JavaPlugin.getPlugin(AdvancedHNS.class).startArenaTimer(this);
        }
    }

    public void decreasePrepareTime() {
        this.prepareTime--;
    }

    public int getPrepareTime() {
        return this.prepareTime;
    }
}
