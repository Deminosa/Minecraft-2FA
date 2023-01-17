package de.deminosa.bungeecord.channel.fromspigot;

import de.deminosa.bungeecord.channel.PluginChannelManager;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnlyProxyJoinMessage implements Listener{
    
    @EventHandler
    public void onIncomingMessage(PluginMessageEvent event) {
        String[] data = PluginChannelManager.getData(event.getData());
        
        if(data[0].equalsIgnoreCase("OnlyProxyJoin")){

        }
    }

}
