package pl.lasota.tool.sr.dynamicrepository.partsql.constrains;

public class PrimaryKey implements Constrain {

    @Override
    public String connect() {
        return "PRIMARY KEY";
    }
}
