package de.deminosa.bungeecord;

import java.io.File;
import java.io.IOException;

import de.deminosa.bungeecord.databasesystem.DataBaseSystem;
import de.deminosa.utils.JIniFile;
import de.deminosa.utils.License;
import de.deminosa.utils.License.LogType;
import de.deminosa.utils.License.ValidationType;
import de.deminosa.utils.auth.AuthManager;
import de.deminosa.utils.mojang.MojangAPI;
import de.deminosa.utils.mysql.MySQL;
import de.deminosa.web.webhandler.Webinterface;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeApp extends Plugin{

    private static BungeeApp instance;
    private Webinterface webinterface;
    private AuthManager authManager;
    private JIniFile config;
    private MySQL mysql;
    private MojangAPI mojangAPI;
    private DataBaseSystem dataBaseSystem;
    
    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();
        
        createConfig();
        setupConfig();
        
        if(checkLicence()){
            webinterface = new Webinterface();
            webinterface.onEnable();

            authManager = new AuthManager();
            mysql = new MySQL(getDataFolder() + "");

            registerChannelID("onlyProxyJoin");
            mojangAPI = new MojangAPI();
            dataBaseSystem = new DataBaseSystem(getDataFolder() + "");
        }else {
            getProxy().stop();
        }
    }

    @Override
    public void onDisable() {
        webinterface.onDisable();
        super.onDisable();
    }

    public DataBaseSystem getDataBaseSystem() {
        return dataBaseSystem;
    }

    public JIniFile getConfig() {
        return config;
    }

    public MySQL getMysql() {
        return mysql;
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

    private boolean checkLicence() {
        String server = "http://media.deminosa.de/license/verify.php";
        String license = config.ReadString("config", "license", "null");
        ValidationType vt = new License(license, server, getDescription().getName()).setConsoleLog(LogType.NONE).isValid();

        switch(vt) {
            case INVALID_PLUGIN:
                log("Invalid Plugin! - Please check whether the license for the plugin is correct!");
                return false;
            case KEY_NOT_FOUND:
                log("Key not found! - Please check the config!");
                return false;
            case KEY_OUTDATED:
                log("Key outdated! - License has expired! - Please contact the seller!");
                return false;
            case NOT_VALID_IP:
                log("IP not valid! - Maybe there is already a connection?");
                return false;
            case PAGE_ERROR:
                log("Page error - Possibly license server offline? - Try again later!");
                return false;
            case URL_ERROR:
                log("URL error - license server not found!");
                return false;
            case VALID:
                log("License accept!");
                return true;
            case WRONG_RESPONSE:
                log("Wrong response - Try again later!");
                return false;
            default:
                log("Something is wrong - no information can be given.");
                return false;
        }
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
            config.setString("config", "license", "null");
            config.setString("config", "ip", "127.0.0.1");

            config.UpdateFile();
        }
        
    }

    public MojangAPI getMojangAPI() {
        return mojangAPI;
    }
}
