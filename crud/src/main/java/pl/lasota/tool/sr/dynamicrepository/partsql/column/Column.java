package pl.lasota.tool.sr.dynamicrepository.partsql.column;

import pl.lasota.tool.sr.dynamicrepository.partsql.constrains.Constrain;
import pl.lasota.tool.sr.dynamicrepository.partsql.datatype.Datatype;
import pl.lasota.tool.sr.dynamicrepository.partsql.Concatenable;

public class Column implements Concatenable {
    private final String name;
    private final Constrain[] constrain;
    private final Datatype datatype;

    public Column(String name, Datatype datatype) {
        this(name, datatype, null);
    }

    public Column(String name, Datatype datatype, Constrain... constrain) {
        this.name = name;
        this.constrain = constrain;
        this.datatype = datatype;
    }

    @Override
    public String connect() {
        StringBuilder constSql = new StringBuilder("");
        if (constrain != null)
            for (Constrain constrain : constrain) {
                constSql.append(" ");
                constSql.append(constrain.connect());
            }

        return name
                + " "
                + datatype.connect()
                + " "
                + constSql;
    }
}
