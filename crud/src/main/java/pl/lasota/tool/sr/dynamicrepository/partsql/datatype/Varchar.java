package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

public class Varchar implements Datatype {
    @Override
    public String connect() {
        return "VARCHAR";
    }
}
