package de.deminosa.bungeecord.databasesystem;

import de.deminosa.bungeecord.BungeeApp;
import de.deminosa.utils.mysql.builder.Table;

public class DataBaseSystem {

    private final Table table;
    private final Table backUpCodes;

    public DataBaseSystem() {
        table = new Table("twoFA_accounts", BungeeApp.getInstance().getDataFolder() + "");
        backUpCodes = new Table("twoFA_backup", BungeeApp.getInstance().getDataFolder() + "");

        
    }

}
