package ru.bandamc.advancedHNS.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class PlayerOpenItemsMenu extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private boolean isCancelled = false;

    public PlayerOpenItemsMenu(Player actor) {
        this.player = actor;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }
}