package de.deminosa.spigot.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;


public class OnlyProxyJoinListener implements Listener{
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if(!event.getAddress().getHostAddress().equals("127.0.0.1")){
            //TODO Make it better
        }
    }
}
