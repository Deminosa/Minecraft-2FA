package de.deminosa;

import net.md_5.bungee.api.plugin.Plugin;

public class App extends Plugin{

    private static App instance;
    
    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static App getInstance() {
        return instance;
    }
}
