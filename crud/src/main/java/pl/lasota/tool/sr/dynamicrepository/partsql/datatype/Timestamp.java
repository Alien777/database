package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

public class Timestamp implements Datatype {
    @Override
    public String connect() {
        return "TIMESTAMP";
    }
}
