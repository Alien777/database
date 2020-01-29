package pl.lasota.tool.sr.dynamicrepository;

public class TableSignature implements Table {

    private final String name;
    private final ColumnSignature[] signatures;


    public TableSignature(String name, ColumnSignature... signatures) {
        this.name = name;
        this.signatures = signatures;
    }

    @Override
    public String build() {
        final StringBuilder columns = new StringBuilder("");
        for (int i = 0; i < signatures.length; i++) {
            columns.append(signatures[i].build());
            if (i < signatures.length - 1) {
                columns.append(",");
            }
        }

        return "CREATE TABLE " + name + " (" + columns + ");";
    }

    @Override
    public String addColumn() {
        final StringBuilder columns = new StringBuilder("");
        for (ColumnSignature signature : signatures) {
            columns.append("ALERT TABLE ");
            columns.append(name);
            columns.append(" ");
            columns.append(signature.build());
            columns.append(";");
            columns.append("\n");
        }

        return columns.toString();
    }

    @Override
    public String removeColumn() {
        final StringBuilder columns = new StringBuilder("");
        columns.append("ALERT TABLE ");
        columns.append(name);
        for (ColumnSignature signature : signatures) {

            columns.append(" ADD COLUMN ");
            columns.append(signature.build());
            columns.append(",");
            columns.append("\n");
        }


        return columns;
    }
}
