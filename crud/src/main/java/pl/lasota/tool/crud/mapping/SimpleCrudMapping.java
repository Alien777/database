package pl.lasota.tool.crud.mapping;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;


@AllArgsConstructor
public class SimpleCrudMapping<CREATE, READ, UPDATING, MODEL> implements CrudMapping<CREATE, READ, UPDATING, MODEL> {

    private final Mapping<CREATE, MODEL> createModelMapping;
    private final Mapping<UPDATING, MODEL> updatingModelMapping;
    private final Mapping<MODEL, READ> readModelMapping;
    private final Mapping<Page<MODEL>, Page<READ>> pagePageMapping;

    @Override
    public MODEL createToModel(CREATE create) {
        return createModelMapping.mapper(create);
    }

    @Override
    public MODEL updatingToModel(UPDATING model) {
        return updatingModelMapping.mapper(model);
    }

    @Override
    public READ modelToRead(MODEL model) {
        return readModelMapping.mapper(model);
    }

    @Override
    public Page<READ> modelsToPage(Page<MODEL> model) {
        return pagePageMapping.mapper(model);
    }
}
