package pl.lasota.tool.crud.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.crud.CrudRepository;
import pl.lasota.tool.crud.repository.EntityBase;

@AllArgsConstructor
public class BaseCrudService<CREATING, READING, UPDATING, MODEL extends EntityBase>
        implements CrudService<CREATING, READING, UPDATING> {

    private final CrudRepository<MODEL> repository;
    private final Mapping<CREATING, MODEL> creatingToModel;
    private final Mapping<UPDATING, MODEL> updatingToModel;
    private final Mapping<MODEL, READING> modelToReading;

    @Override
    @Transactional
    public READING save(CREATING create) {
        MODEL model = creatingToModel.mapper(create);
        model.setId(null);
        MODEL save = repository.save(model);
        if (save == null) {
            return null;
        }
        return modelToReading.mapper(save);
    }

    @Override
    public READING get(Long id) {
        MODEL model = repository.get(id);
        if (model == null) {
            return null;
        }
        return modelToReading.mapper(model);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        return repository.delete(id);
    }

    @Override
    @Transactional
    public READING update(UPDATING updating) {
        MODEL model = updatingToModel.mapper(updating);
        if (model.getId() == null) {
            return null;
        }
        MODEL updated = repository.update(model);
        if (updated == null) {
            return null;
        }
        return modelToReading.mapper(updated);
    }
}
