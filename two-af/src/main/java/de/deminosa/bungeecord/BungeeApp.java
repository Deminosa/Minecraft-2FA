package de.deminosa.bungeecord;

import java.io.File;
import java.io.IOException;

import de.deminosa.auth.AuthManager;
import de.deminosa.utils.JIniFile;
import de.deminosa.utils.License;
import de.deminosa.utils.License.LogType;
import de.deminosa.utils.License.ValidationType;
import de.deminosa.web.Webinterface;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeApp extends Plugin{

    private static BungeeApp instance;
    private Webinterface webinterface;
    private AuthManager authManager;
    private JIniFile config;
    
    @Override
    public void onEnable() {
        instance = this;
        super.onEnable();
        
        createConfig();
        setupConfig();
        checkLicence();

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

    private void checkLicence() {
        String server = "http://media.deminosa.de/license/verify.php";
        String license = config.ReadString("config", "license", "null");
        ValidationType vt = new License(license, server, getDescription().getName()).setConsoleLog(LogType.NONE).isValid();

        switch(vt) {
            case INVALID_PLUGIN:
                getProxy().stop("[2FA] Invalid Plugin!");
                break;
            case KEY_NOT_FOUND:
                getProxy().stop("[2FA] Key not found!");
                break;
            case KEY_OUTDATED:
                getProxy().stop("[2FA] Key outdated!");
                break;
            case NOT_VALID_IP:
                getProxy().stop("[2FA] IP not valid!");
                break;
            case PAGE_ERROR:
                getProxy().stop("[2FA] Page error - Possibly license server offline? - Try again later!");
                break;
            case URL_ERROR:
                getProxy().stop("[2FA] URL error - license server not found!");
                break;
            case VALID:
                break;
            case WRONG_RESPONSE:
                getProxy().stop("[2FA] Wrong response - Try again later!");
                break;
            default:
                getProxy().stop("[2FA] Something is wrong - no information can be given.");
                break;
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
            config.setString("OnlyProxyJoin", "IP", "127.0.0.1");
            config.setString("OnlyProxyJoin", "msg", "&8[&62FA&8] &cKick from server!&nl"
                            +"&cPlease connect via the proxy and, "
                            +"if necessary, identify yourself via the 2FA function!");

            config.setString("config", "isSet", "True");
            config.UpdateFile();
        }
        
    }
}
