package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

public class Integer implements Datatype {
    @Override
    public String connect() {
        return "INTEGER";
    }
}
