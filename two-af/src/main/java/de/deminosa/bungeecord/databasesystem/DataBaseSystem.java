package de.deminosa.bungeecord.databasesystem;

import java.util.UUID;

import de.deminosa.bungeecord.BungeeApp;
import de.deminosa.utils.mojang.sessionserver.MojangName;
import de.deminosa.utils.mysql.ColumType;
import de.deminosa.utils.mysql.builder.Colum;
import de.deminosa.utils.mysql.builder.ColumValue;
import de.deminosa.utils.mysql.builder.Search;
import de.deminosa.utils.mysql.builder.Table;
import de.deminosa.utils.mysql.builder.Update;

public class DataBaseSystem {

    private final Table table;
    private final Table backUpCodes;
    private final Table createTable;

    public DataBaseSystem(String dataFolder) {
        table = new Table("twoFA_accounts", dataFolder);
        backUpCodes = new Table("twoFA_backup", dataFolder);
        createTable = new Table("twoFA_create", dataFolder);

        setUpTable();
        setUpBackUp();
        setUpCreateTable();
    }

    public String createCreateToken(String player) {
        String playerUuid = BungeeApp.getInstance().getMojangAPI().getSessionServer().getUUID(new MojangName(player));
        if(playerUuid == null || playerUuid == "null"){
            return null;
        }

        if(createTable.exsistValue(new Update(new Search("UUID", playerUuid), new ColumValue("UUID", playerUuid)))){
            return null;
        }

        UUID uuid = UUID.randomUUID();
        createTable.setFirstColum(new ColumValue("UUID", playerUuid));
        createTable.updateColum(new Update(new Search("UUID", playerUuid), new ColumValue("TOKEN", uuid.toString())));

        return uuid.toString();
    }

    private void setUpCreateTable() {
        Colum colum = new Colum();

        colum.create("UUID", ColumType.UUID);
        colum.create("TOKEN", ColumType.VARCHAR_256);
        createTable.create(colum);
    }

    private void setUpTable() {
        Colum colum = new Colum();

        colum.create("UUID", ColumType.UUID);
        colum.create("TOKEN_KEY", ColumType.VARCHAR_256);
        table.create(colum);
    }

    private void setUpBackUp() {
        Colum colum = new Colum();

        colum.create("UUID", ColumType.UUID);
        colum.create("BACKUP_CODE", ColumType.VARCHAR_256);
        backUpCodes.create(colum);
    }

    public Table getTable() {
        return table;
    }

    public Table getBackUpCodes() {
        return backUpCodes;
    }

    public Table getCreateTable() {
        return createTable;
    }

}
