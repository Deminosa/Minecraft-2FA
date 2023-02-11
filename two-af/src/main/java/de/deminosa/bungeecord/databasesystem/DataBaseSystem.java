package de.deminosa.bungeecord.databasesystem;

import de.deminosa.utils.mysql.ColumType;
import de.deminosa.utils.mysql.builder.Colum;
import de.deminosa.utils.mysql.builder.Table;

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

    private void setUpCreateTable() {
        Colum colum = new Colum();

        colum.create("UUID", ColumType.UUID);
        colum.create("TOKEN", ColumType.VARCHAR_256);
        createTable.create(colum);
    }

    private void setUpTable() {
        Colum colum = new Colum();

        colum.create("UUID", ColumType.UUID);
        colum.create("KEY", ColumType.VARCHAR_256);
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
