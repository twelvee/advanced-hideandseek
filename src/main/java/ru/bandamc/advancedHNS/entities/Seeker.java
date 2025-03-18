package ru.bandamc.advancedHNS.entities;

import org.bukkit.entity.Player;

public class Seeker implements Actor {
    private Player player;

    public Seeker(Player p) {
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
