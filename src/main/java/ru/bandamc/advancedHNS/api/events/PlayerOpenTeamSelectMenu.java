package ru.bandamc.advancedHNS.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ru.bandamc.advancedHNS.entities.Arena;

public final class PlayerOpenTeamSelectMenu extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private boolean isCancelled = false;

    public PlayerOpenTeamSelectMenu(Player actor) {
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