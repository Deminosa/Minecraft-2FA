package de.deminosa.spigot;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.deminosa.utils.JIniFile;

public class SpigotApp extends JavaPlugin{
    
    private static SpigotApp instance;
    private JIniFile config;

    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();

        createConfig();
        setupConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static SpigotApp getInstance() {
        return instance;
    }

    private void createConfig(){
        File dir = getDataFolder();
        dir.mkdirs();

        File file = new File(dir+"", "config.ini");
        if(!file.exists()){
            try {
                file.createNewFile();
                log("[INFO] Config was created!");
            } catch (IOException e) {
                e.printStackTrace();
                log("[ERO] Fail to create Config! - To protect your Server, we will cancel everyone Connections to Server "
                        +"until the server is restarted and config is setup.");
                log("[ERO] " + e.fillInStackTrace());
                return;
            }
        }

        config = new JIniFile(file);
    }

    private void setupConfig() {
        if(!config.SectionExist("config")){
            config.setString("OnlyProxyJoin", "IP", "127.0.0.1");
            config.setString("OnlyProxyJoin", "msg", "&8[&62FA&8] &cKick from server!&nl"
                            +"&cPlease connect via the proxy and, "
                            +"if necessary, identify yourself via the 2FA function!");

            config.setString("config", "isSet", "True");
            config.UpdateFile();
        }
        
    }
    
    private void registerChannelInID(String id, PluginMessageListener chanelClazz) {
        getServer().getMessenger().registerIncomingPluginChannel(instance, "2fa:"+id, chanelClazz);
        log("[Channel] Create Channel Communication for '2fa:"+id+"' with class '"+chanelClazz.getClass().getSimpleName()+"'");
    }

    private void registerChannelOutID(String id, PluginMessageListener chanelClazz) {
        getServer().getMessenger().registerOutgoingPluginChannel(instance, "2fa:"+id);
        log("[Channel] Create Channel Communication for '2fa:"+id);
    }

    public static void log(String s) {
        System.out.print("[2FA] [Spigot] " + s);
    }
}
