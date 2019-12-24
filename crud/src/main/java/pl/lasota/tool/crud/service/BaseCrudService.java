package pl.lasota.tool.crud.service;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.crud.CrudRepository;

public class BaseCrudService<CREATING, READING, UPDATING, MODEL extends EntityBase>
        implements CrudService<CREATING, READING, UPDATING> {

    private final CrudRepository<MODEL> repository;
    private final Mapping<CREATING, MODEL> creatingToModel;
    private final Mapping<UPDATING, MODEL> updatingToModel;
    private final Mapping<MODEL, READING> modelToReading;

    public BaseCrudService(CrudRepository<MODEL> repository, Mapping<CREATING, MODEL> creatingToModel,
                           Mapping<UPDATING, MODEL> updatingToModel, Mapping<MODEL, READING> modelToReading) {
        this.repository = repository;
        this.creatingToModel = creatingToModel;
        this.updatingToModel = updatingToModel;
        this.modelToReading = modelToReading;
    }

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
    @Transactional(readOnly = true)
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
