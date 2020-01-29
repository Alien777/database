package pl.lasota.tool.sr.dynamicrepository.constrains;

public class Unique implements Constrain {

    @Override
    public String build() {
        return "UNIQUE";
    }
}
