package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

public class Boolean implements Datatype {
    @Override
    public String connect() {
        return "BOOLEAN";
    }
}
