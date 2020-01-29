package pl.lasota.tool.sr.dynamicrepository.datatype;

public class Timestamp implements Datatype {
    @Override
    public String build() {
        return "TIMESTAMP";
    }
}
