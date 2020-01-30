package pl.lasota.tool.sr.dynamicrepository.signaturesql;

import pl.lasota.tool.sr.dynamicrepository.partsql.column.Column;

public class CreateTableSignature implements CreatableSql {

    private final String tableName;
    private final Column[] columns;

    public CreateTableSignature(String tableName, Column... columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    @Override
    public String create() {
        final StringBuilder sql = new StringBuilder("");
        for (int i = 0; i < columns.length; i++) {
            sql.append(columns[i].connect());
            if (i < columns.length - 1) {
                sql.append(",");
            }
        }

        return format("CREATE TABLE " + tableName + " (" + sql + ");");
    }

}
