package pl.lasota.tool.sr.dynamicrepository.partsql.datatype;

import pl.lasota.tool.sr.dynamicrepository.partsql.Concatenable;

public interface Datatype extends Concatenable {

    @Override
    String connect();
}
