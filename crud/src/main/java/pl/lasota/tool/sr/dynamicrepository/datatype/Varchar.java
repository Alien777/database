package pl.lasota.tool.sr.dynamicrepository.datatype;

public class Varchar implements Datatype {
    @Override
    public String build() {
        return "VARCHAR";
    }
}
