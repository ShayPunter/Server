package org.cloudburstmc.server.event.player;

import org.cloudburstmc.server.event.Cancellable;
import org.cloudburstmc.server.event.HandlerList;
import org.cloudburstmc.server.player.Player;

/**
 * @author CreeperFace
 */
public class PlayerToggleSwimEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final boolean isSwimming;

    public PlayerToggleSwimEvent(Player player, boolean isSwimming) {
        super(player);
        this.isSwimming = isSwimming;
    }

    public boolean isSwimming() {
        return this.isSwimming;
    }
}
