package de.deminosa.bungeecord;

import de.deminosa.auth.AuthManager;
import de.deminosa.web.Webinterface;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeApp extends Plugin{

    private static BungeeApp instance;
    private Webinterface webinterface;
    private AuthManager authManager;
    
    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();

        webinterface = new Webinterface();
        webinterface.onEnable();

        authManager = new AuthManager();

        registerChannelID("onlyProxyJoin");
    }

    @Override
    public void onDisable() {
        webinterface.onDisable();
        super.onDisable();
    }

    public static BungeeApp getInstance() {
        return instance;
    }

    public Webinterface getWebinterface() {
        return webinterface;
    }

    public AuthManager getAuthManager() {
        return authManager;
    }

    private void registerChannelID(String id) {
        getProxy().registerChannel("2fa:"+id);
        log("[Channel] Create Channel Communication for '2fa:"+id+"'");
    }

    public static void log(String s) {
        System.out.print("[2FA] [Proxy] " + s);
    }
}
