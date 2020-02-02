package pl.lasota.tool.sr.dynamicrepository.signaturesql;

import pl.lasota.tool.sr.field.Field;

import java.util.List;

public class SearchSignature implements CreatableSql{

    private final String tableName;
    private final List<Field<?>> fields;

    public SearchSignature(String tableName, List<Field<?>> fields) {
        this.tableName = tableName;
        this.fields = fields;
    }

    @Override
    public String create() {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(tableName);
        sql.append(" WHERE ");

        return format("SQL * FROM "+tableName);
    }
}
