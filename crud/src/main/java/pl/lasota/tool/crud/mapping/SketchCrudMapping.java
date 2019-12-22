package pl.lasota.tool.crud.mapping;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;


/**
 * Simple mapping using to crud service
 *
 * @param <CREATING>
 * @param <READING>
 * @param <UPDATING>
 * @param <MODEL>
 */
@AllArgsConstructor
public class SketchCrudMapping<CREATING, READING, UPDATING, MODEL> implements CrudMapping<CREATING, READING, UPDATING, MODEL> {

    private final Mapping<CREATING, MODEL> createModelMapping;
    private final Mapping<UPDATING, MODEL> updatingModelMapping;
    private final Mapping<MODEL, READING> readModelMapping;

    @Override
    public MODEL creatingToModel(CREATING create) {
        return createModelMapping.mapper(create);
    }

    @Override
    public MODEL updatingToModel(UPDATING model) {
        return updatingModelMapping.mapper(model);
    }

    @Override
    public READING modelToReading(MODEL model) {
        return readModelMapping.mapper(model);
    }

}
