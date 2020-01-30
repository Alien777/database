package pl.lasota.tool.sr.dynamicrepository.partsql.constrains;

public class Unique implements Constrain {

    @Override
    public String connect() {
        return "UNIQUE";
    }
}
