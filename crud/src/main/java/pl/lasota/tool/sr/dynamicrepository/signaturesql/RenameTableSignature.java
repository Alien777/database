package pl.lasota.tool.sr.dynamicrepository.signaturesql;

public class RenameTableSignature implements CreatableSql {

    private final String tableName;
    private final String newTableName;


    public RenameTableSignature(String tableName, String newTableName) {
        this.tableName = tableName;
        this.newTableName = newTableName;
    }

    @Override
    public String create() {
        return format("ALTER TABLE " + tableName + " RENAME TO " + newTableName + ";");
    }
}
