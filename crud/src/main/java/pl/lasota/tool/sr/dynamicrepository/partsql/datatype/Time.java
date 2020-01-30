package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

public class Time implements Datatype {
    @Override
    public String connect() {
        return "TIME";
    }
}
