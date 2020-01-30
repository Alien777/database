package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

public class Date implements Datatype {
    @Override
    public String connect() {
        return "DATE";
    }

}
