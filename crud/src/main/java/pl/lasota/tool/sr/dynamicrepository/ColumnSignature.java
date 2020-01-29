package pl.lasota.tool.sr.dynamicrepository;

import pl.lasota.tool.sr.dynamicrepository.constrains.Constrain;
import pl.lasota.tool.sr.dynamicrepository.datatype.Datatype;

public class ColumnSignature implements Column {
    private final String name;
    private final Constrain constrain;
    private final Datatype datatype;

    public ColumnSignature(String name, Datatype datatype) {
        this(name, null, datatype);
    }

    public ColumnSignature(String name, Constrain constrain, Datatype datatype) {
        this.name = name;
        this.constrain = constrain;
        this.datatype = datatype;
    }

    @Override
    public String build() {
        return name
                + " "
                + datatype.build()
                + " "
                + (constrain != null ? constrain.build() : "") + ";";
    }
}
