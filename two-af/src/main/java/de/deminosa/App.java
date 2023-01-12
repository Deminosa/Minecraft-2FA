package de.deminosa;

import de.deminosa.web.Webinterface;
import net.md_5.bungee.api.plugin.Plugin;

public class App extends Plugin{

    private static App instance;
    private Webinterface webinterface;
    
    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();

        webinterface = new Webinterface();
        webinterface.onEnable();
    }

    @Override
    public void onDisable() {
        webinterface.onDisable();
        super.onDisable();
    }

    public static App getInstance() {
        return instance;
    }

    public Webinterface getWebinterface() {
        return webinterface;
    }

    public static void log(String s) {
        System.out.println("[Webinterface] " + s);
    }
}
