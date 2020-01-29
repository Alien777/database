package pl.lasota.tool.sr.dynamicrepository.constrains;

public class NotNull implements Constrain {

    @Override
    public String build() {
        return "NOT NULL";
    }
}
