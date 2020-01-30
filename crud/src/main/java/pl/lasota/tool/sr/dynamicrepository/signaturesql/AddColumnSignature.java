package pl.lasota.tool.sr.dynamicrepository.signaturesql;

import pl.lasota.tool.sr.dynamicrepository.partsql.column.Column;

public class AddColumnSignature implements CreatableSql {

    private final String tableName;
    private final Column[] columns;

    public AddColumnSignature(String tableName, Column... columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    @Override
    public String create() {
        final StringBuilder sql = new StringBuilder("");
        sql.append("ALTER TABLE ");
        sql.append(tableName);
        for (int i = 0; i < columns.length; i++) {
            Column column = columns[i];
            sql.append(" ADD COLUMN ");
            sql.append(column.connect());
            if (i < columns.length - 1)
            {
                sql.append(",");
            }


        }
        sql.append(";");
        return format(sql.toString());
    }

}
