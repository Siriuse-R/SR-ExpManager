package net.siriuser.expmanager.listeners;

import net.siriuser.expmanager.ExpManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {
    private ExpManager plugin;
    public PlayerListener(final ExpManager plugin) {
        this.plugin = plugin;
    }

}
