package ru.bandamc.advancedHNS.entities;

import org.bukkit.entity.Player;

public class Hider implements Actor {
    private Player player;

    public Hider(Player p) {
        this.player = p;
    }

    @Override
    public void setPlayer(Player p) {
        this.player = p;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}
