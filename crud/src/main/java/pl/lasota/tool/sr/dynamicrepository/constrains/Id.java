package pl.lasota.tool.sr.dynamicrepository.constrains;

public class Id implements Constrain {

    @Override
    public String build() {
        return "SERIAL NOT NULL UNIQUE";
    }
}
