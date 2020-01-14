package pl.lasota.tool.sr.service.base;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.DozerSameObject;
import pl.lasota.tool.sr.mapping.Mapping;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.crud.CrudRepository;
import pl.lasota.tool.sr.security.Updating;
import pl.lasota.tool.sr.service.CrudService;

@Transactional(readOnly = true)
public class BaseCrudService<CREATING, READING, UPDATING extends Updating, MODEL extends EntityBase>
        implements CrudService<CREATING, READING, UPDATING> {

    private final CrudRepository<MODEL> repository;
    private final Mapping<CREATING, MODEL> creatingToModel;
    private final Mapping<UPDATING, MODEL> updatingToModel;
    private final Mapping<MODEL, READING> modelToReading;
    private final DozerSameObject<MODEL> modelToModel;

    public BaseCrudService(CrudRepository<MODEL> repository, Mapping<CREATING, MODEL> creatingToModel,
                           Mapping<UPDATING, MODEL> updatingToModel, Mapping<MODEL, READING> modelToReading, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
        this.creatingToModel = creatingToModel;
        this.updatingToModel = updatingToModel;
        this.modelToReading = modelToReading;
        modelToModel = new DozerSameObject<>(modelClass);
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
        if (updating == null
                || updating.getId() == null
                || updating.getId() < 0) {
            return null;
        }
        MODEL inDatabase = repository.get(updating.getId());
        MODEL inUpdated = updatingToModel.mapper(updating);
        modelToModel.mapper(inUpdated, inDatabase);
        return modelToReading.mapper(inDatabase);
    }
}
