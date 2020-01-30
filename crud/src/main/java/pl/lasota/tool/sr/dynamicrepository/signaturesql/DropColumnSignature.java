package pl.lasota.tool.sr.dynamicrepository.signaturesql;

public class DropColumnSignature implements CreatableSql {

    private final String tableName;
    private final String[] names;

    public DropColumnSignature(String tableName, String... names) {
        this.tableName = tableName;
        this.names = names;
    }

    @Override
    public String create() {
        final StringBuilder sql = new StringBuilder("");
        sql.append("ALTER TABLE ");
        sql.append(tableName);
        for (int i = 0; i < names.length; i++) {
            sql.append(" DROP COLUMN ");
            sql.append(names[i]);
            if (i < names.length - 1) {
                sql.append(",");
            }
        }
        sql.append(";");
        return format(sql.toString());
    }

}
