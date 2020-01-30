package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

public class Text implements Datatype {
    @Override
    public String connect() {
        return "TEXT";
    }
}
