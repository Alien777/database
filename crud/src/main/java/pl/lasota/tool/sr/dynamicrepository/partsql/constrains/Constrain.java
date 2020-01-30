package pl.lasota.tool.sr.dynamicrepository.partsql.constrains;

import pl.lasota.tool.sr.dynamicrepository.partsql.Concatenable;

public interface Constrain extends Concatenable {
    @Override
    String connect();
}
