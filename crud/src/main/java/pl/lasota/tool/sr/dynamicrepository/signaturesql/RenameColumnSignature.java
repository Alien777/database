package pl.lasota.tool.sr.dynamicrepository.signaturesql;

public class RenameColumnSignature implements CreatableSql {

    private final String tableName;
    private final String columnName;
    private final String newColumnName;


    public RenameColumnSignature(String tableName, String columnName, String newColumnName) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.newColumnName = newColumnName;
    }

    @Override
    public String create() {
        return format("ALTER TABLE " + tableName + " RENAME COLUMN " + columnName + " TO " + newColumnName + ";");
    }
}
