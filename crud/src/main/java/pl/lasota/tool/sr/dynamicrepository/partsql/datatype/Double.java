package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

public class Double implements Datatype {
    @Override
    public String connect() {
        return "DOUBLE";
    }
}
