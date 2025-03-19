package ru.bandamc.advancedHNS.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ru.bandamc.advancedHNS.entities.Arena;

public final class JoinHiderEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Arena arena;
    private boolean isCancelled = false;

    public JoinHiderEvent(Player actor, Arena arena) {
        this.player = actor;
        this.arena = arena;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
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