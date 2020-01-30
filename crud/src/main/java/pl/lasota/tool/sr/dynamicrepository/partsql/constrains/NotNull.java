package pl.lasota.tool.sr.dynamicrepository.partsql.constrains;

public class NotNull implements Constrain {

    @Override
    public String connect() {
        return "NOT NULL";
    }
}
