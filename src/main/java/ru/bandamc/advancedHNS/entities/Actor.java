package ru.bandamc.advancedHNS.entities;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public interface Actor {
    public void setPlayer(Player p);

    public Player getPlayer();
}
