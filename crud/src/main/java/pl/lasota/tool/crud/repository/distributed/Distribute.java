package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.repository.MapperFields;

import java.util.List;

public interface Distribute<FIELD, MODEL> {

    void fieldProvider(List<FIELD> fields, MapperFields<MODEL> mapperFields);
}
