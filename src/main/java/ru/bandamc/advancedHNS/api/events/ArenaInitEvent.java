package ru.bandamc.advancedHNS.api.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ru.bandamc.advancedHNS.entities.Arena;

public final class ArenaInitEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Arena arena;

    public ArenaInitEvent(Player actor, Arena arena) {
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
}