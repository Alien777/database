package pl.lasota.tool.sr.dynamicrepository.constrains;

public class PrimaryKey implements Constrain {

    @Override
    public String build() {
        return "PRIMARY KEY";
    }
}
