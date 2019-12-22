package pl.lasota.tool.crud.mapping;

import org.springframework.data.domain.Page;

/**
 * Interface providing mapping between object crud procedure
 * @param <CREATING>
 * @param <READING>
 * @param <UPDATING>
 * @param <MODEL>
 */
public interface CrudMapping<CREATING, READING, UPDATING, MODEL> {
    MODEL creatingToModel(CREATING create);

    MODEL updatingToModel(UPDATING model);

    READING modelToReading(MODEL model);
}
