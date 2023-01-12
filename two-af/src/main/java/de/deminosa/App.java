package de.deminosa;

import java.io.IOException;

import com.google.zxing.WriterException;

import de.deminosa.auth.AuthManager;
import de.deminosa.web.Webinterface;
import net.md_5.bungee.api.plugin.Plugin;

public class App extends Plugin{

    private static App instance;
    private Webinterface webinterface;
    private AuthManager authManager;
    
    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();

        webinterface = new Webinterface();
        webinterface.onEnable();

        authManager = new AuthManager();
        String key = authManager.generateSecretKey();
        String barcode = authManager.getGoogleAuthenticatorBarCode(key, "Deminosa", "Minecraft 2FA");
        log("Key: " + key);
        log("Barcode: " + barcode);
        log("QR: " + authManager.createQRCode(barcode, 300, 300));
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

    public AuthManager getAuthManager() {
        return authManager;
    }

    public static void log(String s) {
        System.out.print("[Webinterface] " + s);
    }
}
