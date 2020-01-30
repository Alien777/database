package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

public class Serial implements Datatype {
    @Override
    public String connect() {
        return "SERIAL";
    }
}
